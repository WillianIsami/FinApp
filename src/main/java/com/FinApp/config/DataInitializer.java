package com.FinApp.config;

import com.FinApp.model.Role;
import com.FinApp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.findByName("ROLE_SELLER").isPresent()) {
            roleRepository.save(new Role(null, "ROLE_SELLER"));
            System.out.println("Created ROLE_SELLER");
        }
        if (!roleRepository.findByName("ROLE_MANAGER").isPresent()) {
            roleRepository.save(new Role(null, "ROLE_MANAGER"));
            System.out.println("Created ROLE_MANAGER");
        }
        if (!roleRepository.findByName("ROLE_BOSS").isPresent()) {
            roleRepository.save(new Role(null, "ROLE_BOSS"));
            System.out.println("Created ROLE_BOSS");
        }
    }
}