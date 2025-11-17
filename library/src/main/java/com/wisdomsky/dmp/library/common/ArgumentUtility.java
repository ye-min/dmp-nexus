package com.wisdomsky.dmp.library.common;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.wisdomsky.dmp.library.exception.InvalidArgumentException;
import com.wisdomsky.dmp.library.model.PageParam;

public class ArgumentUtility {
   public static int parsePageOffset(PageParam param) {
      Integer limit = 10;
      Integer index = 1;
      if (param != null) {
         limit = param.getSize() == null ? 10 : param.getSize();
         index = param.getIndex() == null ? 1 : param.getIndex();
      }
      limit = limit < 0 ? 10 : limit;
      index = index < 1 ? 1 : index;
      int offset = (index - 1) * limit;
      return offset;
   }

   public static int parsePageLimit(PageParam param) {
      Integer limit = 10;
      if (param != null) {
         limit = param.getSize() == null ? 10 : param.getSize();
      }
      limit = limit < 0 ? 10 : limit;
      return limit;
   }

   // Disallow null and empty string
   // Limit length
   public static String validateRequiredString(String value, String name, int length) {
      if (StringUtils.isBlank(value)) {
         throw new InvalidArgumentException(String.format("The value of parameter %s cannot be empty.", name));
      }
      if (length > 0 && value.length() > length) {
         throw new InvalidArgumentException(String.format("The length of parameter %s cannot exceed 255 characters.", name));
      }
      return value;
   }

   public static List<String> validateRequiredStringList(List<String> value, String name) {
      if (value.size() == 0) {
         throw new InvalidArgumentException(String.format("The value of parameter %s cannot be empty.", name));
      }
      return value;
   }

   // Disallow null
   public static Integer validateRequiredInteger(Integer value, String name) {
      if (value == null) {
         throw new InvalidArgumentException(String.format("The value of parameter %s is required.", name));
      }
      return value;
   }

   public static Double validateRequiredDouble(Double value, String name) {
      if (value == null) {
         throw new InvalidArgumentException(String.format("The value of parameter %s is required.", name));
      }
      return value;
   }

   public static Long validateRequiredLong(Long value, String name) {
      if (value == null) {
         throw new InvalidArgumentException(String.format("The value of parameter %s is required.", name));
      }
      return value;
   }

   public static BigDecimal validateRequiredBigDecimal(BigDecimal value, String name) {
      if (value == null) {
         throw new InvalidArgumentException(String.format("The value of parameter %s is required.", name));
      }
      return value;
   }

   public static OffsetDateTime validateRequiredOffsetDateTime(OffsetDateTime value, String name) {
      if (value == null) {
         throw new InvalidArgumentException(String.format("The value of parameter %s cannot be empty.", name));
      }
      return value;
   }

   // Diallow null
   // Limit options
   public static Integer validateRequiredEnum(Integer value, String name, List<Integer> optionList) {
      if (value == null) {
         throw new InvalidArgumentException(String.format("The value of parameter %s is required.", name));
      }
      if (!optionList.contains(value)) {
         String optionString = optionList.stream().map(String::valueOf).collect(Collectors.joining(", "));
         throw new InvalidArgumentException(String.format("The value of parameter %s should be %s.", name, optionString));
      }
      return value;
   }

   // Allow null and empty string
   // Null and empty string will be converted to default string
   // Limit length
   public static String validateDefaultString(String value, String name, int length, String defaultValue) {
      if (StringUtils.isBlank(value)) {
         return defaultValue;
      }
      if (length > 0 && value.length() > length) {
         throw new InvalidArgumentException(String.format("The length of parameter %s cannot exceed 255 characters.", name));
      }
      return value;
   }

   // Allow null
   // Disallow empth string
   // Limit length
   public static String validateOptionalNonEmptyString(String value, String name, int length) {
      if (value == null) {
         return value;
      }
      if (value.length() == 0) {
         throw new InvalidArgumentException(String.format("The value of parameter %s cannot be empty.", name));
      }
      if (length > 0 && value.length() > length) {
         throw new InvalidArgumentException(String.format("The length of parameter %s cannot exceed 255 characters.", name));
      }
      return value;
   }

   // Allow null
   // Disallow empth string
   // Limit length
   public static String validateOptionalAllowEmptyString(String value, String name, int length) {
      if (value == null) {
         return value;
      }
      if (length > 0 && value.length() > length) {
         throw new InvalidArgumentException(String.format("The length of parameter %s cannot exceed 255 characters.", name));
      }
      return value;
   }

   // Disallow null
   public static Integer validateOptionalInteger(Integer value, String name) {
      return value;
   }

   // Disallow null
   public static Long validateOptionalLong(Long value, String name) {
      return value;
   }

   // Disallow null
   public static BigDecimal validateOptionalBigDecimal(BigDecimal value, String name) {
      return value;
   }

   // Allow null
   // Limit options
   // Update Param Optional Field
   public static Integer validateOptionalEnum(Integer value, String name, List<Integer> optionList) {
      if (value == null) {
         return value;
      }
      if (!optionList.contains(value)) {
         String optionString = optionList.stream().map(String::valueOf).collect(Collectors.joining(", "));
         throw new InvalidArgumentException(String.format("The value of parameter %s should be %s.", name, optionString));
      }
      return value;
   }

   public static List<Integer> validateOptionalEnumList(List<Integer> value, String name) {
      if (value.contains(null)) {
         throw new InvalidArgumentException(String.format("The value of parameter %s contains null.", name));
      }
      return value;
   }

   public static void checkRequiredFieldList(Map<String, Boolean> withCalled, List<String> requiredFieldList) {
      boolean allRequiredFieldsSet = requiredFieldList.stream().allMatch(field -> withCalled.getOrDefault(field, false));
      if (!allRequiredFieldsSet) {
         String fieldsString = String.join(", ", requiredFieldList);
         if (requiredFieldList.size() > 1) {
            List<String> setFields = withCalled.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
            String setFieldsString = String.join(", ", setFields);
            throw new InvalidArgumentException(
                String.format("The fields %s should be assigned a value. Currently set fields: %s", 
                    fieldsString, setFieldsString));
         } else {
            List<String> setFields = withCalled.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
            String setFieldsString = String.join(", ", setFields);
            throw new InvalidArgumentException(
                String.format("The field %s should be assigned a value. Currently set fields: %s", 
                    fieldsString, setFieldsString));
         }
      }
   }

   public static void checkAtLeastFieldList(Map<String, Boolean> withCalled, List<String> atLeastFieldList) {
      boolean atLeastOneFieldSet = atLeastFieldList.stream().anyMatch(field -> withCalled.getOrDefault(field, false));
      if (!atLeastOneFieldSet) {
         String fieldsString = String.join(", ", atLeastFieldList);
         if (atLeastFieldList.size() > 1) {
            throw new InvalidArgumentException(String.format("At least one of the filed %s should be assigned a value.", fieldsString));
         } else {
            throw new InvalidArgumentException(String.format("The filed %s should be assigned a value.", fieldsString));
         }
      }
   }
}
