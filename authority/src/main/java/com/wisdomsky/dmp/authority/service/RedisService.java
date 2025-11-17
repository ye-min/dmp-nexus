package com.wisdomsky.dmp.authority.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
   private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

   @Autowired
   private StringRedisTemplate redisTemplate;

   public boolean checkSign(String app, String sign, long timeout) {
      String key = String.format("UNIQUEREQ:%s:%s", app, sign);
      try {
         Boolean output = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(System.currentTimeMillis()));
         if (Boolean.TRUE.equals(output)) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            return true;
         } else {
            return false;
         }
      } catch (Exception ex) {
         logger.error("[checkSign] encountered an exception: {}", key);
         return false;
      }
   }

   public int saveToken(String app, String account, String client, String token, int expirationSecond) {
      try {
         Map<Object, Object> value = new HashMap<Object, Object>();
         value.put("token", token);
         value.put("time", String.valueOf(System.currentTimeMillis()));
         value.put("client", client);
         String key = String.format("TOKEN:APP:%s:ACCOUNT:%s:%s", app, account, client);
         redisTemplate.opsForHash().putAll(key, value);
         redisTemplate.expire(key, expirationSecond, TimeUnit.SECONDS);
         return 0;
      } catch (Exception ex) {
         String message = String.format("[saveToken] encountered an exception: %s, %s, %s.", app, account, client);
         logger.error(message, ex);
         return -1;
      }
   }

   public int removeToken(String app, String account, String client) {
      try {
         String key = String.format("TOKEN:APP:%s:ACCOUNT:%s:%s", app, account, client);
         return redisTemplate.delete(key) ? 0 : -1;
      } catch (Exception ex) {
         String message = String.format("[removeToken] encountered an exception: %s, %s, %s.", app, account, client);
         logger.error(message, ex);
         return -1;
      }
   }

   public String getToken(String app, String account, String client) {
      String token = "";
      String key = String.format("TOKEN:APP:%s:ACCOUNT:%s:%s", app, account, client);
      try {
         List<Object> token_data = redisTemplate.opsForHash().multiGet(key, Arrays.asList("token", "client"));
         if (token_data.get(0) != null) {
            if (((String) token_data.get(1)).equals(client)) {
               token = (String) token_data.get(0);
               logger.info("Get token: {}", token);
            }
         }
      } catch (Exception ex) {
         logger.error("[getToken] encountered an exception: {}", key, ex);
      }
      return token;
   }

   public boolean refreshToken(String app, String account, String client, int timeout) {
      String key = String.format("TOKEN:APPID:%s:ACCOUNTID:%s:%s", app, account, client);
      try {
         return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
      } catch (Exception ex) {
         logger.error("[refreshToken] encountered an exception: {}", key, ex);
      }
      return false;
   }
}
