package com.wisdomsky.dmp.security.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.wisdomsky.dmp.library.common.ArgumentUtility;

public class AccountUpdateParam {
   private Long id;
   private String code;
   private String name;
   private String password;
   private Integer type;
   private Integer status;
   private String mobile;
   private String email;
   private String avatar;

   public AccountUpdateParam(Long id, String code, String name, String password, int type, int status, String mobile, String email, String avatar) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.password = password;
      this.type = type;
      this.status = status;
      this.mobile = mobile;
      this.email = email;
      this.avatar = avatar;
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
   
   public static class Builder {
      private Long id;
      private String code;
      private String name;
      private String password;
      private Integer type;
      private Integer status;
      private String mobile;
      private String email;
      private String avatar;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();
      private List<String> requiredFieldList = Arrays.asList("id");
      private List<String> atLeastFieldList = Arrays.asList("code", "name", "password", "type", "status", "mobile", "email", "avatar");

      public Builder() {}

      public Builder withId(Long value) {
         this.id = ArgumentUtility.validateRequiredLong(value, "id");
         withCalled.put("id", true);
         return this;
      }

      public Builder withCode(String value) {
         this.code = ArgumentUtility.validateRequiredString(value, "code", 32);
         withCalled.put("code", !StringUtils.isBlank(this.code));
         return this;
      }

      public Builder withName(String value) {
         this.name = ArgumentUtility.validateRequiredString(value, "name", 32);
         withCalled.put("name", !StringUtils.isBlank(this.name));
         return this;
      }

      public Builder withPassword(String value) {
         this.password = ArgumentUtility.validateOptionalNonEmptyString(value, "password", 0);
         withCalled.put("password", true);
         return this;
      }

      public Builder withType(Integer value) {
         List<Integer> typeOption = List.of(1, 2, 4);
         this.type = ArgumentUtility.validateRequiredEnum(value, "type", typeOption);
         withCalled.put("type", this.type != null);
         return this;
      }

      public Builder withStatus(Integer value) {
         List<Integer> statusOption = List.of(0, 1);
         this.status = ArgumentUtility.validateOptionalEnum(value, "status", statusOption);
         withCalled.put("status", this.status != null);
         return this;
      }

      public Builder withMobile(String value) {
         this.mobile = ArgumentUtility.validateOptionalAllowEmptyString(value, "mobile", 255);
         withCalled.put("mobile", this.mobile != null);
         return this;
      }

      public Builder withEmail(String value) {
         this.email = ArgumentUtility.validateOptionalAllowEmptyString(value, "email", 255);
         withCalled.put("email", this.email != null);
         return this;
      }

      public Builder withAvatar(String value) {
         this.avatar = ArgumentUtility.validateOptionalAllowEmptyString(value, "avatar", 255);
         withCalled.put("avatar", this.avatar != null);
         return this;
      }

      public AccountUpdateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         ArgumentUtility.checkAtLeastFieldList(withCalled, atLeastFieldList);
         return new AccountUpdateParam(
            this.id,
            this.code,
            this.name,
            this.password,
            this.type,
            this.status,
            this.mobile,
            this.email,
            this.avatar);
      }
   }
}