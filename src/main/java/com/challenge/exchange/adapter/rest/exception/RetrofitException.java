package com.challenge.exchange.adapter.rest.exception;

import lombok.Getter;

@Getter
public class RetrofitException extends RuntimeException {
    private final Integer errorCode;
    private final String message;
    
    public RetrofitException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public RetrofitException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }
    
    @Override
    public String toString() {
        if (errorCode == 0)
            return String.format("Exception: %s", getCause().getMessage());
        return String.format("(%s: %s)", errorCode, message);
    }
}
