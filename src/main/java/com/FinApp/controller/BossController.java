package com.FinApp.controller;

import com.FinApp.dto.UserDTO;
import com.FinApp.repository.RoleRepository;
import com.FinApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/boss")
public class BossController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/dashboard")
    public String bossDashboard() {
        return "Boss Dashboard";
    }

    @GetMapping("/managers")
    public List<UserDTO> getAllManagers() {
        return userRepository.findUsersByRoleName("MANAGER");
    }

    @GetMapping("/sellers")
    public List<UserDTO> getAllSellers() {
        return userRepository.findUsersByRoleName("SELLER");
    }
}