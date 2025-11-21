package com.restaurant.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Global filter for logging all requests and responses
 * This helps debug the API Gateway flow
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class LoggingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestId = java.util.UUID.randomUUID().toString().substring(0, 8);
        LocalDateTime startTime = LocalDateTime.now();

        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("REQUEST [{}] - {} {}", requestId, request.getMethod(), request.getURI());
        logger.info("Timestamp: {}", startTime.format(formatter));
        logger.info("Client IP: {}", request.getRemoteAddress());
        logger.debug("Request Headers: {}", request.getHeaders());
        logger.debug("Query Params: {}", request.getQueryParams());
        
        // Log request body size if present
        long contentLength = request.getHeaders().getContentLength();
        if (contentLength > 0) {
            logger.debug("Request body size: {} bytes", contentLength);
        }

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> {
                    ServerHttpResponse response = exchange.getResponse();
                    LocalDateTime endTime = LocalDateTime.now();
                    long duration = java.time.Duration.between(startTime, endTime).toMillis();
                    
                    logger.info("═══════════════════════════════════════════════════════════");
                    logger.info("RESPONSE [{}] - Status: {} (Duration: {} ms)", 
                            requestId, 
                            response.getStatusCode(), 
                            duration);
                    logger.debug("Response Headers: {}", response.getHeaders());
                    logger.info("═══════════════════════════════════════════════════════════");
                })
                .doOnError(throwable -> {
                    LocalDateTime endTime = LocalDateTime.now();
                    long duration = java.time.Duration.between(startTime, endTime).toMillis();
                    logger.error("═══════════════════════════════════════════════════════════");
                    logger.error("ERROR [{}] - Exception occurred (Duration: {} ms)", requestId, duration);
                    logger.error("Error: {}", throwable.getMessage(), throwable);
                    logger.error("═══════════════════════════════════════════════════════════");
                });
    }
}

