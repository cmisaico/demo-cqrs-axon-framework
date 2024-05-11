package com.demo.core.evento;

import lombok.Value;

@Value
public class PagoProcesadoEvento {
    private final String ordenId;
    private final String pagoId;
}
