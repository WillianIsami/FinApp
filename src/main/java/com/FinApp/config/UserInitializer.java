package com.FinApp.config;

import com.FinApp.model.Role;
import com.FinApp.model.User;
import com.FinApp.repository.RoleRepository;
import com.FinApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializer(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByUsername("seller1").isPresent()) {
            Role sellerRole = roleRepository.findByName("ROLE_SELLER")
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            User seller = new User();
            seller.setUsername("seller1");
            seller.setEmail("seller1@gmail.com");
            seller.setPassword(passwordEncoder.encode("password123"));
            seller.setRoles(Collections.singleton(sellerRole));

            userRepository.save(seller);
        }
    }
}