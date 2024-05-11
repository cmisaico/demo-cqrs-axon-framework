package com.demo.orden.command.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class CreaOrdenComando {
    @TargetAggregateIdentifier
    public final String ordenId;
    public final String usuarioId;
    private final String productoId;
    private final int cantidad;
    private final String direccionId;
    private final String ordenEstado;
}
