package com.wisdomsky.dmp.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    /**
     * 创建一个被 @LoadBalanced 注解标记的 WebClient.Builder Bean。
     * 当其他组件（比如你的 ForwardAuthGatewayFilterFactory）注入 WebClient.Builder 时，
     * Spring 会优先提供这个被增强过的 Bean。
     *
     * @return 一个具备负载均衡能力的 WebClient.Builder 实例
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}