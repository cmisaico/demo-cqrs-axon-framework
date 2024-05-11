package com.demo.orden.command.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class ApruebaOrdenComando {
    @TargetAggregateIdentifier
    private final String ordenId;
}
