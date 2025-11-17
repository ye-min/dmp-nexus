package com.wisdomsky.dmp.security.model;

import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Permission {
   private long id;
   private String code;
   private String name;
   private String description;
   private int status;
   private OffsetDateTime recordCreatedTime;
   private OffsetDateTime recordUpdatedTime;

   public Permission(
      long id,
      String code,
      String name,
      String description,
      int status,
      OffsetDateTime recordCreatedTime,
      OffsetDateTime recordUpdatedTime) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.description = description;
      this.status = status;
      this.recordCreatedTime = recordCreatedTime;
      this.recordUpdatedTime = recordUpdatedTime;
   }

   public Permission() {
   }

   public void setId(long id) {
      this.id = id;
   }
   public void setCode(String code) {
      this.code = code;
   }
   public void setName(String name) {
      this.name = name;
   }
   public void setDescription(String description) {
      this.description = description;
   }
   public void setStatus(int status) {
      this.status = status;
   }
   public void setCreatedTime(OffsetDateTime recordCreatedTime) {
      this.recordCreatedTime = recordCreatedTime;
   }
   public void setUpdatedTime(OffsetDateTime recordUpdatedTime) {
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
   public String getDescription() {
      return description;
   }
   public int getStatus() {
      return status;
   }
   public OffsetDateTime getCreatedTime() {
      return recordCreatedTime;
   }
   public OffsetDateTime getUpdatedTime() {
      return recordUpdatedTime;
   }

   public ObjectNode toJSONObject() {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode obj = objectMapper.createObjectNode();

      obj.put("id", id);
      obj.put("code", code);
      obj.put("name", name);
      obj.put("description", description);
      obj.put("status", status);
      return obj;
   }

   @Override
   public String toString() {
      return toJSONObject().toString();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass()!= o.getClass()) return false;
      Permission item = (Permission) o;
      return id == item.id &&
            Objects.equals(code, item.code) &&
            Objects.equals(name, item.name) &&
            Objects.equals(description, item.description) &&
            status == item.status;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, code, name, description, status);
   }
}
