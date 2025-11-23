package com.wisdomsky.dmp.archive.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wisdomsky.dmp.archive.common.TimestampUtility;
import com.wisdomsky.dmp.archive.exception.InvalidParameterException;

public class BaseController {

   protected final Logger logger = LoggerFactory.getLogger(this.getClass());

   protected JSONObject buildJSONResponse(int code, String message, JSONObject data) {
      JSONObject response = new JSONObject();
      response.put("errcode", code);
      response.put("errmsg", message);
      response.put("data", data);
      return response;
   }

   protected String buildResponse(int code, String message) {
      JSONObject response = new JSONObject();
      response.put("errcode", code);
      response.put("errmsg", message);
      return response.toJSONString();
   }

   /**
    * @param code
    * @param message
    * @param data
    * @return String
    */
   protected String buildResponse(int code, String message, JSONArray data) {
      JSONObject response = new JSONObject();
      response.put("errcode", code);
      response.put("errmsg", message);
      response.put("data", data);
      return response.toJSONString();
   }

   /**
    * @param code
    * @param message
    * @param data
    * @return String
    */
   protected String buildResponse(int code, String message, JSONObject data) {
      JSONObject response = new JSONObject();
      response.put("errcode", code);
      response.put("errmsg", message);
      response.put("data", data);
      return response.toJSONString();
   }

   protected int parseIntValue(JSONObject json, String key) throws InvalidParameterException {
      if (!json.containsKey(key)) {
         String errorMessage = String.format("Cannot find the parameter %s.", key);
         throw new InvalidParameterException(errorMessage);
      }
      try {
         int value = json.getIntValue(key);
         return value;
      } catch (Exception ex) {
         String errorMessage = String.format("The parameter %s has invalid value.", key);
         throw new InvalidParameterException(errorMessage);
      }
   }

   protected int parseIntValue(JSONObject json, String key, int defaultValue) throws InvalidParameterException {
      if (!json.containsKey(key)) {
         return defaultValue;
      }
      try {
         int value = json.getIntValue(key);
         return value;
      } catch (Exception ex) {
         String errorMessage = String.format("The parameter %s has invalid value.", key);
         throw new InvalidParameterException(errorMessage);
      }
   }

   protected String parseStringValue(JSONObject json, String key) throws InvalidParameterException {
      if (!json.containsKey(key)) {
         String errorMessage = String.format("Cannot find the parameter %s.", key);
         throw new InvalidParameterException(errorMessage);
      }
      try {
         String value = json.getString(key);
         return value;
      } catch (Exception ex) {
         String errorMessage = String.format("The parameter %s has invalid value.", key);
         throw new InvalidParameterException(errorMessage);
      }
   }

   protected String parseStringValue(JSONObject json, String key, String defaultValue) throws InvalidParameterException {
      if (!json.containsKey(key)) {
         return defaultValue;
      }
      try {
         String value = json.getString(key);
         return value;
      } catch (Exception ex) {
         String errorMessage = String.format("The parameter %s has invalid value.", key);
         throw new InvalidParameterException(errorMessage);
      }
   }

   /**
    * @param json
    * @return int
    * @throws InvalidParameterException
    */
   protected int parseQueryType(JSONObject json) throws InvalidParameterException {
      int value = 0;
      if (!json.containsKey("queryType")) {
         String errorMessage = "Cannot find the parameter quertType.";
         throw new InvalidParameterException(errorMessage);
      }
      try {
         value = json.getIntValue("queryType");
      } catch (Exception ex) {
         String errorMessage = "Invalid quertType.";
         throw new InvalidParameterException(errorMessage);
      }
      return value;
   }

   /**
    * @param json
    * @return int
    * @throws InvalidParameterException
    */
   protected int parseEmergencyTaskQueryType(JSONObject json) throws InvalidParameterException {
      int value = 0;
      if (!json.containsKey("queryType")) {
         String errorMessage = "Cannot find the parameter quertType.";
         throw new InvalidParameterException(errorMessage);
      }
      try {
         value = json.getIntValue("queryType");
      } catch (Exception ex) {
         String errorMessage = "Invalid quertType.";
         throw new InvalidParameterException(errorMessage);
      }

      if (value < 0 || value > 3) {
         String errorMessage = "Query type should be 0, 1, 2 or 3.";
         throw new InvalidParameterException(errorMessage);
      }
      return value;

   }

