package com.demo.orden.core.data;

import com.demo.orden.core.model.OrdenEstado;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "ordenes")
public class OrdenEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true)
    public String ordenId;
    private String productoId;
    private String usuarioId;
    private int cantidad;
    private String direccionId;

    @Enumerated(EnumType.STRING)
    private OrdenEstado ordenEstado;

}
