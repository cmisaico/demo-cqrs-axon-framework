package com.demo.orden.saga;

import com.demo.core.command.ReservadoProductoComando;
import com.demo.core.evento.ProductoReservadoEvento;
import com.demo.core.model.Usuario;
import com.demo.core.query.FetchUsuarioPagoDetalleQuery;
import com.demo.orden.core.evento.OrdenCreadoEvento;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;

@Saga
public class OrdenSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenSaga.class);

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(OrdenCreadoEvento ordenCreadoEvento){
        ReservadoProductoComando reservadoProductoComando = ReservadoProductoComando.builder()
                .productoId(ordenCreadoEvento.getProductoId())
                .ordenId(ordenCreadoEvento.getOrdenId())
                .cantidad(ordenCreadoEvento.getCantidad())
                .userId(ordenCreadoEvento.getUsuarioId())
                .build();

        LOGGER.info("OrdenCreadoEvento recibido para ordenId: {} y productoId: {}", ordenCreadoEvento.getOrdenId(), ordenCreadoEvento.getProductoId());

        commandGateway.send(reservadoProductoComando, (commandMessage, commandResultMessage) -> {
            if(commandResultMessage.isExceptional()){

            }
        });
    }

    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(@Nonnull ProductoReservadoEvento productoReservadoEvento){
        LOGGER.info("ProductoReservadoEvento recibido para ordenId: {} y productoId: {}", productoReservadoEvento.getOrdenId(), productoReservadoEvento.getProductoId());
        FetchUsuarioPagoDetalleQuery fetchUsuarioPagoDetalleQuery =
                new FetchUsuarioPagoDetalleQuery(productoReservadoEvento.getUserId());
        Usuario usuarioPagoDetalles = null;

        try {
            usuarioPagoDetalles = queryGateway
                    .query(fetchUsuarioPagoDetalleQuery, ResponseTypes.instanceOf(Usuario.class)).join();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return;
        }
        if(usuarioPagoDetalles == null){
            return;
        }
        LOGGER.info("Exito al obtener usuarioPagoDetalles para usuario " + usuarioPagoDetalles.getPrimerNombre());
    }
}
