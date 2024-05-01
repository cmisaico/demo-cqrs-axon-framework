package com.demo.producto.controller;

import com.demo.producto.command.CreaProductoComando;
import com.demo.producto.models.dto.CreaProductoRestModelo;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private Environment env;
    private final CommandGateway commandGateway;

    public ProductoController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String crearProducto(@RequestBody CreaProductoRestModelo creaProductoRestModelo) {
        CreaProductoComando creaProductoComando = CreaProductoComando.builder()
                .productoId(UUID.randomUUID().toString())
                .titulo(creaProductoRestModelo.getTitulo())
                .precio(creaProductoRestModelo.getPrecio())
                .cantidad(creaProductoRestModelo.getCantidad())
                .build();
        String valorRetorno;

        try {
            valorRetorno = commandGateway.sendAndWait(creaProductoComando);
        } catch(Exception ex){
            valorRetorno = ex.getLocalizedMessage();
        }
        return valorRetorno;
    }

}
