package com.demo.orden.core.evento;

import com.demo.orden.core.model.OrdenEstado;
import lombok.Value;

@Value
public class OrdenRechazadoEvento {
    private final String ordenId;
    private final String razon;
    private final OrdenEstado ordenEstado = OrdenEstado.RECHAZADO;
}
