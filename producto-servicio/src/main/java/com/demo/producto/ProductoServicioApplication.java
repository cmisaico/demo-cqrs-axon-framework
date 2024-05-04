package com.demo.producto;

import com.demo.producto.command.interceptor.CreaProductoCommandInterceptor;
import org.axonframework.commandhandling.CommandBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductoServicioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductoServicioApplication.class, args);
	}

	@Autowired
	public void registrarCreaProductoCommandInterceptor(ApplicationContext context, CommandBus commandBus){
		commandBus.registerDispatchInterceptor(context.getBean(CreaProductoCommandInterceptor.class));
	}
}
