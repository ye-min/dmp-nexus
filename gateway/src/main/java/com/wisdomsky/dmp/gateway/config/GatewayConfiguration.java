package com.wisdomsky.dmp.gateway.config;

import lombok.extern.slf4j.Slf4j; // <-- 新增
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j // <-- 新增 @Slf4j 注解
public class GatewayConfiguration implements WebFilter {

    private static final String ALLOWED_METHODS = "GET,POST,PUT,DELETE,OPTIONS,HEAD";
    private static final String ALLOWED_ORIGIN = "Access-Control-Allow-Origin";
    private static final String ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
    private static final String MAX_AGE = "3600";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // [日志哨兵 1] 检查过滤器是否被执行
        log.info(">>>> [CORS Filter] Entered. Highest Precedence Filter is running.");

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        
        String origin = request.getHeaders().getOrigin();
        if (origin != null) {
            // [日志哨兵 2] 检查 Origin 是否被正确处理
            log.info(">>>> [CORS Filter] Origin: {}. Setting CORS headers.", origin);
            headers.add(ALLOWED_ORIGIN, origin);
            headers.add(ALLOW_CREDENTIALS, "true");
            headers.add("Access-Control-Expose-Headers", ALLOWED_ORIGIN + ", " + ALLOW_CREDENTIALS);
        } else {
            log.warn(">>>> [CORS Filter] Request does not have an Origin header.");
        }

        if (request.getMethod() == HttpMethod.OPTIONS) {
            // [日志哨兵 3] 检查 OPTIONS 请求是否被正确拦截
            log.info(">>>> [CORS Filter] Pre-flight OPTIONS request detected. Handling and short-circuiting.");
            
            String requestedHeaders = request.getHeaders().getFirst(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
            if (requestedHeaders != null && !requestedHeaders.isEmpty()) {
                // [日志哨兵 4] 检查请求的头是否被正确反射
                log.info(">>>> [CORS Filter] Reflecting requested headers: {}", requestedHeaders);
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestedHeaders);
            } else {
                log.warn(">>>> [CORS Filter] Pre-flight request but no Access-Control-Request-Headers found.");
            }

            headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
            headers.add("Access-Control-Max-Age", MAX_AGE);
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Mono.empty();
        }
        
        // [日志哨兵 5] 检查非 OPTIONS 请求是否被放行
        log.info(">>>> [CORS Filter] Not a pre-flight request. Passing to next filter in chain.");
        return chain.filter(exchange);
    }
}