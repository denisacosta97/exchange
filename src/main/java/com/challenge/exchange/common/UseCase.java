package com.challenge.exchange.common;

@FunctionalInterface
public interface UseCase<T, M> {
    T perform(M var1);
}
