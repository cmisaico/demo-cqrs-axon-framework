package com.demo.producto.query.controller;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoRestModel {
    private String productoId;
    private String titulo;
    private BigDecimal precio;
    private Integer cantidad;
}
