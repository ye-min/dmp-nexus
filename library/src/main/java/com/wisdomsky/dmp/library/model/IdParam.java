package com.wisdomsky.dmp.library.model;

import com.wisdomsky.dmp.library.exception.InvalidArgumentException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IdParam {
   private Long id;

   public IdParam(Long id) {
      this.id = id;
   }

   public IdParam() {
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getId() {
      return id;
   }

   public ObjectNode toJSONObject() {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode obj = objectMapper.createObjectNode();
      obj.put("id", id);
      return obj;
   }

   @Override
   public String toString() {
      return toJSONObject().toString();
   }

   public void validate() {
      if (id == null) {
         throw new InvalidArgumentException("The id field is required.");
      }
   }
}