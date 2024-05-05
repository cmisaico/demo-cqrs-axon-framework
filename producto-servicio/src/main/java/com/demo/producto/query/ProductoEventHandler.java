package com.demo.producto.query;

import com.demo.producto.core.data.ProductoEntity;
import com.demo.producto.core.evento.ProductoCreadoEvento;
import com.demo.producto.repository.ProductoRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("producto-group")
public class ProductoEventHandler {

    private final ProductoRepository productoRepository;

    public ProductoEventHandler(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException exception) throws Exception{
        throw exception;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception exception){
    }


    @EventHandler
    public void on(ProductoCreadoEvento evento){
        ProductoEntity productoEntity = new ProductoEntity();
        BeanUtils.copyProperties(evento, productoEntity);
        try {
            productoRepository.save(productoEntity);
        } catch (Exception e){
            throw new IllegalStateException("Error al guardar el producto");
        }
    }
}
