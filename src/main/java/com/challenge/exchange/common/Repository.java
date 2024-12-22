package com.challenge.exchange.common;

public interface Repository<T, I> {
    T execute(I model);
}
