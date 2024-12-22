package com.challenge.exchange.application.usecase;

import com.challenge.exchange.application.port.model.ExchangeData;
import com.challenge.exchange.common.Repository;
import com.challenge.exchange.domain.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

class ExchangeUseCaseTest {
    
    private final Repository<BigDecimal, ExchangeData> exchangeRepository = mock(Repository.class);
    private final ExchangeUseCase exchangeUseCase = new ExchangeUseCase(exchangeRepository);
    
    
    @Test
    void performOk() {
        ExchangeData data = ExchangeData.builder()
            .from(Currency.USD)
            .to(Currency.ARS)
            .value(BigDecimal.ONE)
            .build();
        
        when(exchangeRepository.execute(data)).thenReturn(BigDecimal.valueOf(85));
        
        BigDecimal result = exchangeUseCase.perform(data);
        
        assertThat(result)
            .isNotNull()
            .isEqualTo(BigDecimal.valueOf(85));
        
        verify(exchangeRepository, times(1)).execute(data);
    }
    
    @Test
    void performWithInvalidFrom() {
        ExchangeData data = ExchangeData.builder()
            .from(null)
            .to(Currency.ARS)
            .value(BigDecimal.ONE)
            .build();
        
        assertThrows(IllegalArgumentException.class, () -> exchangeUseCase.perform(data), "From cant be null");
    }
    
    @Test
    void performWithInvalidTo() {
        ExchangeData data = ExchangeData.builder()
            .from(Currency.USD)
            .to(null)
            .value(BigDecimal.ONE)
            .build();
        
        assertThrows(IllegalArgumentException.class, () -> exchangeUseCase.perform(data), "To cant be null");
    }
    
    @Test
    void performWithInvalidValue() {
        ExchangeData data = ExchangeData.builder()
            .from(Currency.USD)
            .to(Currency.ARS)
            .value(null)
            .build();
        
        assertThrows(IllegalArgumentException.class, () -> exchangeUseCase.perform(data), "Value cant be null");
    }
    
    @Test
    void performWithInvalidData() {
               assertThrows(IllegalArgumentException.class, () -> exchangeUseCase.perform(null), "Param cant be null");
    }
}
