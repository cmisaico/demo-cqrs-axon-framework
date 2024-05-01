package com.demo.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductoServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoServicioApplication.class, args);
	}

}
