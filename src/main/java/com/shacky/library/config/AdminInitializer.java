package com.shacky.library.config;

import com.shacky.library.entities.Admin;
import com.shacky.library.repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(AdminRepository adminRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            if (adminRepo.findAll().isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("password")); // default password
                adminRepo.save(admin);
                System.out.println("Default admin created");
            }
        };
    }
}
