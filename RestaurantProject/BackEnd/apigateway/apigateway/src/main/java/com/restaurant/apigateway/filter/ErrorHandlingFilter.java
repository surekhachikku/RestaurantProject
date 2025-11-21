package com.restaurant.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global Error Handling Filter
 * Provides consistent error responses across all routes
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1) // Run after JWT filter but before routing
public class ErrorHandlingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingFilter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(throwable -> {
                    logger.error("Error processing request: {}", throwable.getMessage(), throwable);
                    
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(getHttpStatus(throwable));
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    
                    Map<String, Object> errorResponse = createErrorResponse(throwable, exchange);
                    
                    try {
                        String json = objectMapper.writeValueAsString(errorResponse);
                        DataBuffer buffer = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
                        return response.writeWith(Mono.just(buffer));
                    } catch (JsonProcessingException e) {
                        logger.error("Error serializing error response", e);
                        return response.setComplete();
                    }
                });
    }

    private HttpStatus getHttpStatus(Throwable throwable) {
        // Map exceptions to appropriate HTTP status codes
        String exceptionName = throwable.getClass().getSimpleName();
        
        if (exceptionName.contains("Timeout") || exceptionName.contains("TimeLimiter")) {
            return HttpStatus.GATEWAY_TIMEOUT;
        } else if (exceptionName.contains("CircuitBreaker")) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        } else if (exceptionName.contains("Connection") || exceptionName.contains("ConnectException")) {
            return HttpStatus.BAD_GATEWAY;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Map<String, Object> createErrorResponse(Throwable throwable, ServerWebExchange exchange) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", getHttpStatus(throwable).value());
        errorResponse.put("error", getHttpStatus(throwable).getReasonPhrase());
        errorResponse.put("message", throwable.getMessage());
        errorResponse.put("path", exchange.getRequest().getPath().toString());
        
        // In development, include stack trace; in production, hide it
        if (logger.isDebugEnabled()) {
            errorResponse.put("exception", throwable.getClass().getName());
        }
        
        return errorResponse;
    }
}

