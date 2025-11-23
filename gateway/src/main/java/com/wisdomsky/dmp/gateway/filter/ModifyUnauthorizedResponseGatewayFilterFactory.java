package com.wisdomsky.dmp.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ModifyUnauthorizedResponseGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    public ModifyUnauthorizedResponseGatewayFilterFactory() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // then() 操作符会在过滤链执行完毕后（即收到下游服务响应后）执行
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();

                // 检查下游服务返回的状态码是否为 401
                if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    
                    // 1. 修改状态码为 200
                    response.setStatusCode(HttpStatus.OK);
                    
                    // 2. 设置响应头
                    response.getHeaders().set("Content-Type", "application/json");
                    response.getHeaders().set("Access-Control-Allow-Origin", "*");
                    
                    // 注意：这里没有修改响应体。如果需要修改响应体（比如返回一个固定的JSON错误信息），
                    // 则需要使用更复杂的 ModifyResponseBody 过滤器或自定义 Body 装饰器。
                    // 根据你的 APISIX 配置，这里只修改了状态和头，所以这样实现是正确的。
                }
            }));
        };
    }
}