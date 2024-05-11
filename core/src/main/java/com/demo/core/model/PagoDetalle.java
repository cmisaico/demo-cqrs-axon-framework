package com.demo.core.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagoDetalle {
    private final String nombre;
    private final String numeroTarjeta;
    private final int validoHastaMes;
    private final int validoHastaAnio;
    private final int cvv;
}
