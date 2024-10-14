package com.FinApp.controller;

import com.FinApp.dto.UserDTO;
import com.FinApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String managerDashboard() {
        return "Manager dashboard";
    }

    @GetMapping("/sellers")
    public List<UserDTO> getAllSellers() {
        return userRepository.findUsersByRoleName("SELLER");
    }
}