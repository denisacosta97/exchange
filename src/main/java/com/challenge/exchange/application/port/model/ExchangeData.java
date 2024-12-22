package com.challenge.exchange.application.port.model;

import com.challenge.exchange.domain.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeData {
    private Currency from;
    private Currency to;
    private BigDecimal value;
}
