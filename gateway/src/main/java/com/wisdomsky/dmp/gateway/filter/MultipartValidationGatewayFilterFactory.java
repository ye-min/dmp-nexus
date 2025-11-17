package com.wisdomsky.dmp.gateway.filter;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;

import com.wisdomsky.dmp.gateway.model.SignValidateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Component
public class MultipartValidationGatewayFilterFactory
      extends AbstractGatewayFilterFactory<MultipartValidationGatewayFilterFactory.Config> {

   @Value("${tjtc.authority.multipart}")
   private String validateMultipartUrl;

   private static final Logger logger = LoggerFactory.getLogger(MultipartValidationGatewayFilterFactory.class);
   private final WebClient webClient;

   public MultipartValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
         ReactorLoadBalancerExchangeFilterFunction lbFunction) {
      super(Config.class);
      this.webClient = webClientBuilder
            .filter(lbFunction)
            .build();
   }

   @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();
            String authorityUrl = validateMultipartUrl;

            return exchange.getRequest().getBody()
                    .defaultIfEmpty(exchange.getResponse().bufferFactory().wrap(new byte[0]))
                    .single()
                    .flatMap(dataBuffer -> {
                        String body = dataBuffer.toString(StandardCharsets.UTF_8);
                        exchange.getAttributes().put("cachedRequestBodyObject", body);
                        
                        return validateSign(exchange, authorityUrl)
                                .flatMap(responseEntity -> {
                                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                                        logger.info("Sign validation successful for path: {}", path);
                                        return chain.filter(exchange);
                                    } else {
                                       int code = -10000;
                                       if (responseEntity.getBody() != null) {
                                          var responseBody = responseEntity.getBody();
                                          if (responseBody == null) {
                                             logger.error("Sign validation failed for path: {}, error code: {}, error message: {}", path, 401, "Unauthorized");
                                          } else {
                                             code = responseBody.getCode();
                                             String message = responseBody.getMessage();
                                             logger.error("Sign validation failed for path: {}, error code: {}, error message: {}", path, code, message);
                                          }
                                       }
                                       ResponseEntity<SignValidateResponse> revisedResponseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                            .body(new SignValidateResponse(code, "Unauthorized"));
                                       return handleInvalidStatus(exchange, revisedResponseEntity);
                                    }
                                })
                                .onErrorResume(throwable -> {
                                    logger.error("Error during authority validation", throwable);
                                    exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                                    return exchange.getResponse().setComplete();
                                });
                    });
        };
    }

   private Mono<ResponseEntity<SignValidateResponse>> validateSign(ServerWebExchange exchange, String authorityUrl) {
      String uri = exchange.getRequest().getPath().value();
      String body = exchange.getAttribute("cachedRequestBodyObject");

      return webClient
            .post()
            .uri(authorityUrl)
            .headers(headers -> headers.addAll(exchange.getRequest().getHeaders()))
            .header("X-Forwarded-Uri", uri)
            .bodyValue(body != null ? body : "{}")
            .retrieve()
            .toEntity(SignValidateResponse.class)
            .onErrorResume(WebClientResponseException.class, e -> {
               HttpStatusCode statusCode = e.getStatusCode();
               if (statusCode == HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value())) {
                  return Mono.just(ResponseEntity.status(HttpStatus.valueOf(statusCode.value()))
                        .body(convertToSignValidateResponse(e.getResponseBodyAsString())));
               }
               return Mono.error(e);
            })
            .onErrorResume(e -> {
               logger.error("Unexpected error: ", e);
               return Mono.error(e);
           });
   }

   private SignValidateResponse convertToSignValidateResponse(String responseBody) {
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.readValue(responseBody, SignValidateResponse.class);
      } catch (JsonProcessingException e) {
         logger.error("Error parsing response body", e);
         return new SignValidateResponse(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
      }
   }

   // Http Status is 401 UNAUTHORIZED
   private Mono<Void> handleInvalidStatus(ServerWebExchange exchange,
         ResponseEntity<SignValidateResponse> responseEntity) {
      exchange.getResponse().setStatusCode(responseEntity.getStatusCode());
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

      SignValidateResponse response = responseEntity.getBody();
      String jsonResponse = convertToJson(response);

      return exchange.getResponse().writeWith(
            Mono.just(exchange.getResponse().bufferFactory().wrap(jsonResponse.getBytes(StandardCharsets.UTF_8))));
   }

   private String convertToJson(SignValidateResponse response) {
      try {
         ObjectMapper objectMapper = new ObjectMapper();
         return objectMapper.writeValueAsString(response);
      } catch (JsonProcessingException e) {
         return "{\"code\":" + response.getCode() + ",\"message\":\"" + response.getMessage() + "\"}";
      }
   }

   public static class Config {
   }
}
