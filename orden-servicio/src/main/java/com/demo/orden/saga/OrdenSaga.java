package com.demo.orden.saga;

import com.demo.core.command.CancelaProductoReservacionComando;
import com.demo.core.command.ProcesoPagoComando;
import com.demo.core.command.ReservaProductoComando;
import com.demo.core.evento.PagoProcesadoEvento;
import com.demo.core.evento.ProductoReservacionCanceladoEvento;
import com.demo.core.evento.ProductoReservadoEvento;
import com.demo.core.model.Usuario;
import com.demo.core.query.FetchUsuarioPagoDetalleQuery;
import com.demo.orden.command.commands.ApruebaOrdenComando;
import com.demo.orden.command.commands.RechazaOrdenComando;
import com.demo.orden.core.evento.OrdenAprobadoEvento;
import com.demo.orden.core.evento.OrdenCreadoEvento;
import com.demo.orden.core.evento.OrdenRechazadoEvento;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Saga
public class OrdenSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdenSaga.class);

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient DeadlineManager deadlineManager;

    private final String PAGO_PROCESO_TIMEOUT_DEADLINE = "pago-proceso-deadline";

    @StartSaga
    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(OrdenCreadoEvento ordenCreadoEvento){
        ReservaProductoComando reservadoProductoComando = ReservaProductoComando.builder()
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
            cancelProductoReservacion(productoReservadoEvento, e.getMessage());
            return;
        }
        if(usuarioPagoDetalles == null){
            cancelProductoReservacion(productoReservadoEvento, "No se pudo obtener el usuario");
            return;
        }
        LOGGER.info("Exito al obtener usuarioPagoDetalles para usuario {}", usuarioPagoDetalles.getPrimerNombre());

        deadlineManager.schedule(Duration.of(10, ChronoUnit.SECONDS),
                PAGO_PROCESO_TIMEOUT_DEADLINE, productoReservadoEvento);

        if(true) return;

        ProcesoPagoComando procesoPagoComando = ProcesoPagoComando.builder()
                .ordenId(productoReservadoEvento.getOrdenId())
                .pagoDetalle(usuarioPagoDetalles.getPagoDetalle())
                .pagoId(UUID.randomUUID().toString())
                .build();
        String resultado = null;
        try {
            resultado = commandGateway.sendAndWait(procesoPagoComando, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            cancelProductoReservacion(productoReservadoEvento, e.getMessage());
            return;
        }

        if(resultado == null){
            LOGGER.info("El proceso d epago ha devuelto un resultado nulo. Iniciando compensacion");
            cancelProductoReservacion(productoReservadoEvento, "No puede procesar el usuario de pago");
        }
    }

    private void cancelProductoReservacion(ProductoReservadoEvento evento, String razon){
        CancelaProductoReservacionComando comando = CancelaProductoReservacionComando.builder()
                .ordenId(evento.getOrdenId())
                .productoId(evento.getProductoId())
                .cantidad(evento.getCantidad())
                .usuarioId(evento.getUserId())
                .razon(razon)
                .build();
        commandGateway.send(comando);
    }

    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(@Nonnull PagoProcesadoEvento evento){
        deadlineManager.cancelAll(PAGO_PROCESO_TIMEOUT_DEADLINE);
        ApruebaOrdenComando aprobadoOrdenComando =
                new ApruebaOrdenComando(evento.getOrdenId());
        commandGateway.send(aprobadoOrdenComando);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(OrdenAprobadoEvento ordenAprobadoEvento){
        LOGGER.info("Orden esta aprobado para ordenId: {}", ordenAprobadoEvento.getOrdenId());
    }

    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(ProductoReservacionCanceladoEvento evento){
        RechazaOrdenComando comando = new RechazaOrdenComando(evento.getOrdenId(),
                evento.getRazon());
        commandGateway.send(comando);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(OrdenRechazadoEvento evento){
        LOGGER.info("Orden esta rechazado para ordenId: {}", evento.getOrdenId());
    }

    @DeadlineHandler(deadlineName = PAGO_PROCESO_TIMEOUT_DEADLINE)
    public void handlePagoProcesoTimeout(ProductoReservadoEvento evento){
        LOGGER.info("El proceso de pago ha expirado para ordenId: {}", evento.getOrdenId());
        cancelProductoReservacion(evento, "El proceso de pago ha expirado");
    }
}
