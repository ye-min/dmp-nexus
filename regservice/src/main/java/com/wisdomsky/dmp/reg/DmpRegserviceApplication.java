package com.wisdomsky.dmp.reg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class DmpRegserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DmpRegserviceApplication.class, args);
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize-> {
                    try {
                    	authorize.anyRequest().authenticated().and().httpBasic();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).csrf().disable().build();
    }

}
