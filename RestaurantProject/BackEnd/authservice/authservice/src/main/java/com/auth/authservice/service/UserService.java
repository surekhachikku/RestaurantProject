package com.auth.authservice.service;

import com.auth.authservice.entity.User;
import com.auth.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Find user by username
     */
    public Optional<User> findByUsername(String username) {
        logger.debug("Fetching user from database: {}", username);
        return userRepository.findByUsername(username);
    }

    /**
     * Validate user credentials
     * @param username the username
     * @param password the plain text password
     * @return User if credentials are valid, empty otherwise
     */
    public Optional<User> validateUser(String username, String password) {
        logger.info("Validating credentials for user: {}", username);
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        
        if (userOpt.isEmpty()) {
            logger.warn("User not found in database: {}", username);
            return Optional.empty();
        }

        User user = userOpt.get();
        
        // Check if password matches (using BCrypt)
        if (passwordEncoder.matches(password, user.getPassword())) {
            logger.info("Password validation successful for user: {}", username);
            return Optional.of(user);
        } else {
            logger.warn("Password validation failed for user: {}", username);
            return Optional.empty();
        }
    }

    /**
     * Create a new user (for registration)
     */
    public User createUser(String username, String password, String role) {
        logger.info("Creating new user: {}", username);
        
        // Check if user already exists
        if (userRepository.findByUsername(username).isPresent()) {
            logger.warn("User already exists: {}", username);
            throw new RuntimeException("User already exists: " + username);
        }

        User user = new User();
        user.setUsername(username);
        // Encode password before storing
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role != null ? role : "ROLE_USER");
        
        User savedUser = userRepository.save(user);
        logger.info("User created successfully: {}", username);
        return savedUser;
    }
}
