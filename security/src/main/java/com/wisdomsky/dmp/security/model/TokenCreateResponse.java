package com.wisdomsky.dmp.security.model;

public class TokenCreateResponse {
   private String client;
   private int expirationSecond;
   private String token;

   // Getters and Setters
   public String getClient() {
      return client;
   }

   public void setClient(String client) {
      this.client = client;
   }

   public int getExpirationSecond() {
      return expirationSecond;
   }

   public void setExpirationSecond(int expirationSecond) {
      this.expirationSecond = expirationSecond;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }
}
