package com.wisdomsky.dmp.archive.common;

import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.exception.InvalidParameterException;

public class JSONUtility {

   public static long parseLongJson(JSONObject json, String field) throws InvalidParameterException {
      try {
         long value = json.getLong(field);
         return value;
      }
      catch (Exception ex) {
         String message = String.format("Invalid %s value.", field);
         throw new InvalidParameterException(message);
      }
   }

   public static int parseIntegerJson(JSONObject json, String field) throws InvalidParameterException {
      try {
         int value = json.getInteger(field);
         return value;
      }
      catch (Exception ex) {
         String message = String.format("Invalid %s value.", field);
         throw new InvalidParameterException(message);
      }
   }

   public static String parseStringJson(JSONObject json, String field) throws InvalidParameterException {
      try {
         String value = json.getString(field);
         return value;
      }
      catch (Exception ex) {
         String message = String.format("Invalid %s value.", field);
         throw new InvalidParameterException(message);
      }
   }
}