package com.wisdomsky.dmp.archive.controller.device;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wisdomsky.dmp.archive.model.MessageCreateParam;
import com.wisdomsky.dmp.archive.service.MessageService;
import com.wisdomsky.dmp.library.model.ResponseTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;

@RestController("devicePayloadController")
@RequestMapping("/device/payload")
public class PayloadController {
    private static final Logger logger = LoggerFactory.getLogger(PayloadController.class);
    private static final String alias = "/device/payload";

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
}
