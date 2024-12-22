package com.challenge.exchange.adapter.rest.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeRestResponse {
    private Boolean success;
    private ErrorRestResponse error;
    private BigDecimal result;
}