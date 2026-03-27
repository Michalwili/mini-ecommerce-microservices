package com.example.inventoryservice.controller;

import com.example.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/check")
    public boolean check(@RequestParam Long productId,
                         @RequestParam int quantity) {
        return inventoryService.isAvailable(productId, quantity);
    }

    @GetMapping("/stock")
    public int getStock(@RequestParam Long productId) {
        return inventoryService.getStock(productId);
    }
}