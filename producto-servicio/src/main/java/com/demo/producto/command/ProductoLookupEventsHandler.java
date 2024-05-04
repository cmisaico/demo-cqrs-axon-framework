package com.demo.producto.command;

import com.demo.producto.core.data.ProductoLookupEntity;
import com.demo.producto.core.data.ProductoLookupRepository;
import com.demo.producto.core.evento.ProductoCreadoEvento;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("producto-group")
public class ProductoLookupEventsHandler {

    private final ProductoLookupRepository productoLookupRepository;

    public ProductoLookupEventsHandler(ProductoLookupRepository productoLookupRepository) {
        this.productoLookupRepository = productoLookupRepository;
    }

    @EventHandler
    public void on(ProductoCreadoEvento evento){
        ProductoLookupEntity productoLookupEntity = new ProductoLookupEntity(evento.getProductoId(),
                evento.getTitulo());
        productoLookupRepository.save(productoLookupEntity);
    }
}
