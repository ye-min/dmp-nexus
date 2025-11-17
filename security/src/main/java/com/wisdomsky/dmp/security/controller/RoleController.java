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
import com.wisdomsky.dmp.security.model.Role;
import com.wisdomsky.dmp.security.model.RoleCreateParam;
import com.wisdomsky.dmp.security.model.RoleQueryParam;
import com.wisdomsky.dmp.security.model.RoleUpdateParam;
import com.wisdomsky.dmp.security.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
   // private final Logger logger = LogManager.getLogger(this.getClass());
   private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
   private static final String alias = "role";

   @Autowired
   @Qualifier("postgresRole")
   private RoleService service;

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> create(
         @RequestBody CreateRequest request) {

      logger.info("[/{}/create] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      RoleCreateParam param = new RoleCreateParam.Builder()
            .withRode(request.getCode())
            .withName(request.getName())
            .withDescription(request.getDescription())
            .build();
      code = service.createRole(param);
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

      RoleUpdateParam param = new RoleUpdateParam.Builder()
            .withId(request.getId())
            .withName(request.getName())
            .withDescription(request.getDescription())
            .withStatus(request.getStatus())
            .build();
      code = service.updateRole(param);
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

      RoleQueryParam param = new RoleQueryParam.Builder()
            .withIdEqual(request.getId())
            .build();
      long count = service.countRole(param);
      if (count < 1) {
         code = -70110;
         message = String.format("The %s record does not exist.", alias);
      } else {
         code = service.deleteRole(param);
         // AccountQueryParam accountQuery = new AccountQueryParam(null, null, null, null, role.getId());
         
         // service.update(param);
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

      RoleQueryParam param = new RoleQueryParam.Builder()
            .withRodeLike(request.getCode())
            .withNameLike(request.getName())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("role_code", true));
      }
      Integer offset = null;
      Integer limit = null;
      List<Role> list = service.retrieveRole(param, sortFieldList, offset, limit);
      for (Role item : list) {
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

      logger.info("[/{}/page] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      RoleQueryParam param = new RoleQueryParam.Builder()
            .withRodeLike(request.getCode())
            .withNameLike(request.getName())
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("role_code", true));
      }
      PageParam page = request.getPage();
      Integer limit = ArgumentUtility.parsePageLimit(page);
      Integer offset = ArgumentUtility.parsePageOffset(page);
      List<Role> list = service.retrieveRole(param, sortFieldList, offset, limit);
      for (Role item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = service.countRole(param);
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
}