   /**
    * @param json
    * @param key
    * @param mode
    * @return Map<String, Integer>
    * @throws InvalidParameterException
    */
   // mode: 1: yyyy;
   // 2: yyyyMM;
   // 3: yyyyMMdd;
   protected Map<String, Integer> parseQueryDate(JSONObject json, String key, int mode)
         throws InvalidParameterException {
      int year = 0;
      int month = 0;
      int date = 0;
      Map<String, Integer> map = new HashMap<String, Integer>();

      String queryDate = json.getString(key);
      if (queryDate == null) {
         String errorMessage = String.format("Cannot find %s", key);
         throw new InvalidParameterException(errorMessage);
      }
      try {
         year = Integer.parseInt(queryDate.substring(0, 4));
      } catch (Exception ex) {
         String errorMessage = getQueryDateErrorMessage(key, mode, 1);
         throw new InvalidParameterException(errorMessage);
      }
      if (year < 2019 || year >= 2038) {
         String errorMessage = getQueryDateErrorMessage(key, mode, 1);
         errorMessage += "(2019 - 2038)";
         throw new InvalidParameterException(errorMessage);
      }
      map.put("year", year);

      if (mode > 1) {
         try {
            month = Integer.parseInt(queryDate.substring(4, 6));
         } catch (Exception ex) {
            String errorMessage = getQueryDateErrorMessage(key, mode, 2);
            throw new InvalidParameterException(errorMessage);
         }
         if (month < 1 || month > 12) {
            String errorMessage = getQueryDateErrorMessage(key, mode, 2);
            errorMessage += "(1 - 12)";
            throw new InvalidParameterException(errorMessage);
         }
         map.put("month", month);
      }

      if (mode > 2) {
         try {
            date = Integer.parseInt(queryDate.substring(6, 8));
         } catch (Exception ex) {
            String errorMessage = getQueryDateErrorMessage(key, mode, 3);
            throw new InvalidParameterException(errorMessage);
         }
         int maxDate = TimestampUtility.getDaysOfMonth(year, month);
         if (date < 1 || date > maxDate) {
            String errorMessage = getQueryDateErrorMessage(key, mode, 3);
            errorMessage += String.format("(1 - %d)", maxDate);
            throw new InvalidParameterException(errorMessage);
         }
         map.put("date", date);
      }

      return map;
   }

   /**
    * @param json
    * @param key
    * @param mode
    * @return Map<String, Integer>
    * @throws InvalidParameterException
    */
   // mode: 1: yyyy;
   // 2: yyyyMM;
   // 3: yyyyMMdd;
   // 4: yyyyMMddHH;
   // 5: yyyyMMddHHmm;
   protected Map<String, Integer> parseQueryDateTime(JSONObject json, String key, int mode)
         throws InvalidParameterException {
      String queryDateTime = json.getString(key);
      if (queryDateTime == null) {
         String errorMessage = String.format("Cannot find %s", key);
         throw new InvalidParameterException(errorMessage);
      }
      return parseQueryDateTime(key, queryDateTime, mode);
   }

