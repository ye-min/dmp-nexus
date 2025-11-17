package com.wisdomsky.dmp.library.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptoUtility {
   public static String sha1(String input) {
      final Logger logger = LoggerFactory.getLogger(CryptoUtility.class);
      try {
         // 获取 SHA-1 摘要算法实例
         MessageDigest digest = MessageDigest.getInstance("SHA-1");

         // 将字符串转换为字节数组，并计算哈希值
         byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

         // 将字节数组转换为十六进制字符串
         StringBuilder hexString = new StringBuilder();
         for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
               hexString.append('0');
            }
            hexString.append(hex);
         }
         return hexString.toString();
      } catch (NoSuchAlgorithmException ex) {
         logger.error("SHA-1 algorithm not found.", ex);
         return null;
      }
   }

}
