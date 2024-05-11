package com.demo.core.evento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoReservacionCanceladoEvento {
    private final String productoId;
    private final int cantidad;
    private final String ordenId;
    private final String usuarioId;
    private final String razon;
}
