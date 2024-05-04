package com.demo.producto.query.controller;

import com.demo.producto.query.EncontrarProductosQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoQueryController {


    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<ProductoRestModel> getProductos() {
        EncontrarProductosQuery encontrarProductosQuery = new EncontrarProductosQuery();
        return queryGateway.query(encontrarProductosQuery,
                ResponseTypes.multipleInstancesOf(ProductoRestModel.class)).join();
    }

}
