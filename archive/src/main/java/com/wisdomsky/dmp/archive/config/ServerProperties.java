package com.wisdomsky.dmp.archive.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("archive")
// @EnableConfigurationProperties(ServerProperties.class)
public class ServerProperties {
   
}