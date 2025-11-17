package com.wisdomsky.dmp.security.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class RoleUpdateParam {
   private Long id;
   private String name;
   private String description;
   private Integer status;

   public RoleUpdateParam(Long id, String name, String description, Integer status) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.status = status;
   }

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

   public static class Builder {
      private Long id;
      private String name;
      private String description;
      private Integer status;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();
      private List<String> requiredFieldList = Arrays.asList("id");
      private List<String> atLeastFieldList = Arrays.asList("name", "description", "status");

      public Builder() {}

      public Builder withId(Long value) {
         this.id = ArgumentUtility.validateRequiredLong(value, "id");
         withCalled.put("id", true);
         return this;
      }

      public Builder withName(String value) {
         this.name = ArgumentUtility.validateOptionalNonEmptyString(value, "name", 255);
         withCalled.put("name", !StringUtils.isBlank(this.name));
         return this;
      }

      public Builder withDescription(String value) {
         this.description = ArgumentUtility.validateOptionalAllowEmptyString(value, "description", 255);
         withCalled.put("description", this.description != null);
         return this;
      }

      public Builder withStatus(Integer value) {
         List<Integer> statusOption = List.of(0, 1);
         this.status = ArgumentUtility.validateOptionalEnum(value, "status", statusOption);
         withCalled.put("status", this.status != null);
         return this;
      }

      public RoleUpdateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         ArgumentUtility.checkAtLeastFieldList(withCalled, atLeastFieldList);
         return new RoleUpdateParam(id, name, description, status);
      }
   }
}