package com.sagar.config;

import com.sagar.model.User;
import com.sagar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if an ADMIN user exists
        if (userRepository.findByRole("ADMIN") == null) {
            System.out.println("Creating default ADMIN user...");

            // Create a new ADMIN user
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword("admin"); // Use encoded password
            adminUser.setRole("ADMIN");
            adminUser.setActive(true);

            // Save the ADMIN user to the database
            userRepository.save(adminUser);

            System.out.println("Default ADMIN user created.");
        } else {
            System.out.println("ADMIN user already exists.");
        }
    }
}
