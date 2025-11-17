package com.wisdomsky.dmp.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.library.common.ArgumentUtility;
import com.wisdomsky.dmp.library.model.PageParam;
import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Account;
import com.wisdomsky.dmp.security.model.AccountCreateParam;
import com.wisdomsky.dmp.security.model.AccountQueryParam;
import com.wisdomsky.dmp.security.model.AccountUpdateParam;
import com.wisdomsky.dmp.security.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
   private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
   private static final String alias = "account";

   @Autowired
   @Qualifier("postgresAccount")
   private AccountService service;

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> create(
         @RequestBody CreateRequest request) {

      logger.info("[/{}/create] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      AccountCreateParam param = new AccountCreateParam.Builder()
            .withCode(request.getCode())
            .withName(request.getName())
            .withType(request.getType())
            .withEmail(request.getEmail())
            .withMobile(request.getMobile())
            .withAvatar(request.getAvatar())
            .build();
      code = service.createAccount(param);
      long id = param.getId();
      data.put("id", id);
      response.setData(data);

      response.setCode(code);
      response.setMessage(message);
      return response;
   }

   @PostMapping(value = "/update", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> update(
         @RequestBody UpdateRequest request) {

      logger.info("[/{}/update] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";
      
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      AccountUpdateParam param = new AccountUpdateParam.Builder()
            .withId(request.getId())
            .withName(request.getName())
            .withPassword(request.getPassword())
            .withType(request.getType())
            .withStatus(request.getStatus())
            .withEmail(request.getEmail())
            .withMobile(request.getMobile())
            .withAvatar(request.getAvatar())
            .build();
      code = service.updateAccount(param);
      long id = request.getId();
      data.put("id", id);
      response.setData(data);
   
      response.setCode(code);
      response.setMessage(message);
      
      return response;
   }

   @PostMapping(value = "/password/update", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> updatePassword(
        @RequestHeader(value = "account", required = false) String accountHeader,
        @RequestBody PasswordUpdateRequest request) {

      logger.info("[/{}/password/update] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";
      
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      Long accountId = Long.parseLong(accountHeader);
      AccountUpdateParam param = new AccountUpdateParam.Builder()
            .withId(accountId)
            .withPassword(request.getPassword())
            .build();
      code = service.updateAccount(param);
      long id = accountId;
      data.put("id", id);
      response.setData(data);
   
      response.setCode(code);
      response.setMessage(message);
      
      return response;
   }

   @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> delete(
         @RequestBody DeleteRequest request) {

      logger.info("[/{}/delete] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";
      
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      AccountQueryParam param = new AccountQueryParam.Builder()
            .withIdEqual(request.getId())
            .withStatusEqual(1)
            .build();
      List<Account> list = service.retrieveAccount(param, null, null, null);
      if (list.size() < 1) {
         code = -70110;
         message = String.format("The %s record does not exist.", alias);
      } else {
         Account account = list.get(0);
         AccountUpdateParam updateParam = new AccountUpdateParam.Builder()
               .withId(request.getId())
               .withCode(account.getCode() + "~" + account.getId())
               .withStatus(0)
               .build();
         code = service.updateAccount(updateParam);
         long id = request.getId();
         data.put("id", id);
         response.setData(data);
      }
   
      response.setCode(code);
      response.setMessage(message);
      
      return response;
   }

   @PostMapping(value = "/detail", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> detail(
         @RequestBody DetailRequest request) {

      logger.info("[/{}/detail] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";
      
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      AccountQueryParam param = new AccountQueryParam.Builder()
            .withIdEqual(request.getId())
            .build();
      List<SortField> sortFieldList = new ArrayList<SortField>();
      Integer offset = null;
      Integer limit = null;
      List<Account> list = service.retrieveAccount(param, sortFieldList, offset, limit);
      if (list.size() > 0) {
         Account item = list.get(0);

         ObjectNode json = item.toJSONObject();
         data = json;
      }
      response.setData(data);
      code = list.size();
   
      response.setCode(code);
      response.setMessage(message);
      
      return response;
   }

   @PostMapping(value = "/list", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> list(
         @RequestBody ListQueryRequest request) {

      logger.info("[/{}/list] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();
      
      AccountQueryParam param = new AccountQueryParam.Builder()
            .withCodeLike(request.getCode())
            .withNameLike(request.getName())
            .withStatusEqual(request.getStatus())
            .withTypeEqual(request.getType())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("code", true));
      }
      Integer offset = null;
      Integer limit = null;
      List<Account> list = service.retrieveAccount(param, sortFieldList, offset, limit);
      for (Account item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = itemList.size();
      data.put("total", total);
      data.set("list", itemList);
      response.setData(data);
      code = itemList.size();
   
      response.setCode(code);
      response.setMessage(message);

      return response;
   }

   @PostMapping(value = "/page", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> page(
         @RequestBody PageQueryRequest request) {

      logger.info("[/{}/list] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      AccountQueryParam param = new AccountQueryParam.Builder()
            .withCodeLike(request.getCode())
            .withNameLike(request.getName())
            .withStatusEqual(request.getStatus())
            .withTypeEqual(request.getType())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("code", true));
      }
      PageParam page = request.getPage();
      Integer limit = ArgumentUtility.parsePageLimit(page);
      Integer offset = ArgumentUtility.parsePageOffset(page);
      List<Account> list = service.retrieveAccount(param, sortFieldList, offset, limit);
      for (Account item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = service.countAccount(param);
      data.put("total", total);
      data.set("list", itemList);
      response.setData(data);
      code = itemList.size();

      response.setCode(code);
      response.setMessage(message);
      return response;
   }

   public static class CreateRequest {
      private String code;
      private String name;
      private Integer type;
      private String mobile;
      private String email;
      private String avatar;
      
      public String getCode() {
          return code;
      }
      public String getName() {
          return name;
      }
      public Integer getType() {
          return type;
      }
      public String getMobile() {
          return mobile;
      }
      public String getEmail() {
          return email;
      }
      public String getAvatar() {
          return avatar;
      }
   }

   public static class UpdateRequest {
      private Long id;
      private String name;
      private String password;
      private Integer type;
      private Integer status;
      private String mobile;
      private String email;
      private String avatar;
      
      public Long getId() {
          return id;
      }
      public String getName() {
          return name;
      }  
      public String getPassword() {
          return password;
      }
      public Integer getType() {
          return type;
      }
      public Integer getStatus() {
          return status;
      }
      public String getMobile() {
          return mobile;
      }
      public String getEmail() {
          return email;
      }
      public String getAvatar() {
          return avatar;
      }
   }

   public static class PasswordUpdateRequest {
      private String password;
      
      public String getPassword() {
          return password;
      }
   }

   public static class DeleteRequest {
      private Long id;
      
      public Long getId() {
          return id;
      }
   }

   public static class DetailRequest {
      private Long id;
      
      public Long getId() {
          return id;
      }
   }

   public static class ListQueryRequest {
      private String code;
      private String name;
      private Integer type;
      private Integer status;
      private List<SortField> sort;
      
      public String getCode() {
          return code;
      }
      public String getName() {
          return name;
      }
      public Integer getType() {
          return type;
      }
      public Integer getStatus() {
          return status;
      }
      public List<SortField> getSort() {
          return sort;
      }
   }

   public static class PageQueryRequest {
      private String code;
      private String name;
      private Integer type;
      private Integer status;
      private List<SortField> sort;
      private PageParam page;
      
      public String getCode() {
          return code;
      }
      public String getName() {
          return name;
      }
      public Integer getType() {
          return type;
      }
      public Integer getStatus() {
          return status;
      }
      public List<SortField> getSort() {
          return sort;
      }
      public PageParam getPage() {
          return page;
      }
   }
}
