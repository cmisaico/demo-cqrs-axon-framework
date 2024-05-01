package com.demo.producto.models.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreaProductoRestModelo {
    private String titulo;
    private BigDecimal precio;
    private Integer cantidad;
}
