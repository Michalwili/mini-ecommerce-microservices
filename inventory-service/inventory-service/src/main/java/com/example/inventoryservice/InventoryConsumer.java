package com.example.inventoryservice;

import com.example.inventoryservice.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    private final InventoryService inventoryService;

    public InventoryConsumer(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order-created-v2", groupId = "inventory-group-v2")
    public void consume(OrderCreatedEvent event) {
        System.out.println("EVENT RECEIVED -> productId=" + event.getProductId() + ", quantity=" + event.getQuantity());
        inventoryService.decreaseStock(event.getProductId(), event.getQuantity());
        System.out.println("EVENT PROCESSED");
    }
}