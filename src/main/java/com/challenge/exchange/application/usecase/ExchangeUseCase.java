package com.challenge.exchange.application.usecase;

import com.challenge.exchange.common.UseCase;
import com.challenge.exchange.application.port.model.ExchangeData;
import com.challenge.exchange.common.Repository;
import com.challenge.exchange.util.Preconditions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ExchangeUseCase implements UseCase<BigDecimal, ExchangeData> {
    
    private final Repository<BigDecimal, ExchangeData> exchangeRepository;
    
    @Override
    public BigDecimal perform(ExchangeData data) {
        Preconditions.nonNullArgument(data, "Param cant be null");
        Preconditions.nonNullArgument(data.getFrom(), "From cant be null");
        Preconditions.nonNullArgument(data.getTo(), "To cant be null");
        Preconditions.nonNullArgument(data.getValue(), "Value be null");
        return exchangeRepository.execute(data);
    }
}
