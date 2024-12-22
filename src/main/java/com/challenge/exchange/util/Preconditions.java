package com.challenge.exchange.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class Preconditions {
    
    public static <T> T nonNullArgument(T argument, String message) {
        if(message == null){
            throw new IllegalArgumentException("Message on Precondition.nonNullArgument cannot be null.");
        }
        if (argument == null) {
            throw new IllegalArgumentException(message);
        }
        return argument;
    }
}
