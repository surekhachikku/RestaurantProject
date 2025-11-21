package com.restaurant.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtAuthenticationFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    // In production, inject from @Value("${jwt.secret}") or use secrets manager
    private final String secretKey;

    public JwtAuthenticationFilter(@org.springframework.beans.factory.annotation.Value("${jwt.secret:mySuperSecretKeyThatIsAtLeast32Chars!}") String secretKey) {
        this.secretKey = secretKey;
        logger.info("JWT Authentication Filter initialized");
    }
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        HttpMethod method = request.getMethod();
        String clientIp = request.getRemoteAddress() != null ? 
                request.getRemoteAddress().getAddress().getHostAddress() : "unknown";

        logger.info("=== JWT Filter - Request Received ===");
        logger.info("Method: {}, Path: {}, Client IP: {}", method, path, clientIp);
        logger.debug("Request URI: {}", request.getURI());
        logger.debug("Request Headers: {}", request.getHeaders());

        // Skip auth for login/register endpoints
        if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
            logger.info("Skipping JWT validation for public endpoint: {}", path);
            // Still add gateway header for public endpoints
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Gateway-Request", "true")
                    .build();
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();
            return chain.filter(mutatedExchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        logger.debug("Authorization header present: {}", authHeader != null);
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Missing or invalid Authorization header for path: {}", path);
            logger.warn("Expected format: 'Bearer <token>'");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        logger.debug("JWT Token extracted (length: {})", token.length());
        logger.trace("JWT Token (first 20 chars): {}...", token.substring(0, Math.min(20, token.length())));

        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            logger.info("JWT Token validated successfully for user: {}", username);
            logger.debug("Token claims: subject={}, issuedAt={}, expiration={}", 
                    claims.getSubject(), 
                    claims.getIssuedAt(), 
                    claims.getExpiration());

            // Pass username downstream by mutating the request
            // Add gateway header to identify requests from API Gateway
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", username)
                    .header("X-Gateway-Request", "true")
                    .build();
            
            logger.debug("Added X-User-Id header: {}", username);
            
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            logger.info("Request forwarded to downstream service with user: {}", username);
            return chain.filter(mutatedExchange);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            logger.error("JWT Token expired for path: {}", path, e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            logger.error("Malformed JWT Token for path: {}", path, e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (io.jsonwebtoken.security.SignatureException e) {
            logger.error("JWT Signature validation failed for path: {}", path, e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        } catch (Exception e) {
            logger.error("JWT validation failed for path: {} - Error: {}", path, e.getMessage(), e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}