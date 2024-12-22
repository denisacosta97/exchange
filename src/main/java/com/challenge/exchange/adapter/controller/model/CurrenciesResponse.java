package com.challenge.exchange.adapter.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CurrenciesResponse {
    private List<String> values;
}
