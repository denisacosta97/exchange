package com.challenge.exchange.application.usecase;

import com.challenge.exchange.common.UseCase;
import com.challenge.exchange.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GetCurrenciesUseCase implements UseCase<List<Currency>, Void> {
    @Override
    public List<Currency> perform(Void var1) {
        return Arrays.asList(Currency.values());
    }
}
