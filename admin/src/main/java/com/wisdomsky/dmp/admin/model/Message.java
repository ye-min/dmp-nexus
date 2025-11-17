package com.wisdomsky.dmp.admin.model;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Message {
   @Value("${tjtc.date-time-format:yyyy-MM-dd HH:mm:ss}")
   private static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

   private int id;
   private String ip;
   private String deviceCode;
   private String topic;
   private String tag;
   private String content;
   private OffsetDateTime sendTime;

   private OffsetDateTime recordCreatedTime;

   public Message(
      int id,
      String ip,
      String deviceCode,
      String topic,
      String tag,
      String content,
      OffsetDateTime sendTime,
      OffsetDateTime recordCreatedTime
   ) {
      this.id = id;
      this.ip = ip;
      this.deviceCode = deviceCode;
      this.topic = topic;
      this.tag = tag;
      this.content = content;
      this.sendTime = sendTime;
      this.recordCreatedTime = recordCreatedTime;
   }

   public Message() {
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getId() {
      return id;
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public String getIp() {
      return ip;
   }

   public void setDeviceCode(String deviceCode) {
      this.deviceCode = deviceCode;
   }

   public String getDeviceCode() {
      return deviceCode;
   }

   public void setTopic(String topic) {
      this.topic = topic;
   }

   public String getTopic() {
      return topic;
   }

   public void setTag(String tag) {
      this.tag = tag;
   }

   public String getTag() {
      return tag;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getContent() {
      return content;
   }

   public void setSendTime(OffsetDateTime sendTime) {
      this.sendTime = sendTime;
   }

   public OffsetDateTime getSendTime() {
      return sendTime;
   }

   public void setRecordCreatedTime(OffsetDateTime recordCreatedTime) {
      this.recordCreatedTime = recordCreatedTime;
   }

   public OffsetDateTime getRecordCreatedTime() {
      return recordCreatedTime;
   }

   public ObjectNode toJSONObject() {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode obj = objectMapper.createObjectNode();
    
      // 添加字段到 ObjectNode
      obj.put("id", id);
      obj.put("ip", ip);
      obj.put("deviceCode", deviceCode);
      obj.put("topic", topic);
      obj.put("tag", tag);
      obj.put("content", content);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
      String formattedSendSendTime = sendTime != null
            ? sendTime.atZoneSameInstant(ZoneId.systemDefault()).format(formatter)
            : null;
      obj.put("sendTime", formattedSendSendTime);

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
      Message item = (Message) o;
      return id == item.id &&
            Objects.equals(ip, item.ip) &&
            Objects.equals(deviceCode, item.deviceCode) &&
            Objects.equals(topic, item.topic) &&
            Objects.equals(tag, item.tag) &&
            Objects.equals(content, item.content) &&
            Objects.equals(sendTime, item.sendTime) &&
            Objects.equals(recordCreatedTime, item.recordCreatedTime);
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         id,
         ip,
         deviceCode,
         topic,
         tag,
         content,
         sendTime,
         recordCreatedTime
      );
   }
}