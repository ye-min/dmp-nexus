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
import com.wisdomsky.dmp.security.model.Permission;
import com.wisdomsky.dmp.security.model.PermissionCreateParam;
import com.wisdomsky.dmp.security.model.PermissionQueryParam;
import com.wisdomsky.dmp.security.model.PermissionUpdateParam;
import com.wisdomsky.dmp.security.service.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
   // private final Logger logger = LogManager.getLogger(this.getClass());
   private static final Logger logger = LoggerFactory.getLogger(PermissionController.class);
   private static final String alias = "permission";

   @Autowired
   @Qualifier("postgresPermission")
   private PermissionService service;

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> create(
         @RequestBody PermissionCreateParam request) {

            logger.info("[/{}/create] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      PermissionCreateParam param = new PermissionCreateParam.Builder()
            .withCode(request.getCode())
            .withName(request.getName())
            .withDescription(request.getDescription())
            .build();
      code = service.createPermission(param);
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

      PermissionUpdateParam param = new PermissionUpdateParam.Builder()
            .withId(request.getId())
            .withName(request.getName())
            .withDescription(request.getDescription())
            .withStatus(request.getStatus())
            .build();
      code = service.updatePermission(param);
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

      PermissionQueryParam param = new PermissionQueryParam.Builder()
            .withIdEqual(request.getId())
            .build();
      long count = service.countPermission(param);
      if (count < 1) {
         code = -70110;
         message = String.format("The %s record does not exist.", alias);
      } else {
         code = service.deletePermission(param);
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

      PermissionQueryParam param = new PermissionQueryParam.Builder()
            .withCodeLike(request.getCode())
            .withNameLike(request.getName())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("permission_code", true));
      }
      Integer offset = null;
      Integer limit = null;
      List<Permission> list = service.retrievePermission(param, sortFieldList, offset, limit);
      for (Permission item : list) {
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

      PermissionQueryParam param = new PermissionQueryParam.Builder()
            .withCodeLike(request.getCode())
            .withNameLike(request.getName())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("permission_code", true));
      }
      PageParam page = request.getPage();
      Integer limit = ArgumentUtility.parsePageLimit(page);
      Integer offset = ArgumentUtility.parsePageOffset(page);
      List<Permission> list = service.retrievePermission(param, sortFieldList, offset, limit);
      for (Permission item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = service.countPermission(param);
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
      
      public String getCode() {
          return code;
      }  
      public String getName() {
          return name;
      }
      public String getDescription() {
          return description;
      }
   }

   public static class UpdateRequest {
      private Long id;
      private String name;
      private String description;
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
      private List<SortField> sort;
      
      public String getCode() {
          return code;
      }
      public String getName() {
          return name;
      }
      public List<SortField> getSort() {
          return sort;
      }
   }

   public static class PageQueryRequest {
      private String code;
      private String name;
      private List<SortField> sort;
      private PageParam page;
      
      public String getCode() {
          return code;
      }
      public String getName() {
          return name;
      }
      public List<SortField> getSort() {
          return sort;
      }
      public PageParam getPage() {
          return page;
      }
   }

   /*@PostMapping(value = "/tree", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getTree(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="timestamp") long timestamp,
         @RequestHeader(value="client") String client,
         @RequestHeader(value="sign") String sign ,
         @RequestBody PermissionQueryParam request) {

      logger.info("[/permission/tree] is {}. body is :{}.", "account:" + account + ",sign:" + sign, request.toString());
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         List<Permission> list = service.retrieveTree();
         for (Permission item : list) {
            JSONObject json = item.toJSONObject();
            itemList.add(json);
         }
         long total = itemList.size();
         data.put("total", total);
         data.put("list", itemList);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70501;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the permission tree failed.";
         logger.error(message, ex);
      } finally {
         response.setCode(code);
         response.setMessage(message);
      }
      return response;
   }

   @PostMapping(value = "/tree/accountcode", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<JSONObject> getTreeByCode(
         @RequestHeader(value="app") String app,
         @RequestHeader(value="account") String account,
         @RequestHeader(value="timestamp") long timestamp,
         @RequestHeader(value="client") String client,
         @RequestHeader(value="sign") String sign,
         @RequestBody PermissionQueryParam request) {

      logger.info("[/tree/accountcode] is {}. body is :{}.", "account:" + account + ",sign:" + sign, request.toString());
      int code = 0;
      String message = "success";
      JSONObject data = new JSONObject();
      JSONArray itemList = new JSONArray();
      ResponseTemplate<JSONObject> response = new ResponseTemplate<JSONObject>();
      try {
         List<Permission> list = service.retrieveTreeByCode(request.getCode());
         for (Permission item : list) {
            JSONObject json = item.toJSONObject();
            itemList.add(json);
         }
         long total = itemList.size();
         data.put("total", total);
         data.put("list", itemList);
         response.setData(data);
      } catch (InvalidParameterException ex) {
         code = -70109;
         message = ex.getMessage();
      } catch (SQLExecutionException ex) {
         code = -70501;
         message = ex.getMessage();
      } catch (Exception ex) {
         code = -70002;
         message = "Get the permission tree by account code failed.";
         logger.error(message, ex);
      } finally {
         response.setCode(code);
         response.setMessage(message);
      }
      return response;
   }*/
}
