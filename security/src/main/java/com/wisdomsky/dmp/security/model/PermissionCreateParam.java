package com.wisdomsky.dmp.security.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class PermissionCreateParam {
   private Long id;
   private String code;
   private String name;
   private String description;
   private Integer status;

   public PermissionCreateParam(Long id, String code, String name, String description, Integer status) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.description = description;
      this.status = status;
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
   public Integer getStatus() {
      return status;
   }

   public static class Builder {
      private Long id;
      private String code;
      private String name;
      private String description;
      private Integer status;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();
      private List<String> requiredFieldList = Arrays.asList("id", "code", "name");

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
         this.name = ArgumentUtility.validateRequiredString(value, "name", 255);
         withCalled.put("name", true);
         return this;
      }

      public Builder withDescription(String value) {
         this.description = ArgumentUtility.validateDefaultString(value, "description", 255, "");
         withCalled.put("description", true);
         return this;
      }

      public PermissionCreateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         this.id = null;
         this.status = 2;
         return new PermissionCreateParam(id, code, name, description, status);
      }
   }
}
