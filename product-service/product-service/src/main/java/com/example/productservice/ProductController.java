package com.example.productservice;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final List<Product> products = List.of(
            new Product(1L, "Laptop"),
            new Product(2L, "Mouse"),
            new Product(3L, "Keyboard")
    );

    @Cacheable("products")
    @GetMapping
    public List<Product> getAll() {
        System.out.println("Fetching all products from source...");
        return products;
    }

    @Cacheable(value = "product", key = "#id")
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        System.out.println("Fetching product by id from source...");
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));    }
}