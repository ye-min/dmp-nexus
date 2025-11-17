package com.wisdomsky.dmp.archive.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.wisdomsky.dmp.archive.interceptor.DeviceInterceptor;

@Configuration
@ConditionalOnProperty(prefix="tjtc", name="interceptor-device-enabled", havingValue="true", matchIfMissing=false)
public class WebMvcConfig implements WebMvcConfigurer {

    private final DeviceInterceptor deviceInterceptor;

    public WebMvcConfig(DeviceInterceptor signatureInterceptor) {
        this.deviceInterceptor = signatureInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(deviceInterceptor)
                .addPathPatterns("/device/**"); // 应用到所有 /api 路径
        // .excludePathPatterns("/api/public/**"); // 排除 /api/public 路径
    }
}