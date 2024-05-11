package com.demo.orden.command.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class RechazaOrdenComando {
    @TargetAggregateIdentifier
    private final String ordenId;
    private final String razon;
}
