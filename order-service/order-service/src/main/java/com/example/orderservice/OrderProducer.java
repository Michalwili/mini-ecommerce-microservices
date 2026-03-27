package com.example.orderservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("SENDING EVENT -> productId=" + event.getProductId() + ", quantity=" + event.getQuantity());
        kafkaTemplate.send("order-created-v2", event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        System.out.println("KAFKA SEND FAILED -> " + ex.getMessage());
                    } else {
                        System.out.println("KAFKA SEND SUCCESS -> topic=" + result.getRecordMetadata().topic()
                                + ", partition=" + result.getRecordMetadata().partition()
                                + ", offset=" + result.getRecordMetadata().offset());
                    }
                });
    }
}