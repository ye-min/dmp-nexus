package com.wisdomsky.dmp.security.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class MenuUpdateParam {
   private Long id;
   private String name;
   private String description;
   private Integer type;
   private String icon;
   private Integer sequence;
   private String url;
   private Integer status;

   public MenuUpdateParam(Long id, String name, String description, Integer type, String icon, Integer sequence, String url, Integer status) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.icon = icon;
      this.sequence = sequence;
      this.url = url;
      this.type = type;
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
   public String getIcon() {
      return icon;
   }
   public Integer getSequence() {
      return sequence;
   }
   public String getUrl() {
      return url;
   }
   public Integer getType() {
      return type;
   }
   public Integer getStatus() {
      return status;
   }

   public static class Builder {
      private Long id;
      private String name;
      private String description;
      private Integer type;
      private String icon;
      private Integer sequence;
      private String url;
      private Integer status;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();
      private List<String> requiredFieldList = Arrays.asList("id");
      private List<String> atLeastFieldList = Arrays.asList("name", "description", "type", "status", "icon", "sequence", "url");

      public Builder() {}

      public Builder withId(Long value) {
         this.id = ArgumentUtility.validateRequiredLong(value, "id");
         withCalled.put("id", true);
         return this;
      }

      public Builder withName(String value) {
         this.name = ArgumentUtility.validateOptionalNonEmptyString(value, "name", 255);
         withCalled.put("name",!StringUtils.isBlank(this.name));
         return this;
      }

      public Builder withDescription(String value) {
         this.description = ArgumentUtility.validateOptionalAllowEmptyString(value, "description", 255);
         withCalled.put("description", this.description!= null);
         return this;
      }

      public Builder withType(Integer value) {
         List<Integer> typeOption = List.of(1, 2, 4);
         this.type = ArgumentUtility.validateOptionalEnum(value, "type", typeOption);
         withCalled.put("type", this.type!= null);
         return this;
      }

      public Builder withIcon(String value) {
         this.icon = ArgumentUtility.validateOptionalNonEmptyString(value, "icon", 255);
         withCalled.put("icon",!StringUtils.isBlank(this.icon));
         return this;
      }

      public Builder withSequence(Integer value) {
         this.sequence = ArgumentUtility.validateOptionalInteger(value, "sequence");
         withCalled.put("sequence", this.sequence!= null);
         return this;
      }

      public Builder withUrl(String value) {
         this.url = ArgumentUtility.validateOptionalNonEmptyString(value, "url", 255);
         withCalled.put("url",!StringUtils.isBlank(this.url));
         return this;
      }

      public Builder withStatus(Integer value) {
         List<Integer> statusOption = List.of(0, 1);
         this.status = ArgumentUtility.validateOptionalEnum(value, "status", statusOption);
         withCalled.put("status", this.status!= null);
         return this;
      }

      public MenuUpdateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         ArgumentUtility.checkAtLeastFieldList(withCalled, atLeastFieldList);
         return new MenuUpdateParam(id, name, description, type, icon, sequence, url, status);
      }
   }
}
