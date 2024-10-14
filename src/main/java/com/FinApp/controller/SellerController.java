package com.FinApp.controller;

import com.FinApp.model.Sale;
import com.FinApp.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @Autowired SaleRepository saleRepository;

    @GetMapping("/dashboard")
    public String getSellerDashboard() {
        return "Seller Dashboard";
    }

    @PostMapping("/sales")
    public Sale createSale(@RequestBody Sale sale) {
        return saleRepository.save(sale);
    }

    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }
}