   protected Map<String, Integer> parseQueryDateTime(String key, String value, int mode)
         throws InvalidParameterException {
      int year = 0;
      int month = 0;
      int date = 0;
      int hour = 0;
      int minute = 0;
      int second = 0;
      Map<String, Integer> map = new HashMap<String, Integer>();

      String queryDateTime = value;

      try {
         year = Integer.parseInt(queryDateTime.substring(0, 4));
      } catch (Exception ex) {
         String errorMessage = getQueryDateTimeErrorMessage(key, mode, 1);
         throw new InvalidParameterException(errorMessage);
      }
      if (year < 2019 || year >= 2038) {
         String errorMessage = getQueryDateTimeErrorMessage(key, mode, 1);
         throw new InvalidParameterException(errorMessage);
      }
      map.put("year", year);

      if (mode > 1) {
         try {
            month = Integer.parseInt(queryDateTime.substring(4, 6));
         } catch (Exception ex) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 2);
            throw new InvalidParameterException(errorMessage);
         }
         if (month < 1 || month > 12) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 2);
            throw new InvalidParameterException(errorMessage);
         }
         map.put("month", month);
      }

      if (mode > 2) {
         try {
            date = Integer.parseInt(queryDateTime.substring(6, 8));
         } catch (Exception ex) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 3);
            throw new InvalidParameterException(errorMessage);
         }
         int maxDate = TimestampUtility.getDaysOfMonth(year, month);
         if (date < 1 || date > maxDate) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 3);
            throw new InvalidParameterException(errorMessage);
         }
         map.put("date", date);
      }

      if (mode > 3) {
         try {
            hour = Integer.parseInt(queryDateTime.substring(8, 10));
         } catch (Exception ex) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 4);
            throw new InvalidParameterException(errorMessage);
         }
         if (hour < 0 || hour > 23) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 4);
            throw new InvalidParameterException(errorMessage);
         }
         map.put("hour", hour);
      }

      if (mode > 4) {
         try {
            minute = Integer.parseInt(queryDateTime.substring(10, 12));
         } catch (Exception ex) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 5);
            throw new InvalidParameterException(errorMessage);
         }
         if (minute < 0 || minute > 59) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 5);
            throw new InvalidParameterException(errorMessage);
         }

         map.put("minute", minute);
      }

      if (mode > 5) {
         try {
            second = Integer.parseInt(queryDateTime.substring(12, 14));
         } catch (Exception ex) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 6);
            throw new InvalidParameterException(errorMessage);
         }
         if (minute < 0 || minute > 59) {
            String errorMessage = getQueryDateTimeErrorMessage(key, mode, 6);
            throw new InvalidParameterException(errorMessage);
         }
         map.put("second", second);
      }

      return map;
   }

   protected String parseQueryDateTime(JSONObject json, String key, int mode, int outMode)
         throws InvalidParameterException {
      Map<String, Integer> startMap = parseQueryDateTime(json, key, mode);
      int year = startMap.get("year");
      int month = startMap.get("month");
      int date = startMap.get("date");
      String format = "%04d-%02d-%02d %02d:%02d:%02d";
      String queryDateTime = "";
      switch (outMode) {
         case 0:
            int hour = startMap.get("hour");
            int minute = startMap.get("minute");
            int second = startMap.get("second");
            format = "%04d-%02d-%02d %02d:%02d:%02d";
            queryDateTime = String.format(format, year, month, date, hour, minute, second);
            break;
         case 1:
            format = "%04d-%02d-%02d";
            queryDateTime = String.format(format, year, month, date);
            break;
      }

      return queryDateTime;
   }

   protected String parseQueryDateTime(String key, String value, int mode, int outMode)
         throws InvalidParameterException {
      Map<String, Integer> startMap = parseQueryDateTime(key, value, mode);
      int year = startMap.get("year");
      int month = startMap.get("month");
      int date = startMap.get("date");
      String format = "%04d-%02d-%02d %02d:%02d:%02d";
      String queryDateTime = "";
      switch (outMode) {
         case 0:
            int hour = startMap.get("hour");
            int minute = startMap.get("minute");
            int second = startMap.get("second");
            format = "%04d-%02d-%02d %02d:%02d:%02d";
            queryDateTime = String.format(format, year, month, date, hour, minute, second);
            break;
         case 1:
            format = "%04d-%02d-%02d";
            queryDateTime = String.format(format, year, month, date);
            break;
      }

      return queryDateTime;
   }

   protected long parseQueryDate(JSONObject json, String key)
         throws InvalidParameterException {
      String value = "";
      if (!json.containsKey(key)) {
         String errorMessage = String.format("Cannot find the parameter %s.", key);
         throw new InvalidParameterException(errorMessage);
      }
      try {
         value = json.getString(key);
         long timestamp = TimestampUtility.string2Timestamp(value, 3);
         return timestamp;
      } catch (Exception ex) {
         String errorMessage = String.format("Invalid %s.", key);
         throw new InvalidParameterException(errorMessage);
      }   
   }

   protected long parseQueryDateTime(JSONObject json, String key)
         throws InvalidParameterException {
      String value = "";
      if (!json.containsKey(key)) {
         String errorMessage = String.format("Cannot find the parameter %s.", key);
         throw new InvalidParameterException(errorMessage);
      }
      try {
         value = json.getString(key);
         long timestamp = TimestampUtility.string2Timestamp(value, 0);
         return timestamp;
      } catch (Exception ex) {
         String errorMessage = String.format("Invalid %s.", key);
         throw new InvalidParameterException(errorMessage);
      }   
   }

   /**
    * @param json
    * @param key
    * @param mode
    * @return Map<String, Integer>
    * @throws InvalidParameterException
    */
   // mode: 1: HH;
   // 2: HHmm;
   protected Map<String, Integer> parseQueryTime(JSONObject json, String key, int mode)
         throws InvalidParameterException {
      int hour = 0;
      int minute = 0;
      Map<String, Integer> map = new HashMap<String, Integer>();

      String queryTime = json.getString(key);
      if (queryTime == null) {
         String errorMessage = String.format("Cannot find %s", key);
         throw new InvalidParameterException(errorMessage);
      }

      try {
         hour = Integer.parseInt(queryTime.substring(0, 2));
      } catch (Exception ex) {
         String errorMessage = getQueryTimeErrorMessage(key, mode, 1);
         throw new InvalidParameterException(errorMessage);
      }
      if (hour < 0 || hour > 23) {
         String errorMessage = getQueryTimeErrorMessage(key, mode, 1);
         throw new InvalidParameterException(errorMessage);
      }
      map.put("hour", hour);

      if (mode > 1) {
         try {
            minute = Integer.parseInt(queryTime.substring(2, 4));
         } catch (Exception ex) {
            String errorMessage = getQueryTimeErrorMessage(key, mode, 2);
            throw new InvalidParameterException(errorMessage);
         }
         if (minute < 0 || minute > 59) {
            String errorMessage = getQueryTimeErrorMessage(key, mode, 2);
            throw new InvalidParameterException(errorMessage);
         }

         map.put("minute", minute);
      }

      return map;
   }

   protected int parseHourInterval(JSONObject json, String key) throws InvalidParameterException {

      int interval = json.getIntValue(key);
      if (interval == 0) {
         String errorMessage = String.format("Cannot find %s", key);
         throw new InvalidParameterException(errorMessage);
      }
      if (interval != 1 && interval != 2) {
         throw new InvalidParameterException("Invalid interval. The interval should be 1 or 2.");
      }
      return interval;
   }

   /**
    * @param json
    * @param key
    * @return int
    * @throws InvalidParameterException
    */
   protected int parseMinuteInterval(JSONObject json, String key) throws InvalidParameterException {
      int interval = json.getIntValue(key);
      if (interval == 0) {
         String errorMessage = String.format("Cannot find %s", key);
         throw new InvalidParameterException(errorMessage);
      }
      if (interval != 5 && interval != 15 && interval != 30) {
         throw new InvalidParameterException("Invalid interval. The interval should be 5, 15 or 30.");
      }
      return interval;
   }

   /**
    * @param key
    * @param mode
    * @param position
    * @return String
    */
   private String getQueryDateTimeErrorMessage(String key, int mode, int position) {
      String format = "";
      String section = "";
      switch (mode) {
         case 1:
            format = "yyyy";
            break;
         case 2:
            format = "yyyyMM";
            break;
         case 3:
            format = "yyyyMMdd";
            break;
         case 4:
            format = "yyyyMMddHH";
            break;
         case 5:
            format = "yyyyMMddHHmm";
            break;
         case 6:
            format = "yyyyMMddHHmmss";
            break;
      }
      switch (position) {
         case 1:
            section = "yyyy";
            break;
         case 2:
            section = "MM";
            break;
         case 3:
            section = "dd";
            break;
         case 4:
            section = "HH";
            break;
         case 5:
            section = "mm";
            break;
         case 6:
            section = "ss";
            break;
      }

      String errorMessage = String.format("Invalid %s. It should be %s. Please check the value of %s", key, format,
            section);
      return errorMessage;
   }

   /**
    * @param key
    * @param mode
    * @param position
    * @return String
    */
   private String getQueryDateErrorMessage(String key, int mode, int position) {
      String format = "";
      String section = "";
      switch (mode) {
         case 1:
            format = "yyyy";
            break;
         case 2:
            format = "yyyyMM";
            break;
         case 3:
            format = "yyyyMMdd";
            break;
      }
      switch (position) {
         case 1:
            section = "yyyy";
            break;
         case 2:
            section = "MM";
            break;
         case 3:
            section = "dd";
            break;
      }

      String errorMessage = String.format("Invalid %s. It should be %s. Please check the value of %s", key, format,
            section);
      return errorMessage;
   }

   /**
    * @param key
    * @param mode
    * @param position
    * @return String
    */
   private String getQueryTimeErrorMessage(String key, int mode, int position) {
      String format = "";
      String section = "";
      switch (mode) {
         case 1:
            format = "HH";
            break;
         case 2:
            format = "HHmm";
            break;
      }
      switch (position) {
         case 1:
            section = "HH";
            break;
         case 2:
            section = "mm";
            break;

      }

      String errorMessage = String.format("Invalid %s. It should be %s. Please check the value of %s", key, format,
            section);
      return errorMessage;
   }

   protected float formatFloat(Float value, int scale, float defaultValue) {
      if (value == null) {
         return defaultValue;
      }
      BigDecimal bd =  new BigDecimal((double)value);
      bd = bd.setScale(scale, RoundingMode.HALF_UP);
      float ft = bd.floatValue();
      return ft;
   }

   protected double formatDouble(Double value, int scale, double defaultValue) {
      if (value == null) {
         return defaultValue;
      }
      BigDecimal bd =  new BigDecimal((double)value);
      bd = bd.setScale(scale, RoundingMode.HALF_UP);
      double doubleValue= bd.doubleValue();
      return doubleValue;
   }
}