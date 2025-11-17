package com.wisdomsky.dmp.security.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Menu {
   private long id;
   private String code;
   private String name;
   private String description;
   private int type;
   private String icon;
   private int sequence;
   private String url;
   private String path;
   private int status;
   private int level;
   private int parentId;
   private List<Menu> children;
   private OffsetDateTime recordCreatedTime;
   private OffsetDateTime recordUpdatedTime;

   public Menu(long id, String code, String name, String description, int type, String icon,
         int sequence, String url, String path, int status, int level, int parentId,
         List<Menu> children, OffsetDateTime recordCreatedTime, OffsetDateTime recordUpdatedTime) {
      this.id = id;
      this.code = code;
      this.name = name;
      this.description = description;
      this.type = type;
      this.icon = icon;
      this.sequence = sequence;
      this.url = url;
      this.path = path;
      this.status = status;
      this.level = level;
      this.parentId = parentId;
      this.children = children;
      this.recordCreatedTime = recordCreatedTime;
      this.recordUpdatedTime = recordUpdatedTime;
   }

   public Menu() {
   }

   public void setId(int id) {
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

   public void setType(int type) {
      this.type = type;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   public void setSequence(int sequence) {
      this.sequence = sequence;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public void setPath(String path) {
      this.path = path;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public void setLevel(int level) {
      this.level = level;
   }

   public void setParentId(int parentId) {
      this.parentId = parentId;
   }

   public void setChildren(List<Menu> children) {
      this.children = children;
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

   public String getDescription() {
      return description;
   }

   public int getType() {
      return type;
   }

   public String getIcon() {
      return icon;
   }

   public int getSequence() {
      return sequence;
   }

   public String getUrl() {
      return url;
   }

   public String getPath() {
      return path;
   }

   public int getStatus() {
      return status;
   }

   public int getLevel() {
      return level;
   }

   public int getParentId() {
      return parentId;
   }

   public List<Menu> getChildren() {
      return children;
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
      obj.put("description", description);
      obj.put("type", type);
      obj.put("icon", icon);
      obj.put("sequence", sequence);
      obj.put("url", url);
      obj.put("path", path);
      obj.put("status", status);
      obj.put("level", level);
      return obj;
   }

   public ObjectNode toLoginJSONObject() {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode obj = objectMapper.createObjectNode();
      obj.put("id", id);
      obj.put("code", code);
      obj.put("name", name);
      obj.put("description", description);
      obj.put("type", type);
      obj.put("status", status);
      obj.put("icon", icon);
      obj.put("sequence", sequence);
      obj.put("url", url);
      return obj;
   }

   @Override
   public String toString() {
      return toJSONObject().toString();
   }

   @Override
   public boolean equals(Object o) {
      if (this == o)
         return true;
      if (o == null || getClass() != o.getClass())
         return false;
      Menu item = (Menu) o;
      return id == item.id &&
            Objects.equals(code, item.code) &&
            Objects.equals(name, item.name) &&
            Objects.equals(description, item.description) &&
            type == item.type &&
            Objects.equals(icon, item.icon) &&
            sequence == item.sequence &&
            Objects.equals(url, item.url) &&
            Objects.equals(path, item.path) &&
            status == item.status &&
            level == item.level &&
            Objects.equals(children, item.children);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, code, name, description, type, icon,
            sequence, url, path, status, level, children);
   }

   
}
