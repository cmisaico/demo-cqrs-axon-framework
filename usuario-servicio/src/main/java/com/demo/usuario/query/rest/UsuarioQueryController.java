package com.demo.usuario.query.rest;

import com.demo.core.model.Usuario;
import com.demo.core.query.FetchUsuarioPagoDetalleQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
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
        FetchUsuarioPagoDetalleQuery query = new FetchUsuarioPagoDetalleQuery(usuarioId);
        return queryGateway.query(query, ResponseTypes.instanceOf(Usuario.class)).join();
    }
}
