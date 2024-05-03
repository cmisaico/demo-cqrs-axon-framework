package com.demo.producto.repository;

import com.demo.producto.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity, String> {
    ProductoEntity findByProductoId(String productoId);
    ProductoEntity findByProductoIdOrTitulo(String productoId, String titulo);
}
