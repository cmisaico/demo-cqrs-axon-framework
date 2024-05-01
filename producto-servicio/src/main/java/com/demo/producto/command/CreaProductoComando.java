package com.demo.producto.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Builder
@Data
public class CreaProductoComando {

    @TargetAggregateIdentifier
    private final String productoId;
    private final String titulo;
    private final BigDecimal precio;
    private final Integer cantidad;

}
