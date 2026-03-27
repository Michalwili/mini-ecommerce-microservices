package com.example.inventoryservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {

    private final Map<Long, Integer> stock = new HashMap<>();

    public InventoryService() {
        stock.put(1L, 10);
        stock.put(2L, 5);
        stock.put(3L, 0);
    }

    public boolean isAvailable(Long productId, int quantity) {
        return stock.getOrDefault(productId, 0) >= quantity;
    }

    public void decreaseStock(Long productId, int quantity) {
        int current = stock.getOrDefault(productId, 0);
        stock.put(productId, current - quantity);
        System.out.println("STOCK UPDATED -> productId=" + productId + ", newStock=" + stock.get(productId));
    }

    public int getStock(Long productId) {
        return stock.getOrDefault(productId, 0);
    }
}