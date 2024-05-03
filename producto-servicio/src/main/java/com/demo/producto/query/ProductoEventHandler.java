package com.demo.producto.query;

import com.demo.producto.entity.ProductoEntity;
import com.demo.producto.evento.ProductoCreadoEvento;
import com.demo.producto.repository.ProductoRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductoEventHandler {

    private final ProductoRepository productoRepository;

    public ProductoEventHandler(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @EventHandler
    public void on(ProductoCreadoEvento evento){
        ProductoEntity productoEntity = new ProductoEntity();
        BeanUtils.copyProperties(evento, productoEntity);
        productoRepository.save(productoEntity);
    }
}
