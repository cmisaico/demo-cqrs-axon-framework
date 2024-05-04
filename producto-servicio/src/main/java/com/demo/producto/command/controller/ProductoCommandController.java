package com.demo.producto.command.controller;

import com.demo.producto.command.CreaProductoComando;
import com.demo.producto.core.data.dto.CreaProductoRestModelo;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/producto")
public class ProductoCommandController {

    private Environment env;
    private final CommandGateway commandGateway;

    public ProductoCommandController(Environment env, CommandGateway commandGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String crearProducto(@Valid @RequestBody CreaProductoRestModelo creaProductoRestModelo) {
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

    @GetMapping
    public String obtenerProducto(){
        return "Obtener Producto : " + env.getProperty("local.server.port");
    }

}
