package com.challenge.exchange.application.usecase;

import com.challenge.exchange.domain.Currency;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GetCurrenciesUseCaseTest {
    
    private final GetCurrenciesUseCase useCase = new GetCurrenciesUseCase();
    
    @Test
    void testPerform() {
        List<Currency> currencies = useCase.perform(null);
        
        assertThat(currencies)
            .isNotNull()
            .hasSize(Currency.values().length);
    }
}