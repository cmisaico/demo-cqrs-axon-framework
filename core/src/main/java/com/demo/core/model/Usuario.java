package com.demo.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Usuario {
    private final String primerNombre;
    private final String apellido;
    private final String usuarioId;
    private final PagoDetalle pagoDetalle;
}
