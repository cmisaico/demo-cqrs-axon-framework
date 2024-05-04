package com.demo.producto.core.evento;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoCreadoEvento {
    private String productoId;
    private String titulo;
    private BigDecimal precio;
    private Integer cantidad;
}
