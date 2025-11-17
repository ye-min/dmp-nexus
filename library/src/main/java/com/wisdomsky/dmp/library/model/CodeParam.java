package com.wisdomsky.dmp.library.model;

import org.apache.commons.lang3.StringUtils;

import com.wisdomsky.dmp.library.exception.InvalidArgumentException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CodeParam {
   private String code;

   public CodeParam(String code) {
      this.code = code;
   }

   public CodeParam() {
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getCode() {
      return code;
   }

   public ObjectNode toJSONObject() {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode obj = objectMapper.createObjectNode();
      obj.put("code", code);
      return obj;
   }

   @Override
   public String toString() {
      return toJSONObject().toString();
   }

   public void validate() {
      if (StringUtils.isBlank(code)) {
         throw new InvalidArgumentException("The value of the code cannot be empty.");
      }
      if (code.length() > 32) {
         throw new InvalidArgumentException("The length of the code cannot exceed 32 characters.");
      }
   }
}