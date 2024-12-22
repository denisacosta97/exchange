package com.challenge.exchange.adapter.controller.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeResponse {
    private String from;
    private String to;
    private BigDecimal value;
    private BigDecimal result;
}
