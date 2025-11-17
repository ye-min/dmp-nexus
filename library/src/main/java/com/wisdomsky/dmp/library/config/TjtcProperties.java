package com.wisdomsky.dmp.library.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tjtc")
public class TjtcProperties {
   private String dateFormat;
   private String dateTimeFormat;
   private String defaultPassword;
   private double distanceThreshold;
   private boolean interceptorDeviceEnabled;
   private double positionWeightFourThree;
   private double positionWeightFourFour;
   private double positionWeightFiveThree;
   private double positionWeightFiveFour;
   private double positionWeightFiveFive;
   private boolean debug;
   private Device device = new Device();
   private Eureka eureka = new Eureka();

   // Getters and Setters

   public void setDateFormat(String dateFormat) {
      this.dateFormat = dateFormat;
   }
   public String getDateFormat() {
      return dateFormat;
   }
   public void setDateTimeFormat(String dateTimeFormat) {
      this.dateTimeFormat = dateTimeFormat;
   }
   public String getDateTimeFormat() {
      return dateTimeFormat;
   }
   public void setDefaultPassword(String defaultPassword) {
      this.defaultPassword = defaultPassword;
   }
   public String getDefaultPassword() {
      return defaultPassword;
   }
   public void setDistanceThreshold(double distanceThreshold) {
      this.distanceThreshold = distanceThreshold;
   }
   public double getDistanceThreshold() {
      return distanceThreshold;
   }
   public void setInterceptorDeviceEnabled(boolean interceptorDeviceEnabled) {
      this.interceptorDeviceEnabled = interceptorDeviceEnabled;
   }
   public boolean isInterceptorDeviceEnabled() {
      return interceptorDeviceEnabled;
   }
   public void setPositionWeightFourThree(double positionWeightFourThree) {
      this.positionWeightFourThree = positionWeightFourThree;
   }
   public double getPositionWeightFourThree() {
      return positionWeightFourThree;
   }
   public void setPositionWeightFourFour(double positionWeightFourFour) {
      this.positionWeightFourFour = positionWeightFourFour;
   }
   public double getPositionWeightFourFour() {
      return positionWeightFourFour;
   }
   public void setPositionWeightFiveThree(double positionWeightFiveThree) {
      this.positionWeightFiveThree = positionWeightFiveThree;
   }
   public double getPositionWeightFiveThree() {
      return positionWeightFiveThree;
   }
   public void setPositionWeightFiveFour(double positionWeightFiveFour) {
      this.positionWeightFiveFour = positionWeightFiveFour;
   }
   public double getPositionWeightFiveFour() {
      return positionWeightFiveFour;
   }
   public void setPositionWeightFiveFive(double positionWeightFiveFive) {
      this.positionWeightFiveFive = positionWeightFiveFive;
   }
   public double getPositionWeightFiveFive() {
      return positionWeightFiveFive;
   }
   public void setDebug(boolean debug) {
      this.debug = debug;
   }
   public boolean isDebug() {
      return debug;
   }
   public void setDevice(Device device) {
      this.device = device;
   }
   public Device getDevice() {
      return device;
   }
   public void setEureka(Eureka eureka) {
      this.eureka = eureka;
   }
   public Eureka getEureka() {
      return eureka;
   }

   public static class Device {
      private String token;

      public void setToken(String token) {
         this.token = token;
      }
      public String getToken() {
         return token;
      }     
   }

   public static class Eureka {
      private String host;
      private int port;

      public void setHost(String host) {
         this.host = host;
      }
      public String getHost() {
         return host;
      }
      public void setPort(int port) {
         this.port = port;
      }
      public int getPort() {
         return port;
      }
   }
}
