package com.demo.core.evento;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoReservadoEvento {
    private final String productoId;
    private final String ordenId;
    private final String userId;
    private final int cantidad;
}
