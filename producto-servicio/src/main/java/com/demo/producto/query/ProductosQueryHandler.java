package com.demo.producto.query;

import com.demo.producto.core.data.ProductoEntity;
import com.demo.producto.query.controller.ProductoRestModel;
import com.demo.producto.repository.ProductoRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductosQueryHandler {

    private final ProductoRepository productoRepository;

    public ProductosQueryHandler(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @QueryHandler
    public List<ProductoRestModel> encontrarProductos(EncontrarProductosQuery query){
        List<ProductoRestModel> productosRest = new ArrayList<>();
        List<ProductoEntity> productosAlmacenados = productoRepository.findAll();
        for(ProductoEntity productoEntity : productosAlmacenados) {
            ProductoRestModel productoRestModel = new ProductoRestModel();
            BeanUtils.copyProperties(productoEntity, productoRestModel);
            productosRest.add(productoRestModel);
        }
        return productosRest;
    }


}
