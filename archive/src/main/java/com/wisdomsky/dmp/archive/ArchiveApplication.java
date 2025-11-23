package com.wisdomsky.dmp.archive;

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
@ComponentScan(basePackages = {"com.wisdomsky.dmp.archive"})
public class ArchiveApplication 
{
   public static void main( String[] args )
   {
      SpringApplication.run(ArchiveApplication.class, args);
   }
}
