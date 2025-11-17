package com.wisdomsky.dmp.registry.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "eureka.server")
public class EurekaServerProperties {

    private String hostname;
    private int port;

    // Getter and Setter for hostname
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    // Getter and Setter for port
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}