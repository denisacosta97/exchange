package com.challenge.exchange.config;

import com.challenge.exchange.adapter.controller.exception.InvalidFormatException;
import com.challenge.exchange.adapter.rest.exception.AdapterException;
import com.challenge.exchange.adapter.rest.exception.RetrofitException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handle(Throwable ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno, no es posible procesar la solicitud en este momento");
    }
    
    @ExceptionHandler({ConversionFailedException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handle(ConversionFailedException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponse(HttpStatus.BAD_REQUEST, "La moneda seleccionada es inválida. Para obtener valores correctos, consulta en /api/v1/exchange/currencies");
    }
    
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handle(InvalidFormatException ex) {
        log.error(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex);
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException ex) {
        log.error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), ex);
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Existen campos con valores inválidos para ser procesados");
    }
    
    @ExceptionHandler(AdapterException.class)
    public ResponseEntity<ErrorResponse> handle(AdapterException ex) {
        log.error(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), ex);
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }
    
    @ExceptionHandler(RetrofitException.class)
    public ResponseEntity<ErrorResponse> handle(RetrofitException ex) {
        log.error(HttpStatus.CONFLICT.getReasonPhrase(), ex);
        return buildResponse(HttpStatus.CONFLICT, "Error al comunicarse con la API Exchange Rate. "+ex.toString());
    }
    
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus httpStatus, String message) {
        final ErrorResponse response = ErrorResponse.builder()
            .message(message)
            .status(httpStatus.value())
            .build();
        return new ResponseEntity<>(response, httpStatus);
    }
    
    @Builder
    private static class ErrorResponse {
        @JsonProperty
        String message;
        @JsonProperty
        Integer status;
    }
}

