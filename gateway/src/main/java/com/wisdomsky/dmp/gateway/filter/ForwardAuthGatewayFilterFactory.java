package com.wisdomsky.dmp.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class ForwardAuthGatewayFilterFactory
        extends AbstractGatewayFilterFactory<ForwardAuthGatewayFilterFactory.Config> {

    private final WebClient webClient;

    public ForwardAuthGatewayFilterFactory(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // [最终修正] 正确使用 cacheRequestBody API。
            // 我们的所有逻辑都必须在传递给它的第二个参数（一个 Function）内部。
            // 这个 Function 接收一个可重复读取 body 的 ServerHttpRequest，并必须返回一个 Mono<Void>。
            return ServerWebExchangeUtils.cacheRequestBody(exchange, (cachedRequest) -> {

                // 从传递进来的、已缓存的请求中获取 body。
                Flux<DataBuffer> body = cachedRequest.getBody();

                // 将 Flux<DataBuffer> 转换为 Mono<String>
                Mono<String> bodyStringMono = body
                        .map(buffer -> {
                            byte[] bytes = new byte[buffer.readableByteCount()];
                            buffer.read(bytes);
                            return new String(bytes, StandardCharsets.UTF_8);
                        })
                        .reduce(String::concat)
                        .defaultIfEmpty("");

                // 使用 flatMap 构建响应式链
                return bodyStringMono.flatMap(bodyString -> {

                    // 构建发往认证服务的请求
                    WebClient.ResponseSpec responseSpec = webClient
                            .method(HttpMethod.POST)
                            .uri(URI.create(config.getUri()))
                            .headers(authHeaders -> {
                                HttpHeaders originalHeaders = cachedRequest.getHeaders();
                                config.getHeaders().forEach(headerName -> {
                                    List<String> headerValues = originalHeaders.get(headerName);
                                    if (headerValues != null && !headerValues.isEmpty()) {
                                        authHeaders.addAll(headerName, headerValues);
                                    }
                                });

                                // [最终修正] 从正确的 'cachedRequest' 对象获取 URI
                                authHeaders.set("X-Forwarded-Uri", cachedRequest.getURI().getPath());

                                if (originalHeaders.getContentType() != null) {
                                    authHeaders.setContentType(originalHeaders.getContentType());
                                }
                            })
                            .body(BodyInserters.fromValue(bodyString))
                            .retrieve();

                    // 发送认证请求并处理结果
                    return responseSpec
                            .toBodilessEntity()
                            .flatMap(responseEntity -> {
                                // 认证成功
                                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                                    return chain.filter(exchange.mutate().request(cachedRequest).build());
                                } else {
                                    // [关键修改] 认证失败，只设置状态码，然后完成
                                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                    return exchange.getResponse().setComplete();
                                }
                            })
                            .onErrorResume(e -> {
                                log.error("Forward-auth sub-request failed!", e);
                                // [关键修改] 子请求失败，只设置状态码，然后完成
                                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                return exchange.getResponse().setComplete();
                            });
                });
            });
        };
    }

    public static class Config {
        private String uri;
        private List<String> headers;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(String headers) {
            this.headers = Arrays.asList(headers.split(","));
        }
    }
}