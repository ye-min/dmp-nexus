package com.wisdomsky.dmp.security.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.library.common.CryptoUtility;
import com.wisdomsky.dmp.library.common.TimestampUtility;
import com.wisdomsky.dmp.security.exception.SecurityException;
import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;
import com.wisdomsky.dmp.security.model.Account;
import com.wisdomsky.dmp.security.model.AccountQueryParam;
import com.wisdomsky.dmp.security.model.Menu;
import com.wisdomsky.dmp.security.model.MenuQueryParam;
import com.wisdomsky.dmp.security.model.Role;
import com.wisdomsky.dmp.security.model.RoleQueryParam;
import com.wisdomsky.dmp.security.service.AccountService;
import com.wisdomsky.dmp.security.service.MenuService;
import com.wisdomsky.dmp.security.service.RoleService;
import com.wisdomsky.dmp.security.annotation.CheckDigest;
import com.wisdomsky.dmp.security.feign.TokenServiceFeignClient;
import com.wisdomsky.dmp.security.model.TokenCreateResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/identity")
public class IdentityController {
   private static final Logger logger = LoggerFactory.getLogger(IdentityController.class);
   private static final String alias = "identity";

   @Autowired
   @Qualifier("postgresAccount")
   private AccountService service;

   @Autowired
   @Qualifier("postgresRole")
   private RoleService roleService;

   @Autowired
   @Qualifier("postgresMenu")
   private MenuService menuService;

   @Autowired
   private TokenServiceFeignClient userServiceFeignClient;

