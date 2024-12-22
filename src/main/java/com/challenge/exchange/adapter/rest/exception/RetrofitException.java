package com.challenge.exchange.adapter.rest.exception;

import lombok.Getter;

@Getter
public class RetrofitException extends RuntimeException {
    private final String message;
    
    public RetrofitException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
