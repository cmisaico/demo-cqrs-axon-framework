package com.demo.producto.query;

import com.demo.core.evento.ProductoReservadoEvento;
import com.demo.producto.core.data.ProductoEntity;
import com.demo.producto.core.evento.ProductoCreadoEvento;
import com.demo.producto.repository.ProductoRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("producto-grupo")
public class ProductoEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoEventHandler.class);

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
        } catch (IllegalArgumentException ex){
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void on(ProductoReservadoEvento evento){
        ProductoEntity productoEntity = productoRepository.findByProductoId(evento.getProductoId());
        productoEntity.setCantidad(productoEntity.getCantidad() - evento.getCantidad());
        productoRepository.save(productoEntity);

        LOGGER.info("ProductoReservadoEvento recibido para productoId: {} y ordenId: {}", evento.getProductoId(), evento.getOrdenId());
    }
}
