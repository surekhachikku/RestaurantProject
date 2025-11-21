package com.auth.authservice.controller;

import com.auth.authservice.entity.User;
import com.auth.authservice.security.JwtUtil;
import com.auth.authservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        logger.info("Login attempt for user: {}", username);

        if (username == null || password == null) {
            logger.warn("Missing username or password in login request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username and password are required"));
        }

        // Validate user credentials from database
        Optional<User> userOpt = userService.validateUser(username, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getUsername());
            
            logger.info("Login successful for user: {}", username);
            
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Login failed for user: {} - Invalid credentials", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> userData) {
        String username = userData.get("username");
        String password = userData.get("password");
        String role = userData.get("role");

        logger.info("Registration attempt for user: {}", username);

        if (username == null || password == null) {
            logger.warn("Missing username or password in registration request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Username and password are required"));
        }

        try {
            User user = userService.createUser(username, password, role);
            
            logger.info("Registration successful for user: {}", username);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}