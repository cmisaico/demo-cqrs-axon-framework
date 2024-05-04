package com.demo.producto.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoLookupRepository extends JpaRepository<ProductoLookupEntity, String> {

    ProductoLookupEntity findByProductoIdOrTitulo(String productoId, String titulo);

}
