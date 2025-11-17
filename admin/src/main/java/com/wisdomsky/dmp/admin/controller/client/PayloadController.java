package com.wisdomsky.dmp.admin.controller.client;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.admin.model.Message;
import com.wisdomsky.dmp.admin.model.MessageCreateParam;
import com.wisdomsky.dmp.admin.model.MessageQueryParam;
import com.wisdomsky.dmp.admin.service.MessageService;
import com.wisdomsky.dmp.library.common.ArgumentUtility;
import com.wisdomsky.dmp.library.exception.InvalidArgumentException;
import com.wisdomsky.dmp.library.model.PageParam;
import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.wisdomsky.dmp.library.model.SortField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController("clientPayloadController")
@RequestMapping("/client/payload")
public class PayloadController {
   private static final Logger logger = LoggerFactory.getLogger(PayloadController.class);
   private static final String alias = "/client/payload";
   private static final Set<String> VALID_SORT_FIELDS = Set.of("id", "ip", "topic", "tag", "content", "sendTime");

   @Value("${tjtc.date-time-format:yyyy-MM-dd HH:mm:ss}")
	private static String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

   @Autowired
   private MessageService service;

   @PostMapping(value = "/create", produces = "application/json;charset=UTF-8")
    public ResponseTemplate<ObjectNode> create(
            HttpServletRequest httpRequest,
            @RequestBody CreateRequest request) {

        logger.info("[{}/create] is called with {}", alias, request.toString());
        int code = 0;
        String message = "success";

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.createObjectNode();
        ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

        String ip = httpRequest.getRemoteAddr();

        ZoneId zoneId = ZoneId.systemDefault();
        OffsetDateTime offsetDateTime = request.getSendTime() != null
                ? request.getSendTime().atZone(zoneId).toOffsetDateTime()
                : null;

        MessageCreateParam param = new MessageCreateParam.Builder()
                .withIp(ip)
                .withDeviceCode(request.getDeviceCode())
                .withTopic(request.getTopic())
                .withTag(request.getTag())
                .withContent(request.getContent())
                .withSendTime(offsetDateTime)
                .build();
        code = service.create(param);
        long id = param.getId();
        data.put("id", id);
        response.setData(data);

        response.setCode(code);
        response.setMessage(message);
        return response;
   }

