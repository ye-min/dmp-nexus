package com.wisdomsky.dmp.admin.model;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wisdomsky.dmp.library.common.ArgumentUtility;
import com.wisdomsky.dmp.library.model.RequestParam;

public class MessageCreateParam extends RequestParam {
   private Long id;
   private String ip;
   private String deviceCode;
   private String topic;
   private String tag;
   private String content;
   private OffsetDateTime sendTime;

   public MessageCreateParam(
      Long id,
      String ip,
      String deviceCode,
      String topic,
      String tag,
      String content,
      OffsetDateTime sendTime
   ) {
      this.id = id;
      this.ip = ip;
      this.deviceCode = deviceCode;
      this.topic = topic;
      this.tag = tag;
      this.content = content;
      this.sendTime = sendTime;
   }

   public Long getId() {
      return id;
   }

   public String getIp() {
      return ip;
   }

   public String getDeviceCode() {
      return deviceCode;
   }

   public String getTopic() {
      return topic;
   }

   public String getTag() {
      return tag;
   }

   public String getContent() {
      return content;
   }

   public OffsetDateTime getSendTime() {
      return sendTime;
   }

   public static class Builder {
      private Long id;
      private String ip;
      private String deviceCode;
      private String topic;
      private String tag;
      private String content;
      private OffsetDateTime sendTime;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();
      private List<String> requiredFieldList = Arrays.asList(
         "ip",
         "deviceCode",
         "topic",
         "tag",
         "content",
         "sendTime"
      );

      public Builder() {}

      public Builder withId(Long value) {
         this.id = ArgumentUtility.validateRequiredLong(value, "id");
         withCalled.put("id", true);
         return this;
      }

      public Builder withIp(String value) {
         this.ip = ArgumentUtility.validateRequiredString(value, "ip", -1);
         withCalled.put("ip", true);
         return this;
      }

      public Builder withDeviceCode(String value) {
         this.deviceCode = ArgumentUtility.validateRequiredString(value, "deviceCode", -1);
         withCalled.put("deviceCode", true);
         return this;
      }

      public Builder withTopic(String value) {
         this.topic = ArgumentUtility.validateRequiredString(value, "topic", -1);
         withCalled.put("topic", true);
         return this;
      }

      public Builder withTag(String value) {
         this.tag = ArgumentUtility.validateRequiredString(value, "tag", -1);
         withCalled.put("tag", true);
         return this;
      }

      public Builder withContent(String value) {
         this.content = ArgumentUtility.validateRequiredString(value, "content", -1);
         withCalled.put("content", true);
         return this;
      }

      public Builder withSendTime(OffsetDateTime value) {
         this.sendTime = ArgumentUtility.validateRequiredOffsetDateTime(value, "sendTime");
         withCalled.put("sendTime", true);
         return this;
      }

      public MessageCreateParam build() {
         ArgumentUtility.checkRequiredFieldList(withCalled, requiredFieldList);
         this.id = null;

         return new MessageCreateParam(
            this.id,
            this.ip,
            this.deviceCode,
            this.topic,
            this.tag,
            this.content,
            this.sendTime
            );
      }
   }
}