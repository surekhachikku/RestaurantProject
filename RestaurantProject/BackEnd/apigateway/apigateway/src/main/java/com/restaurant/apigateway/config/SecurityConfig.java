package com.restaurant.apigateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        logger.info("Configuring Spring Security for API Gateway - Allowing all requests, JWT filter will handle authentication");
        
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .anyExchange().permitAll() // Allow all requests, JWT filter will handle authentication
                )
                .build();
    }
}