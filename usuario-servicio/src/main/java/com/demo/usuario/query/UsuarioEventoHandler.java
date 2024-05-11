package com.demo.usuario.query;

import com.demo.core.model.PagoDetalle;
import com.demo.core.model.Usuario;
import com.demo.core.query.FetchUsuarioPagoDetalleQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEventoHandler {

    @QueryHandler
    public Usuario findUsuarioPagoDetalle(FetchUsuarioPagoDetalleQuery query){
        PagoDetalle pagoDetalle = PagoDetalle.builder()
                .numeroTarjeta("1234567890123456")
                .cvv(123)
                .nombre("CHRISTIAN")
                .validoHastaMes(12)
                .validoHastaAnio(2030)
                .build();

        return Usuario.builder()
                .usuarioId(query.getUsuarioId())
                .primerNombre("Christian")
                .apellido("Medina")
                .pagoDetalle(pagoDetalle)
                .build();
    }
}
