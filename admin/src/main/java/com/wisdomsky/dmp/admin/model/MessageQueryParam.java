package com.wisdomsky.dmp.admin.model;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import com.wisdomsky.dmp.library.model.RequestParam;

public class MessageQueryParam extends RequestParam {
   private Long idEqual;
   private String ipEqual;
   private String deviceCodeEqual;
   private String topicEqual;
   private String tagEqual;
   private String contentLike;
   private OffsetDateTime sendTimeBefore;
   private OffsetDateTime sendTimeAfter;

   public MessageQueryParam() {}

   public MessageQueryParam(
      Long idEqual,
      String ipEqual,
      String deviceCodeEqual,
      String topicEqual,
      String tagEqual,
      String contentLike,
      OffsetDateTime sendTimeBefore,
      OffsetDateTime sendTimeAfter
   ) {
      this.idEqual = idEqual;
      this.ipEqual = ipEqual;
      this.deviceCodeEqual = deviceCodeEqual;
      this.topicEqual = topicEqual;
      this.tagEqual = tagEqual;
      this.contentLike = contentLike;

      this.sendTimeBefore = sendTimeBefore;
      this.sendTimeAfter = sendTimeAfter;
   }

   public Long getIdEqual() {
      return idEqual;
   }
   public String getIpEqual() {
      return ipEqual;
   }
   public String getDeviceCodeEqual() {
      return deviceCodeEqual;
   }
   public String getTopicEqual() {
      return topicEqual;
   }
   public String getTagEqual() {
      return tagEqual;
   }
   public String getContentLike() {
      return contentLike;
   }
   public OffsetDateTime getSendTimeBefore() {
      return sendTimeBefore;
   }
   public OffsetDateTime getSendTimeAfter() {
      return sendTimeAfter;
   }

   public static class Builder {
      private Long idEqual;
      private String ipEqual;
      private String deviceCodeEqual;
      private String topicEqual;
      private String tagEqual;
      private String contentLike;
      private OffsetDateTime sendTimeBefore;
      private OffsetDateTime sendTimeAfter;

      private Map<String, Boolean> withCalled = new HashMap<String, Boolean>();

      public Builder() {
      }

      public Builder withIdEqual(Long idEqual) {
         this.idEqual = idEqual;
         withCalled.put("idEqual", true);
         return this;
      }

      public Builder withIpEqual(String ipEqual) {
         this.ipEqual = ipEqual;
         withCalled.put("ipEqual", true);
         return this;
      }

      public Builder withDeviceCodeEqual(String deviceCodeEqual) {
         this.deviceCodeEqual = deviceCodeEqual;
         withCalled.put("deviceCodeEqual", true);
         return this;
      }

      public Builder withTopicEqual(String topicEqual) {
         this.topicEqual = topicEqual;
         withCalled.put("topicEqual", true);
         return this;
      }

      public Builder withTagEqual(String tagEqual) {
         this.tagEqual = tagEqual;
         withCalled.put("tagEqual", true);
         return this;
      }

      public Builder withContentLike(String contentLike) {
         this.contentLike = contentLike;
         withCalled.put("contentLike", true);
         return this;
      }

      public Builder withSendTimeBefore(OffsetDateTime sendTimeBefore) {
         this.sendTimeBefore = sendTimeBefore;
         withCalled.put("sendTimeBefore", true);
         return this;
      }

      public Builder withSendTimeAfter(OffsetDateTime sendTimeAfter) {
         this.sendTimeAfter = sendTimeAfter;
         withCalled.put("sendTimeAfter", true);
         return this;
      }

      public MessageQueryParam build() {
         return new MessageQueryParam(
            idEqual,
            ipEqual,
            deviceCodeEqual,
            topicEqual,
            tagEqual,
            contentLike,
            sendTimeBefore,
            sendTimeAfter
         );
      }
   }
}
