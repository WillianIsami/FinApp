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
        if (!roleRepository.findByName("SELLER").isPresent()) {
            roleRepository.save(new Role(null, "SELLER"));
            System.out.println("Created SELLER");
        }
        if (!roleRepository.findByName("MANAGER").isPresent()) {
            roleRepository.save(new Role(null, "MANAGER"));
            System.out.println("Created MANAGER");
        }
        if (!roleRepository.findByName("BOSS").isPresent()) {
            roleRepository.save(new Role(null, "BOSS"));
            System.out.println("Created BOSS");
        }
    }
}