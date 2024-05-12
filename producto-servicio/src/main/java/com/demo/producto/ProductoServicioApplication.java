package com.demo.producto;

import com.demo.producto.command.interceptor.CreaProductoCommandInterceptor;
import com.demo.producto.core.errorhandling.ProductoEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

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

	@Autowired
	public void configure(EventProcessingConfigurer config){
		config.registerListenerInvocationErrorHandler("producto-group",
				conf -> new ProductoEventsErrorHandler());
	}

	@Bean(name = "productoSnapshotTriggerDefinition")
	public SnapshotTriggerDefinition snapshotTriggerDefinition(Snapshotter snapshotter){
		return new EventCountSnapshotTriggerDefinition(snapshotter, 3);
	}

}
