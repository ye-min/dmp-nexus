package com.wisdomsky.dmp.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class InternalAccessValidationGatewayFilterFactory
      extends AbstractGatewayFilterFactory<InternalAccessValidationGatewayFilterFactory.Config> {

   private static final Logger logger = LoggerFactory.getLogger(InternalAccessValidationGatewayFilterFactory.class);
   public InternalAccessValidationGatewayFilterFactory() {
      super(Config.class);
   }

   @Override
   public GatewayFilter apply(Config config) {
      return (exchange, chain) -> {
         ServerHttpRequest request = exchange.getRequest();
         String internalCallValue = request.getHeaders().getFirst("X-Internal-Call");

         if (internalCallValue == null) {
            String message = String.format("External access denied.");
            logger.warn(message);
            return handleInvalidSign(exchange);
         } else {
            if (!internalCallValue.equals("true")) {
               String message = String.format("External access denied.");
               logger.warn(message);
               return handleInvalidSign(exchange);
            }
         }

         return chain.filter(exchange);
      };
   }

   private Mono<Void> handleInvalidSign(ServerWebExchange exchange) {
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

      String jsonResponse = "{\"code\": -10004, \"message\": \"External access denied\"}";
      byte[] bytes = jsonResponse.getBytes();
      DataBuffer buffer = response.bufferFactory().wrap(bytes);
      return response.writeWith(Mono.just(buffer));
   }

   public static class Config {
   }
}
