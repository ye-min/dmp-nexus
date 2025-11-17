package com.wisdomsky.dmp.security.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.wisdomsky.dmp.library.common.CryptoUtility;
import com.wisdomsky.dmp.library.common.ArgumentUtility;
import com.wisdomsky.dmp.library.model.RequestParam;

public class AccountCreateParam extends RequestParam {
   private Long id;
   private String code;
   private String name;
   private String password;
   private Integer type;
   private Integer status;
   private String mobile;
   private String email;
   private String avatar;

   public AccountCreateParam(
      Long id,
      String code,
      String name,
      String password,
      Integer type,
      Integer status,
      String mobile,
      String email,
      String avatar) {
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
      @Value("${tjtc.default-password:123456}")
      private String defaultPassword;

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
      private List<String> requiredFieldList = Arrays.asList(
         "code",
         "name");

      public Builder() {}

      public Builder withCode(String value) {
         this.code = ArgumentUtility.validateRequiredString(value, "code", 32);
         withCalled.put("code", true);
         return this;
      }

      public Builder withName(String value) {
         this.name = ArgumentUtility.validateRequiredString(value, "name", 32);
         withCalled.put("name", true);
         return this;
      }

      public Builder withType(Integer value) {
         List<Integer> typeOption = List.of(1, 2, 4);
         this.type = ArgumentUtility.validateRequiredEnum(value, "type", typeOption);
         withCalled.put("type", true);
         return this;
      }

      public Builder withMobile(String value) {
         this.mobile = ArgumentUtility.validateDefaultString(value, "mobile", 255, "");
         withCalled.put("mobile", true);
         return this;
      }

      public Builder withEmail(String value) {
         this.email = ArgumentUtility.validateDefaultString(value, "email", 255, "");
         withCalled.put("email", true);
         return this;
      }

      public Builder withAvatar(String value) {
         this.avatar = ArgumentUtility.validateDefaultString(value, "avatar", 255, "");
         withCalled.put("avatar", true);
         return this;
      }

      public AccountCreateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         this.id = null;
         this.status = 1;
         if (defaultPassword == null) {
            defaultPassword = "123456";
         }
         this.password = CryptoUtility.sha1(defaultPassword);
         if (this.password == null) {
            this.password = "7c4a8d09ca3762af61e59520943dc26494f8941b";
         }
         return new AccountCreateParam(
            this.id,
            this.code,
            this.name,
            this.password,
            this.type,
            this.status,
            this.mobile,
            this.email,
            this.avatar
            );
      }
   }
}