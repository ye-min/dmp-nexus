package com.wisdomsky.dmp.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.wisdomsky.dmp.security", "com.wisdomsky.dmp.library"})
public class SecurityApplication 
{
   public static void main( String[] args )
   {
      SpringApplication.run(SecurityApplication.class, args);
   }
}
