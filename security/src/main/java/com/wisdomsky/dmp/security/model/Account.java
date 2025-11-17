package com.wisdomsky.dmp.security.model;

import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.OffsetDateTime;

public class Account {
   private long id;
   private String code;
   private String name;
   private String password;
   private int type;
   private int status;
   private String mobile;
   private String email;
   private String avatar;
   private OffsetDateTime recordCreatedTime;
   private OffsetDateTime recordUpdatedTime;

   public Account(
      long id,
      String code,
      String name,
      String password,
      int type,
      int status,
      String mobile,
      String email,
      String avatar,
      OffsetDateTime recordCreatedTime,
      OffsetDateTime recordUpdatedTime) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.password = password;
      this.type = type;
      this.status = status;
      this.mobile = mobile;
      this.email = email;
      this.avatar = avatar;
      this.recordCreatedTime = recordCreatedTime;
      this.recordUpdatedTime = recordUpdatedTime;
   }

   public Account() {
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setType(int type) {
      this.type = type;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public void setMobile(String mobile) {
      this.mobile = mobile;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public void setRecordCreatedTime(OffsetDateTime recordCreatedTime) {
      this.recordCreatedTime = recordCreatedTime;
   }

   public void setRecordUpdatedTime(OffsetDateTime recordUpdatedTime) {
      this.recordUpdatedTime = recordUpdatedTime;
   }

   public long getId() {
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
   
   public int getType() {
      return type;
   }
   
   public int getStatus() {
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
   
   public OffsetDateTime getRecordCreatedTime() {
      return recordCreatedTime;
   }
   
   public OffsetDateTime getRecordUpdatedTime() {
      return recordUpdatedTime;
   }

   public ObjectNode toJSONObject() {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode obj = objectMapper.createObjectNode();

      obj.put("id", id);
      obj.put("code", code);
      obj.put("name", name);
      obj.put("type", type);
      obj.put("status", status);
      obj.put("mobile", mobile);
      obj.put("email", email);
      obj.put("avatar", avatar);

      return obj;
   }

   @Override
   public String toString() {
      return toJSONObject().toString();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Account item = (Account) o;
      return id == item.id &&
            Objects.equals(code, item.code) &&
            Objects.equals(name, item.name) &&
            Objects.equals(password, item.password) &&
            type == item.type &&
            status == item.status &&
            Objects.equals(mobile, item.mobile) &&
            Objects.equals(email, item.email) &&
            Objects.equals(avatar, item.avatar) &&
            Objects.equals(recordCreatedTime, item.recordCreatedTime) &&
            Objects.equals(recordUpdatedTime, item.recordUpdatedTime);
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         id,
         code,
         name,
         password,
         type,
         status,
         mobile,
         email,
         avatar,
         recordCreatedTime,
         recordUpdatedTime);
   }
}