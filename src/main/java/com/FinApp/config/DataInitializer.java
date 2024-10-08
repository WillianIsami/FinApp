package com.FinApp.config;

import com.FinApp.model.Role;
import com.FinApp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!roleRepository.findByName("ROLE_SELLER").isPresent()) {
            Role role = new Role();
            role.setName("ROLE_SELLER");
            roleRepository.save(role);
        }
        if (!roleRepository.findByName("ROLE_MANAGER").isPresent()) {
            Role role = new Role();
            role.setName("ROLE_MANAGER");
            roleRepository.save(role);
        }
        if (!roleRepository.findByName("ROLE_BOSS").isPresent()) {
            Role role = new Role();
            role.setName("ROLE_BOSS");
            roleRepository.save(role);
        }
    }
}
