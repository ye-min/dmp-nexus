package com.wisdomsky.dmp.authority;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.wisdomsky.dmp.authority", "com.wisdomsky.dmp.archive" })
public class AuthorityApplication {
   public static void main(String[] args) {
      SpringApplication.run(AuthorityApplication.class, args);
   }
}
