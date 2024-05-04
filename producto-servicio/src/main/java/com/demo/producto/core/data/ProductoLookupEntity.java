package com.demo.producto.core.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productolookup")
public class ProductoLookupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String productoId;

    @Column(unique = true)
    private String titulo;
}
