package com.wisdomsky.dmp.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.library.common.ArgumentUtility;
import com.wisdomsky.dmp.library.model.PageParam;
import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.wisdomsky.dmp.library.model.SortField;
import com.wisdomsky.dmp.security.model.Menu;
import com.wisdomsky.dmp.security.model.MenuCreateParam;
import com.wisdomsky.dmp.security.model.MenuQueryParam;
import com.wisdomsky.dmp.security.model.MenuUpdateParam;
import com.wisdomsky.dmp.security.service.AccountService;
import com.wisdomsky.dmp.security.service.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wisdomsky.dmp.security.service.MenuService;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {
   // private final Logger logger = LogManager.getLogger(this.getClass());
   private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
   private static final String alias = "menu";

   @Autowired
   @Qualifier("postgresMenu")
   private MenuService service;

   @Autowired
   @Qualifier("postgresAccount")
   private AccountService accountService;

   @Autowired
   @Qualifier("postgresPermission")
   private PermissionService permissionService;

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> create(
         @RequestBody CreateRequest request) {

      logger.info("[/{}/create] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();
      
      MenuCreateParam param = new MenuCreateParam.Builder()
            .withCode(request.getCode())
            .withName(request.getName())
            .withDescription(request.getDescription())
            .withIcon(request.getIcon())
            .withSequence(request.getSequence())
            .withType(request.getType())
            .withUrl(request.getUrl())
            .withParentId(request.getParentId())
            .build();
      code = service.createMenu(param);
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

      MenuUpdateParam param = new MenuUpdateParam.Builder()
            .withId(request.getId())
            .withName(request.getName())
            .withDescription(request.getDescription())
            .withIcon(request.getIcon())
            .withSequence(request.getSequence())
            .withType(request.getType())
            .withUrl(request.getUrl())
            .withStatus(request.getStatus())
            .build();
      code = service.updateMenu(param);
      long id = request.getId();
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
      
      MenuQueryParam param = new MenuQueryParam.Builder()
            .withIdEqual(request.getId())
            .build();
      long count = service.countMenu(param);
      if (count < 1) {
         code = -70110;
         message = String.format("The %s record does not exist.", alias);
      } else {
         code = service.deleteMenu(param);
         long id = request.getId();
         data.put("id", id);
         response.setData(data);
      }
   
      response.setCode(code);
      response.setMessage(message);
      
      return response;
   }

   @PostMapping(value = "/list", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> getDataSetList(
         @RequestBody ListQueryRequest request) {

      logger.info("[/{}/list] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();
      
      MenuQueryParam param = new MenuQueryParam.Builder()
            .withCodeLike(request.getCode())
            .withNameLike(request.getName())
            .withStatusEqual(request.getStatus())
            .withTypeEqual(request.getType())
            .withAccountIdEqual(request.getAccountId())
            .withRoleIdEqual(request.getRoleId())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("menu_code", true));
      }
      Integer offset = null;
      Integer limit = null;
      List<Menu> list = service.retrieveMenu(param, sortFieldList, offset, limit);
      for (Menu item : list) {
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
   public ResponseTemplate<ObjectNode> getDataSetList(
         @RequestBody PageQueryRequest request) {

      logger.info("[/{}/list] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      MenuQueryParam param = new MenuQueryParam.Builder()
            .withCodeLike(request.getCode())
            .withNameLike(request.getName())
            .withStatusEqual(request.getStatus())
            .withTypeEqual(request.getType())
            .withAccountIdEqual(request.getAccountId())
            .withRoleIdEqual(request.getRoleId())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("menu_code", true));
      }
      PageParam page = request.getPage();
      Integer limit = ArgumentUtility.parsePageLimit(page);
      Integer offset = ArgumentUtility.parsePageOffset(page);
      List<Menu> list = service.retrieveMenu(param, sortFieldList, offset, limit);
      for (Menu item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = service.countMenu(param);
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
      private String description;
      private Integer type;
      private String icon;
      private Integer sequence;
      private String url;
      private Long parentId;
      
      public String getCode() {
          return code;
      }  
      public String getName() {
          return name;
      }
      public String getDescription() {
          return description;
      }
      public Integer getType() {
          return type;
      }
      public String getIcon() {
          return icon;
      }
      public Integer getSequence() {
          return sequence;
      }
      public String getUrl() {
          return url;
      }
      public Long getParentId() {
          return parentId;
      }
   }

   public static class UpdateRequest {
      private Long id;
      private String name;
      private String description;
      private Integer type;
      private String icon;
      private Integer sequence;
      private String url;
      private Integer status;
      
      public Long getId() {
          return id;
      }
      public String getName() {
          return name;
      }  
      public String getDescription() {
          return description;
      }
      public Integer getType() {
          return type;
      }
      public String getIcon() {
          return icon;
      }
      public Integer getSequence() {
          return sequence;
      }
      public String getUrl() {
          return url;
      }
      public Integer getStatus() {
         return status;
      }
   }

   public static class DeleteRequest {
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
      private Long accountId;
      private Long roleId;
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
      public Long getAccountId() {
          return accountId;
      }
      public Long getRoleId() {
          return roleId;
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
      private Long accountId;
      private Long roleId;
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
      public Long getAccountId() {
          return accountId;
      }
      public Long getRoleId() {
          return roleId;
      }
      public List<SortField> getSort() {
          return sort;
      }
      public PageParam getPage() {
          return page;
      }
   }
}
