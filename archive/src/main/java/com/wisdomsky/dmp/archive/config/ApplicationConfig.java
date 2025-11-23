package com.wisdomsky.dmp.archive.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ServerProperties.class})
public class ApplicationConfig {
}