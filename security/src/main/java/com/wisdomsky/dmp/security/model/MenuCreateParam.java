package com.wisdomsky.dmp.security.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class MenuCreateParam {
   private Long id;
   private String code;
   private String name;
   private String description;
   private Integer type;
   private String icon;
   private Integer sequence;
   private String url;
   private Integer status;
   private Long parentId;

   public MenuCreateParam(Long id, String code, String name, String description, Integer type, String icon, Integer sequence, String url, Integer status, Long parentId) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.description = description;
      this.type = type;
      this.icon = icon;
      this.sequence = sequence;
      this.url = url;
      this.status = status;
      this.parentId = parentId;
   }

   public Long getId() {
      return id;
   }
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
   public Integer getStatus() {
      return status;
   }
   public Long getParentId() {
      return parentId;
   }

   public static class Builder {
      private Long id;
      private String code;
      private String name;
      private String description;
      private Integer type;
      private String icon;
      private Integer sequence;
      private String url;
      private Integer status;
      private Long parentId;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();
      private List<String> requiredFieldList = Arrays.asList("id", "code", "name", "type", "sequence", "url", "parentId");

      public Builder() {}

      public Builder withId(Long value) {
         this.id = ArgumentUtility.validateRequiredLong(value, "id");
         withCalled.put("id", true);
         return this;
      }

      public Builder withCode(String value) {
         this.code = this.name = ArgumentUtility.validateRequiredString(value, "code", 32);
         withCalled.put("code", true);
         return this;
      }

      public Builder withName(String value) {
         this.code = this.name = ArgumentUtility.validateRequiredString(value, "name", 32);
         withCalled.put("name", true);
         return this;
      }

      public Builder withDescription(String value) {
         this.description = ArgumentUtility.validateDefaultString(value, "description", 255, "");
         withCalled.put("description", true);
         return this;
      }

      public Builder withType(Integer value) {
         List<Integer> typeOption = List.of(1, 2);
         this.type = ArgumentUtility.validateRequiredEnum(value, "type", typeOption);
         withCalled.put("type", true);
         return this;
      }

      public Builder withIcon(String value) {
         this.icon = ArgumentUtility.validateDefaultString(value, "icon", 255, "");
         withCalled.put("icon", true);
         return this;
      }

      public Builder withSequence(Integer value) {
         this.sequence = ArgumentUtility.validateRequiredInteger(value, "sequence");
         withCalled.put("sequence", true);
         return this;
      }

      public Builder withUrl(String value) {
         this.url = ArgumentUtility.validateRequiredString(value, "url", 255);
         withCalled.put("url", true);
         return this;
      }

      public Builder withParentId(Long value) {
         this.parentId= ArgumentUtility.validateRequiredLong(value, "parentId");
         withCalled.put("parentId", true);
         return this;
      }

      public MenuCreateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         this.id = null;
         this.status = 1;
         return new MenuCreateParam(id, code, name, description, type, icon, sequence, url, status, parentId);
      }
   }
}
