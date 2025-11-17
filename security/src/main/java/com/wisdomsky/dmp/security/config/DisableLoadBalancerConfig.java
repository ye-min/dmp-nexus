/* package com.wisdomsky.dmp.security.config;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DisableLoadBalancerConfig {
    @Bean
    public static BeanFactoryPostProcessor disableLoadBalancer() {
        return beanFactory -> {
            // Since beanFactory is already a ConfigurableListableBeanFactory, 
            // no need for instanceof check
            beanFactory.registerResolvableDependency(ServiceInstanceListSupplier.class, null);
        };
    }
} */