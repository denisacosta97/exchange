package com.challenge.exchange.adapter.controller;


import com.challenge.exchange.adapter.controller.model.CurrenciesResponse;
import com.challenge.exchange.adapter.controller.model.ExchangeResponse;
import com.challenge.exchange.application.port.model.ExchangeData;
import com.challenge.exchange.common.UseCase;
import com.challenge.exchange.domain.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeController.class)
class ExchangeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UseCase<BigDecimal, ExchangeData> exchangeUseCase;
    @MockBean
    private UseCase<List<Currency>, Void> currenciesUseCase;
    private static final String PATH = "/api/v1/exchange";
    private static final String VALUE_PARAM = "value";
    private static final String FROM_PARAM = "from";
    private static final String TO_PARAM = "to";
    private static final BigDecimal VALUE = BigDecimal.ONE;
    private static final Currency FROM = Currency.USD;
    private static final Currency TO = Currency.EUR;
    private static final BigDecimal RESULT = new BigDecimal("85.00");
    
    @Test
    void exchangeOk() throws Exception {
        ExchangeResponse response = ExchangeResponse.builder()
            .from(FROM.name())
            .to(TO.name())
            .result(RESULT)
            .value(VALUE)
            .build();
        
        when(exchangeUseCase.perform(any(ExchangeData.class))).thenReturn(RESULT);
        
        mockMvc.perform(get(PATH)
                .param(VALUE_PARAM, VALUE.toString())
                .param(FROM_PARAM, FROM.name())
                .param(TO_PARAM, TO.name()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
        verify(exchangeUseCase, times(1)).perform(any());
    }
    
    @Test
    void exchangeWithInvalidValue() throws Exception {
        when(exchangeUseCase.perform(any(ExchangeData.class))).thenReturn(RESULT);
        
        mockMvc.perform(get(PATH)
                .param(VALUE_PARAM, BigDecimal.ZERO.toString())
                .param(FROM_PARAM, FROM.name())
                .param(TO_PARAM, TO.name()))
            .andDo(print())
            .andExpect(status().isBadRequest());
        verify(exchangeUseCase, times(0)).perform(any());
    }
    
    @Test
    void exchangeWithInvalidCurrency() throws Exception {
        when(exchangeUseCase.perform(any(ExchangeData.class))).thenReturn(RESULT);
        
        mockMvc.perform(get(PATH)
                .param(VALUE_PARAM, BigDecimal.ZERO.toString())
                .param(FROM_PARAM, "")
                .param(TO_PARAM, TO.name()))
            .andDo(print())
            .andExpect(status().isBadRequest());
        verify(exchangeUseCase, times(0)).perform(any());
    }
    
    @Test
    void getCurrencies() throws Exception {
        List<Currency> currencies = Arrays.asList(Currency.USD, Currency.EUR);
        when(currenciesUseCase.perform(null)).thenReturn(currencies);
        
        CurrenciesResponse response = CurrenciesResponse.builder()
            .values(currencies.stream()
                .map(Currency::name)
                .collect(Collectors.toList()))
            .build();
        
        mockMvc.perform(get(PATH+ "/currencies"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }
}
