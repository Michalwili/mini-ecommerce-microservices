package com.example.orderservice;

import org.springframework.stereotype.Component;

@Component
public class SimpleRateLimiter {

    private static final int LIMIT = 10;
    private static final long WINDOW_MS = 60000;

    private long windowStart = System.currentTimeMillis();
    private int requestCount = 0;

    public synchronized boolean allowRequest() {
        long now = System.currentTimeMillis();

        if (now - windowStart >= WINDOW_MS) {
            windowStart = now;
            requestCount = 0;
            System.out.println("Rate limit window reset");
        }

        if (requestCount >= LIMIT) {
            System.out.println("requestCount=" + requestCount + ", allowed=false");
            return false;
        }

        requestCount++;
        System.out.println("requestCount=" + requestCount + ", allowed=true");
        return true;
    }
}