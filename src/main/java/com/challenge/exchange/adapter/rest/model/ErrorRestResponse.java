package com.challenge.exchange.adapter.rest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorRestResponse {
    private Integer code;
    private String type;
    private String info;
}