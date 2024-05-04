package com.demo.producto.command;


import com.demo.producto.core.evento.ProductoCreadoEvento;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductoAggregate {

    @AggregateIdentifier
    private String productoId;
    private String titulo;
    private BigDecimal precio;
    private Integer cantidad;

    public ProductoAggregate() {
    }

    @CommandHandler
    public ProductoAggregate(CreaProductoComando comando) {
        if(comando.getTitulo() == null || comando.getTitulo().isBlank()){
            throw new IllegalArgumentException("El título del producto no puede ser nulo o vacío");
        }
        if(comando.getPrecio() == null || comando.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("El precio del producto no puede ser nulo o menor o igual a cero");
        }

        ProductoCreadoEvento productoCreadoEvento = new ProductoCreadoEvento();
        BeanUtils.copyProperties(comando, productoCreadoEvento);
        AggregateLifecycle.apply(productoCreadoEvento);
    }

    @EventSourcingHandler
    public void on(ProductoCreadoEvento evento){
        this.productoId = evento.getProductoId();
        this.titulo = evento.getTitulo();
        this.precio = evento.getPrecio();
        this.cantidad = evento.getCantidad();
    }
}
