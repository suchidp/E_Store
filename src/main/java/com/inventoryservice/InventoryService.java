package com.inventoryservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InventoryService {
	public static void main(String[] args) {
		SpringApplication.run(InventoryService.class, args);
	}
}
