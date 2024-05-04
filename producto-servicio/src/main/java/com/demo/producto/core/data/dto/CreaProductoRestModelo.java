package com.demo.producto.core.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreaProductoRestModelo {
    @NotBlank(message = "El titulo es requerido")
    private String titulo;

    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Max(value = 100, message = "La cantidad no puede ser mayor a 100")
    private Integer cantidad;
}
