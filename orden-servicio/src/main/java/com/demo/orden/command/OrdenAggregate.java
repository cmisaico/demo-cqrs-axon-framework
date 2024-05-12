package com.demo.orden.command;

import com.demo.orden.command.commands.ApruebaOrdenComando;
import com.demo.orden.command.commands.CreaOrdenComando;
import com.demo.orden.command.commands.RechazaOrdenComando;
import com.demo.orden.core.evento.OrdenAprobadoEvento;
import com.demo.orden.core.evento.OrdenCreadoEvento;
import com.demo.orden.core.evento.OrdenRechazadoEvento;
import com.demo.orden.core.model.OrdenEstado;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrdenAggregate {

    @AggregateIdentifier
    private String ordenId;
    private String userId;
    private String productoId;
    private int cantidad;
    private String direccionId;
    private OrdenEstado ordenEstado;

    public OrdenAggregate() {
    }

    @CommandHandler
    public OrdenAggregate(CreaOrdenComando comando) {
        OrdenCreadoEvento ordenCreadoEvento = new OrdenCreadoEvento();
        BeanUtils.copyProperties(comando, ordenCreadoEvento);
        AggregateLifecycle.apply(ordenCreadoEvento);
    }

    @EventSourcingHandler
    protected void on(OrdenCreadoEvento evento) throws Exception{
        this.ordenId = evento.getOrdenId();
        this.userId = evento.getUsuarioId();
        this.productoId = evento.getProductoId();
        this.cantidad = evento.getCantidad();
        this.direccionId = evento.getDireccionId();
        this.ordenEstado = evento.getOrdenEstado();
    }

    @CommandHandler
    public void handle(ApruebaOrdenComando comando) {
        OrdenAprobadoEvento ordenAprobadoEvento = new OrdenAprobadoEvento(comando.getOrdenId());
        AggregateLifecycle.apply(ordenAprobadoEvento);
    }

    @EventSourcingHandler
    protected void on(OrdenAprobadoEvento evento) {
        this.ordenEstado = evento.getOrdenEstado();
    }

    @CommandHandler
    public void handle(RechazaOrdenComando comando) {
        OrdenRechazadoEvento evento = new OrdenRechazadoEvento(comando.getOrdenId(),
                comando.getRazon());
        AggregateLifecycle.apply(evento);
    }

    @EventSourcingHandler
    public void on(OrdenRechazadoEvento evento) {
        this.ordenEstado = evento.getOrdenEstado();
    }
}
