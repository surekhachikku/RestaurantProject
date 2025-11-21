package com.restaurant.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * CORS Configuration for API Gateway
 * In production, configure allowed origins based on your frontend domains
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        
        // In production, replace with specific frontend domains
        // Example: corsConfig.setAllowedOrigins(Arrays.asList("https://yourdomain.com", "https://app.yourdomain.com"));
        corsConfig.setAllowedOrigins(Arrays.asList("*")); // Allow all for development
        
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setExposedHeaders(Arrays.asList("Authorization", "X-User-Id", "X-Request-ID"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L); // Cache preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}

