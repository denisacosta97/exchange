package com.challenge.exchange.adapter.rest;

import com.challenge.exchange.adapter.rest.model.ExchangeRestResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.math.BigDecimal;

public interface ExchangeRateApi {
    @GET("convert")
    Call<ExchangeRestResponse> convert(
        @Query("from") String from,
        @Query("to") String to,
        @Query("amount") BigDecimal amount,
        @Query("access_key") String accessKey
    );
}