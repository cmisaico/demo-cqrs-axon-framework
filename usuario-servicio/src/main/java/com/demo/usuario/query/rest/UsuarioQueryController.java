package com.demo.usuario.query.rest;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioQueryController {

    @Autowired
    QueryGateway queryGateway;

    @GetMapping("/{usuarioId}/pago-detalles")
    public Usuario getUsuarioPagoDetalles(@PathVariable String usuarioId){
        FetchUsua
        return queryGateway.query(new EncuentraUsuarioQuery(usuarioId), ResponseTypes.instanceOf(Usuario.class)).join();
    }
}
