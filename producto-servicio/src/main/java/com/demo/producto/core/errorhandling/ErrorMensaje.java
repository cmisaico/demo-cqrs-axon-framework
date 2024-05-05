package com.demo.producto.core.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorMensaje {
    private final Date timestamp;
    private final String mensaje;
}
