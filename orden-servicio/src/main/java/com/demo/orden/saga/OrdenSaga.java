package com.demo.orden.saga;

import com.demo.orden.core.evento.OrdenCreadoEvento;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrdenSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "ordenId")
    public void handle(OrdenCreadoEvento ordenCreadoEvento){

    }
}
