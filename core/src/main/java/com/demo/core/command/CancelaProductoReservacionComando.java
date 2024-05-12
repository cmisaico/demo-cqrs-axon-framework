package com.demo.core.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CancelaProductoReservacionComando {
    @TargetAggregateIdentifier
    private final String productoId;
    private final String ordenId;
    private final String usuarioId;
    private final int cantidad;
    private final String razon;
}
