package com.demo.orden.core.evento;

import com.demo.orden.core.model.OrdenEstado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenCreadoEvento {
    private String ordenId;
    private String productoId;
    private String usuarioId;
    private int cantidad;
    private String direccionId;
    private OrdenEstado ordenEstado;
}
