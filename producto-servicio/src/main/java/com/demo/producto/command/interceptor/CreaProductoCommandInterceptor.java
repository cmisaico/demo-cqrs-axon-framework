package com.demo.producto.command.interceptor;

import com.demo.producto.command.CreaProductoComando;
import com.demo.producto.core.data.ProductoLookupEntity;
import com.demo.producto.core.data.ProductoLookupRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreaProductoCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreaProductoCommandInterceptor.class);

    private final ProductoLookupRepository productoLookupRepository;

    public CreaProductoCommandInterceptor(ProductoLookupRepository productoLookupRepository) {
        this.productoLookupRepository = productoLookupRepository;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(
            @Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            LOGGER.info("Interceptando comando: {}", command.getPayloadType());
            if(CreaProductoComando.class.equals(command.getPayloadType())){
                CreaProductoComando creaProductoCommand = (CreaProductoComando) command.getPayload();
//                if(creaProductoCommand.getPrecio().compareTo(BigDecimal.ZERO) <= 0){
//                    throw new IllegalArgumentException("El precio del producto no puede ser menor o igual a cero");
//                }
//                if(creaProductoCommand.getTitulo() == null || creaProductoCommand.getTitulo().isBlank()){
//                    throw new IllegalArgumentException("El titulo del producto es requerido");
//                }
                ProductoLookupEntity productoLookupEntity = productoLookupRepository.findByProductoIdOrTitulo(creaProductoCommand.getProductoId(),
                        creaProductoCommand.getTitulo());
                if(productoLookupEntity != null){
                    throw new IllegalArgumentException(String.format("El producto con id %s o titulo %s ya existe",
                            creaProductoCommand.getProductoId(), creaProductoCommand.getTitulo()));
                }
            }
            return command;
        };
    }
}