   @PostMapping(value = "/login", produces = "application/json;charset=UTF-8")
   @CheckDigest(requestBodyParam = "request")
   public ResponseTemplate<ObjectNode> login(
        @RequestHeader(value="app") String app,
        @RequestHeader(value="account") String account,
        @RequestHeader(value="timestamp") long timestamp,
        @RequestHeader(value="client") String client,
        @RequestHeader(value="digest") String digest,
        @RequestHeader(value="sign") String sign,
        @RequestBody String request) {

    logger.info("[/{}/login] with {}.body is :{}", alias, "account:" + account + ", sign:" + sign, request);
    
    ObjectMapper mapper = new ObjectMapper();
    int code = 0;
    String message = "success";
    ObjectNode data = mapper.createObjectNode();
    ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

    try {
        LoginRequest loginRequest = mapper.readValue(request, LoginRequest.class);
        if (loginRequest == null) {
            throw new SecurityException(-98001, "Invalid request");
        }

        String accountCode = loginRequest.getCode();
        if (!StringUtils.hasText(accountCode)) {
            throw new SecurityException(-98002, "Invalid request");
        }

        Pattern pattern = Pattern.compile("[(\\pP|\\pS)]");
        if (pattern.matcher(accountCode).find()) {
            throw new SecurityException(-98003, "The account does not exist");
        }

        AccountQueryParam param = new AccountQueryParam.Builder()
                .withCodeEqual(accountCode)
                .build();
        List<Account> list = service.retrieveAccount(param, new ArrayList<>(), null, null);
        if (list.isEmpty()) {
            throw new SecurityException(-98004, "The account does not exist");
        }

        Account accountInstance = list.get(0);
        if (list.get(0).getStatus() == 0) {
            throw new SecurityException(-98005, "The account is inactive");
        }

        String password = accountInstance.getPassword();
        if (!StringUtils.hasText(password) || password.length() < 6) {
            throw new SecurityException(-98006, "Invalid password");
        }

        // Validate sign
        account = "000000";
        client = "000000";
        String value = String.format("%s%s%d%s%s%s", app, account, timestamp, client, digest, password);
        String sha1Value = CryptoUtility.sha1(value);
        if (!Objects.equals(sha1Value, sign)) {
            throw new SecurityException(-98007, "Invalid sign");
        }

        // Add account info
        data.set("account", accountInstance.toJSONObject());

        // Create token
        ResponseTemplate<TokenCreateResponse> tokenResponse = userServiceFeignClient.createToken(app, 
                String.valueOf(accountInstance.getId()));
        if (tokenResponse.getCode() < 0) {
            logger.error("Access /authority/token/create gets code={} message={}.", 
                    tokenResponse.getCode(), tokenResponse.getMessage());
            throw new SecurityException(-98008, "Failed to create token");
        }
        data.put("token", tokenResponse.getData().getToken());
        data.put("client", tokenResponse.getData().getClient());
        
        // Add login time
        data.put("loginDateTime", TimestampUtility.timestamp2String(TimestampUtility.getCurrentTimestamp(), 1));

        // Get roles
        RoleQueryParam roleParam = new RoleQueryParam.Builder()
                .withAccountIdEqual(accountInstance.getId())
                .build();
        List<Role> roleList = roleService.retrieveRole(roleParam, new ArrayList<>(), null, null);
        ArrayNode roleArray = mapper.createArrayNode();
        roleList.forEach(role -> roleArray.add(role.toLoginJSONObject()));
        data.set("role", roleArray);

        // Get menus
        List<Menu> menuTree;
        boolean isAdmin = roleList.stream().anyMatch(role -> "admin".equals(role.getRode()));
        if (isAdmin) {
            MenuQueryParam menuParam = new MenuQueryParam.Builder().build();
            List<Menu> menuList = menuService.retrieveMenu(menuParam, new ArrayList<>(), null, null);
            menuTree = menuService.buildMenuTree(menuList);
        } else {
            MenuQueryParam menuParam = new MenuQueryParam.Builder()
                    .withIdEqual(accountInstance.getId())
                    .build();
            List<Menu> menuList = menuService.retrieveMenu(menuParam, new ArrayList<>(), null, null);
            menuTree = menuService.buildMenuTree(menuList);
        }
        
        ArrayNode menuArray = traverseMenuStream(menuTree);
        data.set("menu", menuArray);

        code = list.size();
        
    } catch (JsonProcessingException e) {
        throw new SecurityException(-98001, "Invalid request");
    }

    response.setCode(code);
    response.setMessage(message);
    response.setData(data);
    
    return response;
}

private ArrayNode traverseMenuStream(List<Menu> menuList) {
   ObjectMapper objectMapper = new ObjectMapper();
   ArrayNode arrayNode = objectMapper.createArrayNode();
   
   menuList.stream().forEach(menu -> {
       ObjectNode jsonNode = menu.toLoginJSONObject();
       
       if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
           ArrayNode childrenArray = traverseMenuStream(menu.getChildren());
           jsonNode.set("children", childrenArray);
       }
       arrayNode.add(jsonNode);
   });
   
   return arrayNode;
}

   @PostMapping(value = "/logout", produces = "application/json;charset=UTF-8")
   @CheckDigest(requestBodyParam = "request")
   public SimpleResponseTemplate logout(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="timestamp") long timestamp,
         @RequestHeader(value="client") String client,
         @RequestHeader(value="digest") String digest,
         @RequestHeader(value="sign") String sign,
         @RequestBody String request) {

      logger.info("[/{}/logout] with {}", alias, "account:" + account + ", timestamp: " + timestamp + ", client: " + client);
      int code = 0;
      String message = "success";
      SimpleResponseTemplate response = new SimpleResponseTemplate();
      
      SimpleResponseTemplate tokenResponse = userServiceFeignClient.deleteToken(app, account, client);
      if (tokenResponse.getCode() < 0) {
         String errorMessage = String.format("Access /authority/token/delete gets code=%d message=%s.", tokenResponse.getCode(), tokenResponse.getMessage());
         logger.error(errorMessage);
         throw new SecurityException(-98010, "Token operation failed");
      }
   
      response.setCode(code);
      response.setMessage(message);
      
      return response;
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class LoginRequest {
      private String code;
      
      public String getCode() {
          return code;
      }

      public void setCode(String code) {
         this.code = code;
     }
   }
}
