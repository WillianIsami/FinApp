package com.FinApp.config;

import com.FinApp.model.Role;
import com.FinApp.model.User;
import com.FinApp.repository.RoleRepository;
import com.FinApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Order(2)
public class UserInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.findByUsername("admin").isPresent()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("adminpassword"));

            Role bossRole = roleRepository.findByName("ROLE_BOSS")
                    .orElseThrow(() -> new RuntimeException("ROLE_BOSS not found"));
            Role managerRoller = roleRepository.findByName("ROLE_MANAGER")
                    .orElseThrow(() -> new RuntimeException("ROLE_BOSS not found"));
            Role sellerRole = roleRepository.findByName("ROLE_SELLER")
                    .orElseThrow(() -> new RuntimeException("ROLE_BOSS not found"));

            Set<Role> roles = new HashSet<>(List.of(bossRole, managerRoller, sellerRole));
            admin.setRoles(roles);

            userRepository.save(admin);
            System.out.println("Created admin user");
        }

        // Similarly, create other users like managers or sellers
        if (!userRepository.findByUsername("manager1").isPresent()) {
            User manager = new User();
            manager.setUsername("manager1");
            manager.setEmail("manager1@gmail.com");
            manager.setPassword(passwordEncoder.encode("managerpassword"));

            Role managerRole = roleRepository.findByName("ROLE_MANAGER")
                    .orElseThrow(() -> new RuntimeException("ROLE_MANAGER not found"));

            Set<Role> roles = new HashSet<>();
            roles.add(managerRole);
            manager.setRoles(roles);

            userRepository.save(manager);
            System.out.println("Created manager1 user");
        }

        if (!userRepository.findByUsername("seller1").isPresent()) {
            User seller = new User();
            seller.setUsername("seller1");
            seller.setEmail("seller1@gmail.com");
            seller.setPassword(passwordEncoder.encode("sellerpassword"));

            Role sellerRole = roleRepository.findByName("ROLE_SELLER")
                    .orElseThrow(() -> new RuntimeException("ROLE_SELLER not found"));

            Set<Role> roles = new HashSet<>();
            roles.add(sellerRole);
            seller.setRoles(roles);

            userRepository.save(seller);
            System.out.println("Created seller1 user");
        }
    }
}