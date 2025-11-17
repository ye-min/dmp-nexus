package com.wisdomsky.dmp.admin.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.wisdomsky.dmp.library.common.SignUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@ConditionalOnProperty(prefix="tjtc", name="interceptor-device-enabled", havingValue="true", matchIfMissing=false)
public class DeviceInterceptor implements HandlerInterceptor {

   @Value("${tjtc.device.token:tjtc}")
   private String token;

   @Override
   public boolean preHandle(
         @NonNull HttpServletRequest request,
         @NonNull HttpServletResponse response,
         @NonNull Object handler) throws Exception {

      /* logger.info("===== Request Headers =====");
      while (headerNames.hasMoreElements()) {
         String headerName = headerNames.nextElement();
         logger.info(headerName + ": " + request.getHeader(headerName));
      }
      logger.info("=========================="); */
      // Check for specific headers added by Gateway
      String gatewayAuth = request.getHeader("X-Gateway-Authenticated");
        
      // If the request has passed through the Gateway, skip the verification
      if ("true".equals(gatewayAuth)) {
         return true; // Skip authentication
      }

      String app = request.getHeader("app");
      String digest = request.getHeader("digest");
      String timestamp = request.getHeader("timestamp");
      String client = request.getHeader("client");
      String sign = request.getHeader("sign");

      if (app == null || digest == null || timestamp == null || client == null || sign == null) {
         throw new IllegalArgumentException("Missing required headers");
      }

      String concatenated = app + digest + token + client + timestamp;

      String calculatedSign = SignUtility.calculateSHA1(concatenated);

      if (!calculatedSign.equals(sign)) {
         throw new SecurityException("Invalid signature");
      }

      return true;
   }
}