package com.restaurant.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple Rate Limiting Filter
 * In production, use Redis-based rate limiting for distributed systems
 * 
 * This is a basic in-memory implementation for demonstration
 */
@Component
public class RateLimitingFilter extends AbstractGatewayFilterFactory<RateLimitingFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);
    
    // In-memory rate limiter (use Redis in production for distributed systems)
    private final ConcurrentHashMap<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();
    
    public RateLimitingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String clientId = getClientId(exchange);
            String key = clientId + ":" + exchange.getRequest().getPath().toString();
            
            RateLimitInfo rateLimitInfo = rateLimitMap.computeIfAbsent(key, 
                k -> new RateLimitInfo(config.getRequestsPerMinute()));
            
            long currentTime = System.currentTimeMillis();
            
            // Reset counter if a minute has passed
            if (currentTime - rateLimitInfo.getWindowStart() > 60000) {
                rateLimitInfo.reset(config.getRequestsPerMinute());
            }
            
            // Check if limit exceeded
            if (rateLimitInfo.getCount().get() >= config.getRequestsPerMinute()) {
                logger.warn("Rate limit exceeded for client: {} on path: {}", 
                        clientId, exchange.getRequest().getPath());
                
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                exchange.getResponse().getHeaders().add("X-RateLimit-Limit", 
                        String.valueOf(config.getRequestsPerMinute()));
                exchange.getResponse().getHeaders().add("X-RateLimit-Remaining", "0");
                return exchange.getResponse().setComplete();
            }
            
            // Increment counter
            int remaining = config.getRequestsPerMinute() - rateLimitInfo.getCount().incrementAndGet();
            exchange.getResponse().getHeaders().add("X-RateLimit-Limit", 
                    String.valueOf(config.getRequestsPerMinute()));
            exchange.getResponse().getHeaders().add("X-RateLimit-Remaining", 
                    String.valueOf(remaining));
            
            return chain.filter(exchange);
        };
    }

    private String getClientId(ServerWebExchange exchange) {
        // Try to get user ID from header (set by JWT filter)
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
        if (userId != null) {
            return userId;
        }
        
        // Fallback to IP address
        if (exchange.getRequest().getRemoteAddress() != null) {
            return exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        }
        
        return "unknown";
    }

    public static class Config {
        private int requestsPerMinute = 60; // Default: 60 requests per minute

        public int getRequestsPerMinute() {
            return requestsPerMinute;
        }

        public void setRequestsPerMinute(int requestsPerMinute) {
            this.requestsPerMinute = requestsPerMinute;
        }
    }

    private static class RateLimitInfo {
        private final AtomicInteger count;
        private long windowStart;

        public RateLimitInfo(int maxRequests) {
            this.count = new AtomicInteger(0);
            this.windowStart = System.currentTimeMillis();
        }

        public void reset(int maxRequests) {
            count.set(0);
            windowStart = System.currentTimeMillis();
        }

        public AtomicInteger getCount() {
            return count;
        }

        public long getWindowStart() {
            return windowStart;
        }
    }
}

