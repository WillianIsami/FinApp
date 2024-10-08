package com.FinApp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('SELLER')")
    public String getSellerDashboard() {
        return "Seller Dashboard Data";
    }
}
