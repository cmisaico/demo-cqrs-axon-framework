package com.demo.orden.query;

import com.demo.orden.core.data.OrdenEntity;
import com.demo.orden.core.data.OrdenRepository;
import com.demo.orden.core.evento.OrdenAprobadoEvento;
import com.demo.orden.core.evento.OrdenCreadoEvento;
import com.demo.orden.core.evento.OrdenRechazadoEvento;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("orden-grupo")
public class OrdenEventosHandler {

    private final OrdenRepository ordenRepository;

    public OrdenEventosHandler(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    @EventHandler
    public void on(OrdenCreadoEvento evento){
        OrdenEntity ordenEntity = new OrdenEntity();
        BeanUtils.copyProperties(evento, ordenEntity);
        ordenRepository.save(ordenEntity);
    }

    @EventHandler
    public void on(OrdenAprobadoEvento evento){
        OrdenEntity ordenEntity = ordenRepository.findByOrdenId(evento.getOrdenId());
        if(ordenEntity == null){
            return;
        }
        ordenEntity.setOrdenEstado(evento.getOrdenEstado());
        ordenRepository.save(ordenEntity);
    }

    @EventHandler
    public void on(OrdenRechazadoEvento evento){
        OrdenEntity ordenEntity = ordenRepository.findByOrdenId(evento.getOrdenId());
        ordenEntity.setOrdenEstado(evento.getOrdenEstado());
        ordenRepository.save(ordenEntity);
    }
}
