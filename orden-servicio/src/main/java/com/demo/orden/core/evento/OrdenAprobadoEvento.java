package com.demo.orden.core.evento;

import com.demo.orden.core.model.OrdenEstado;
import lombok.Value;

@Value
public class OrdenAprobadoEvento {
    private final String ordenId;
    private final OrdenEstado ordenEstado = OrdenEstado.APROBADO;
}
