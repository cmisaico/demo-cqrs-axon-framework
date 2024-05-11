package com.demo.core.command;

import com.demo.core.model.PagoDetalle;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class ProcesoPagoComando {
    @TargetAggregateIdentifier
    private final String pagoId;
    private final String ordenId;
    private final PagoDetalle pagoDetalle;
}
