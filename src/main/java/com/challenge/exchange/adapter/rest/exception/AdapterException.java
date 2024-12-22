package com.challenge.exchange.adapter.rest.exception;

import lombok.Getter;

@Getter
public class AdapterException extends RuntimeException {
    private final String message;
    
    public AdapterException(String message) {
        super(message);
        this.message = message;
    }
}
