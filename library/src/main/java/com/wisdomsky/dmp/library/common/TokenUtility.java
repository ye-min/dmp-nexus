package com.wisdomsky.dmp.library.common;

import java.util.Random;

public class TokenUtility {
   public static String buildComplexTokenCode(int length) {
      String base = "^#`~abcdefghijklmnopqrstuvwxyz0123456789$ABCDEFGHIJKLMNOPQRSRUVWXYZ><.?";
      Random random = new Random();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < length; i++) {
         int number = random.nextInt(base.length());
         sb.append(base.charAt(number));
      }
      return sb.toString();
   }

   public static String buildCouponCode(int length) {
      String base = "0123456789ABCDEFGHIJKLMNOPQRSRUVWXYZ";
      Random random = new Random();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < length; i++) {
         int number = random.nextInt(base.length());
         sb.append(base.charAt(number));
      }
      return sb.toString();
   }

   public static String buildNumberCode(int length) {
      String base = "1234567890";
      Random random = new Random();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < length; i++) {
         int number = random.nextInt(base.length());
         sb.append(base.charAt(number));
      }
      return sb.toString();
   }

   public static String buildSmsCode(int length) {
      return buildNumberCode(length);
   }

   public static String buildNormalTokenCode(int length) {
      String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSRUVWXYZ";
      Random random = new Random();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < length; i++) {
         int number = random.nextInt(base.length());
         sb.append(base.charAt(number));
      }
      return sb.toString();
   }
}
