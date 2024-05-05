package com.demo.orden.core.model;

import lombok.Value;

@Value
public class OrdenResumen {

    private final String ordenId;
    private final OrdenEstado ordenEstado;
    private final String mensaje;
}
