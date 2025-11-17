package com.wisdomsky.dmp.authority.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.authority.service.RedisService;
import com.wisdomsky.dmp.library.common.TokenUtility;
import com.wisdomsky.dmp.authority.exception.AuthorityException;
import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.wisdomsky.dmp.library.model.SimpleResponseTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/token")
@CrossOrigin
public class TokenController {
   private static final Logger logger = LoggerFactory.getLogger(TokenController.class);
   private static final String alias = "token";

   @Autowired
   RedisService redisService;

   @Value("${tjtc.debug:false}")
	private boolean debug = false;

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> createToken(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account) {

      logger.info("[/{}/create] with {}.", alias, "app: " + app + ", account: " + account);
      int code = 0;
      String message = "success";
      
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      String newToken = TokenUtility.buildComplexTokenCode(128);
      String newClient = TokenUtility.buildNormalTokenCode(6);
      int expirationSecond = 86400 * 7; // 60 * 60 * 24 * 7
      
      data.put("token", newToken);
      data.put("client", newClient);
      data.put("expirationSecond", expirationSecond);
      code = redisService.saveToken(app, account, newClient, newToken, expirationSecond);

      if (code < 0) {
         throw new AuthorityException(-99005, "Create token failed.");
      }
      
      response.setData(data);
      response.setCode(code);
      response.setMessage(message);

      return response;
   }

   @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
   public SimpleResponseTemplate deleteToken(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="client") String client) {

      logger.info("[/{}/delete] with {}", alias, "account:" + account + ", client: " + client);
      int code = 0;
      String message = "success";
      SimpleResponseTemplate response = new SimpleResponseTemplate();

      code = redisService.removeToken(app, account, client);
      if (code < 0) {
         throw new AuthorityException(-99006, "Delect token failed.");
      }

      response.setCode(code);
      response.setMessage(message);

      return response;
   }
}
