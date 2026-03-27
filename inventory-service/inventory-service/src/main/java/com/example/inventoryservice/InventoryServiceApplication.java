package com.example.inventoryservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner runner(@Value("${kafka.bootstrap-servers}") String bootstrapServers) {
		return args -> {
			System.out.println("INVENTORY STARTED WITH kafka.bootstrap-servers=" + bootstrapServers);
			System.out.println("INVENTORY VERSION MARKER = BUILD-NEW");
		};
	}
}
