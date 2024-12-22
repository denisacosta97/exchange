package com.challenge.exchange.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreconditionsTest {
    
    @Test
    void nonNullArgumentWithMessage() {
        String result = Preconditions.nonNullArgument("test", "message");
        assertEquals("test", result);
    }

    @Test
    void nonNullArgumentWithMessageNull() {
        assertThrows(IllegalArgumentException.class,
            () -> Preconditions.nonNullArgument("test", null),
            "Message on Precondition.nonNullArgument cannot be null."
        );
    }

    @Test
    void nonNullArgumentWithMessageAndNullArgument() {
        assertThrows(IllegalArgumentException.class,
            () -> Preconditions.nonNullArgument(null, "test message"),
            "test message");
    }
}