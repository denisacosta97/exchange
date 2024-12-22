package com.challenge.exchange.adapter.rest;

import com.challenge.exchange.adapter.rest.exception.AdapterException;
import com.challenge.exchange.adapter.rest.model.ExchangeRestResponse;
import com.challenge.exchange.application.port.model.ExchangeData;
import com.challenge.exchange.common.Repository;
import com.challenge.exchange.adapter.rest.util.RetrofitClient;
import com.challenge.exchange.util.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.math.BigDecimal;

@Slf4j
@Component
public class ExchangeRateRestAdapter implements Repository<BigDecimal, ExchangeData> {
    
    private final ExchangeRateApi exchangeService;
    @Value("${auth.token}")
    private String accessToken;
    
    public ExchangeRateRestAdapter(Retrofit retrofit) {
        this.exchangeService = RetrofitClient.create(retrofit, ExchangeRateApi.class);
    }
    
    @Override
    public BigDecimal execute(ExchangeData param) {
        validateParam(param);
        ExchangeRestResponse response = RetrofitClient.perform(exchangeService.convert(param.getFrom().name(), param.getTo().name(),
            param.getValue(), accessToken));
        log.info("Respuesta de exchange: {}", response);
        if (response == null || response.getError() != null)
            //Log de error
            throw new AdapterException("No fue posible realizar la conversión");
        return response.getResult();
    }
    
    private void validateParam(ExchangeData param) {
        Preconditions.nonNullArgument(param, "Param cant be null");
        Preconditions.nonNullArgument(param.getFrom(), "From cant be null");
        Preconditions.nonNullArgument(param.getTo(), "To cant be null");
        Preconditions.nonNullArgument(param.getValue(), "Value be null");
    }
}