   @PostMapping(value = "/delete", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> delete(
         @RequestBody DetailRequest request) {

      logger.info("[{}/delete] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";
      
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      MessageQueryParam queryParam = new MessageQueryParam.Builder()
            .withIdEqual(request.getId())
            .build();
      service.delete(queryParam);
      response.setCode(code);
      response.setMessage(message);
      return response;
   }

   @PostMapping(value = "/detail", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> detail(
         @RequestBody DetailRequest request) {

      logger.info("[{}/detail] is called with {}", alias, request.toString());
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      MessageQueryParam param = new MessageQueryParam.Builder()
            .withIdEqual(request.getId())
            .build();
      List<SortField> sortFieldList = new ArrayList<SortField>();
      Integer offset = null;
      Integer limit = null;
      List<Message> list = service.retrieve(param, sortFieldList, offset, limit);
      if (list.size() > 0) {
         Message item = list.get(0);
         data = item.toJSONObject();
      }
      response.setData(data);
      code = list.size();
      response.setCode(code);
      response.setMessage(message);
      return response;
   }

   @PostMapping(value = "/list", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> list(
         @RequestBody ListQueryRequest request) {

      logger.info("[{}/list] is called with {}", alias, request.toString());

      if (request.sort!= null &&!request.sort.isEmpty()) {
         for (SortField field : request.sort) {
            if (!VALID_SORT_FIELDS.contains(field.getField())) {
               throw new InvalidArgumentException("Invalid sort field: " + field.getField());
            }
         }
      }
      int code = 0;
      String message = "success";

      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();

      ZoneId zoneId = ZoneId.systemDefault();
      OffsetDateTime startDateTime = request.getStartTime() != null ? request.getStartTime().atZone(zoneId).toOffsetDateTime() : null;
      OffsetDateTime endDateTime = request.getEndTime() != null ? request.getEndTime().atZone(zoneId).toOffsetDateTime() : null;

      MessageQueryParam param = new MessageQueryParam.Builder()
            .withDeviceCodeEqual(request.getDeviceCode())
            .withTopicEqual(request.getTopic())
            .withTagEqual(request.getTag())
            .withSendTimeAfter(startDateTime)
            .withSendTimeBefore(endDateTime)
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("sendTime", true));
      }
      Integer offset = null;
      Integer limit = null;
      List<Message> list = service.retrieve(param, sortFieldList, offset, limit);
      for (Message item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = itemList.size();
      data.put("total", total);
      data.set("list", itemList);

      response.setData(data);
      code = itemList.size();
      response.setCode(code);
      response.setMessage(message);

      return response;
   }

   @PostMapping(value = "/page", produces = "application/json;charset=UTF-8")
   public ResponseTemplate<ObjectNode> page(
         @RequestBody PageQueryRequest request) {

      logger.info("[{}/list] is called with {}", alias, request.toString());

      if (request.getSort()!= null && !request.getSort().isEmpty()) {
         for (SortField field : request.sort) {
            if (!VALID_SORT_FIELDS.contains(field.getField())) {
               throw new InvalidArgumentException("Invalid sort field: " + field.getField());
            }
         }
      }

      int code = 0;
      String message = "success";
      
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectNode data = objectMapper.createObjectNode();
      ArrayNode itemList = objectMapper.createArrayNode();
      ResponseTemplate<ObjectNode> response = new ResponseTemplate<>();
      
      ZoneId zoneId = ZoneId.systemDefault();
      OffsetDateTime startDateTime = request.getStartTime() != null ? request.getStartTime().atZone(zoneId).toOffsetDateTime() : null;
      OffsetDateTime endDateTime = request.getEndTime() != null ? request.getEndTime().atZone(zoneId).toOffsetDateTime() : null;
      MessageQueryParam param = new MessageQueryParam.Builder()
            .withDeviceCodeEqual(request.getDeviceCode())
            .withTopicEqual(request.getTopic())
            .withTagEqual(request.getTag())
            .withSendTimeAfter(startDateTime)
            .withSendTimeBefore(endDateTime)
            .build();
      List<SortField> sortFieldList = request.getSort();
      if (sortFieldList == null || sortFieldList.size() < 1){
         sortFieldList = new ArrayList<SortField>();
         sortFieldList.add(new SortField("sendTime", true));
      }

      PageParam page = request.getPage();
      Integer limit = ArgumentUtility.parsePageLimit(page);
      Integer offset = ArgumentUtility.parsePageOffset(page);
      List<Message> list = service.retrieve(param, sortFieldList, offset, limit);
      for (Message item : list) {
         ObjectNode json = item.toJSONObject();
         itemList.add(json);
      }
      long total = service.count(param);
      data.put("total", total);
      data.set("list", itemList);

      response.setData(data);
      code = itemList.size();
      response.setCode(code);
      response.setMessage(message);

      return response;
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class CreateRequest {
      private String deviceCode;
      private String topic;
      private String tag;
      private String content;
      private LocalDateTime sendTime;

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

      public LocalDateTime getSendTime() {
         return sendTime;
      }
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class DetailRequest {
      private Long id;
      
      public Long getId() {
          return id;
      }
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class ListQueryRequest {
      private String deviceCode;
      private String topic;
      private String tag;
      private LocalDateTime startTime;
      private LocalDateTime endTime;
      private List<SortField> sort;
      
      public String getDeviceCode() {
          return deviceCode;
      }

      public String getTopic() {
          return topic;
      }
      public String getTag() {
          return tag;
      }
      public LocalDateTime getStartTime() {
          return startTime;
      }
      public LocalDateTime getEndTime() {
          return endTime;
      }
      public List<SortField> getSort() {
          return sort;
      }
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class PageQueryRequest {
      private String deviceCode;
      private String topic;
      private String tag;
      private LocalDateTime startTime;
      private LocalDateTime endTime;
      private List<SortField> sort;
      private PageParam page;
      
      public String getDeviceCode() {
          return deviceCode;
      }
      public String getTopic() {
          return topic;
      }
      public String getTag() {
          return tag;
      }
      public List<SortField> getSort() {
          return sort;
      }
      public PageParam getPage() {
          return page;
      }
      public LocalDateTime getStartTime() {
          return startTime;
      }
      public LocalDateTime getEndTime() {
          return endTime;
      }
   }
}
