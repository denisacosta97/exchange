package com.challenge.exchange.adapter.controller;

import com.challenge.exchange.adapter.controller.exception.InvalidFormatException;
import com.challenge.exchange.adapter.controller.model.CurrenciesResponse;
import com.challenge.exchange.common.UseCase;
import com.challenge.exchange.application.port.model.ExchangeData;
import com.challenge.exchange.adapter.controller.model.ExchangeResponse;
import com.challenge.exchange.domain.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
public class ExchangeController {
    
    private final UseCase<BigDecimal, ExchangeData> exchangeUseCase;
    private final UseCase<List<Currency>, Void> currenciesUseCase;
    
    @GetMapping()
    public ResponseEntity<ExchangeResponse> exchange(@RequestParam BigDecimal value,
                                                     @RequestParam Currency from,
                                                     @RequestParam Currency to) {
        validate(value);
        
        BigDecimal result = exchangeUseCase.perform(ExchangeData.builder()
            .from(from)
            .to(to)
            .value(value)
            .build());
        
        return ResponseEntity.ok(ExchangeResponse.builder()
            .from(from.name())
            .to(to.name())
            .value(value)
            .result(result)
            .build());
    }
    
    @GetMapping("/currencies")
    public ResponseEntity<CurrenciesResponse> getCurrencies() {
        
        List<Currency> result = currenciesUseCase.perform(null);
        
        return ResponseEntity.ok(CurrenciesResponse.builder()
            .values(result.stream()
                .map(Currency::name)
                .collect(Collectors.toList()))
            .build());
    }
    
    void validate(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidFormatException("El valor a convertir debe ser mayor a cero");
        }
    }
}
