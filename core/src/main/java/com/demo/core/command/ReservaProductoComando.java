package com.demo.core.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ReservaProductoComando {

    @TargetAggregateIdentifier
    private final String productoId;
    private final String ordenId;
    private final String userId;
    private final int cantidad;
}