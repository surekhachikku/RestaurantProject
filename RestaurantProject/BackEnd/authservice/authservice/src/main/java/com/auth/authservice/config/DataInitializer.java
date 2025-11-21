package com.auth.authservice.config;

import com.auth.authservice.entity.User;
import com.auth.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Initializes default admin user in the database if it doesn't exist
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if admin user exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            logger.info("Creating default admin user...");
            
            User admin = new User();
            admin.setUsername("admin");
            // Encode the password before storing
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole("ROLE_ADMIN");
            
            userRepository.save(admin);
            logger.info("Default admin user created successfully - username: admin, password: password");
        } else {
            logger.info("Admin user already exists in database");
        }
    }
}

