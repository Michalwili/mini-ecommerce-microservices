package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.OrderCreatedEvent;
import com.example.orderservice.OrderProducer;
import com.example.orderservice.SimpleRateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Value("${inventory.service.url}")
    private String inventoryServiceUrl;
    private final RestTemplate restTemplate;
    private final SimpleRateLimiter rateLimiter;
    private final OrderProducer orderProducer;
    private final List<OrderRequest> orders = new ArrayList<>();

    public OrderController(RestTemplate restTemplate, SimpleRateLimiter rateLimiter, OrderProducer orderProducer) {
        this.restTemplate = restTemplate;
        this.rateLimiter = rateLimiter;
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest req) {

        if (!rateLimiter.allowRequest()) {
            return ResponseEntity.status(429).body("Too many requests");
        }

        String url = inventoryServiceUrl + "/inventory/check?productId="
                + req.getProductId() + "&quantity=" + req.getQuantity();

        Boolean available = restTemplate.getForObject(url, Boolean.class);

        if (Boolean.FALSE.equals(available)) {
            return ResponseEntity.badRequest().body("Out of stock");
        }

        orders.add(req);

        orderProducer.sendOrderCreatedEvent(
                new OrderCreatedEvent(req.getProductId(), req.getQuantity())
        );

        return ResponseEntity.ok("Order created");

    }

}