package com.demo.orden.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<OrdenEntity, String> {
    OrdenEntity findByOrdenId(String ordenId);
}
