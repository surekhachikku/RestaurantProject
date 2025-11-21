package com.restaurant.apigateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Gateway Configuration for Circuit Breaker and Resilience
 * This prevents cascade failures when downstream services are unavailable
 */
@Configuration
public class GatewayConfig {

    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    /**
     * Circuit Breaker Configuration
     * - Fails fast when service is down
     * - Prevents cascading failures
     * - Automatically recovers when service is back
     */
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        logger.info("Configuring Circuit Breaker for API Gateway");
        
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        // Open circuit after 5 failures
                        .failureRateThreshold(50)
                        // Wait 10 seconds before trying again
                        .waitDurationInOpenState(Duration.ofSeconds(10))
                        // Keep circuit half-open for 5 seconds
                        .slidingWindowSize(10)
                        // Minimum number of calls before circuit can open
                        .minimumNumberOfCalls(5)
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        // Timeout after 3 seconds
                        .timeoutDuration(Duration.ofSeconds(3))
                        .build())
                .build());
    }
}

