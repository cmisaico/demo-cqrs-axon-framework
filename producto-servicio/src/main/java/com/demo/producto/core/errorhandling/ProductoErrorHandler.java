package com.demo.producto.core.errorhandling;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ProductoErrorHandler {

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorMensaje errorMensaje = new ErrorMensaje(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMensaje, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        ErrorMensaje errorMensaje = new ErrorMensaje(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMensaje, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = CommandExecutionException.class)
    public ResponseEntity<Object> handleCommandExecutionException(CommandExecutionException ex, WebRequest request){
        ErrorMensaje errorMensaje = new ErrorMensaje(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMensaje, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
