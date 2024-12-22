package com.challenge.exchange.adapter.rest;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.challenge.exchange.adapter.rest.exception.AdapterException;
import com.challenge.exchange.adapter.rest.model.ErrorRestResponse;
import com.challenge.exchange.adapter.rest.model.ExchangeRestResponse;
import com.challenge.exchange.adapter.rest.util.RetrofitClient;
import com.challenge.exchange.application.port.model.ExchangeData;
import com.challenge.exchange.domain.Currency;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

class ExchangeRateRestAdapterTest {
    
    public static final ExchangeData PARAM_MOCK = ExchangeData.builder()
        .from(Currency.USD)
        .to(Currency.ARS)
        .value(BigDecimal.ONE)
        .build();
    private final ExchangeRateApi exchangeService = mock(ExchangeRateApi.class);
    private final Call<ExchangeRestResponse> mockCall = mock(Call.class);
    private final Retrofit retrofitMock = new Retrofit.Builder()
        .baseUrl("http://www.test.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    private ExchangeRateRestAdapter exchangeRateRestAdapter;
    
    @Test
    void executeOk() {
        try (MockedStatic<RetrofitClient> mocked = mockStatic(RetrofitClient.class)) {
            mocked.when(() -> RetrofitClient.create(retrofitMock, ExchangeRateApi.class)).thenReturn(exchangeService);
            exchangeRateRestAdapter = new ExchangeRateRestAdapter(retrofitMock);
            
            ExchangeRestResponse response = ExchangeRestResponse.builder().result(BigDecimal.ONE).build();
            when(exchangeService.convert(anyString(), anyString(), any(BigDecimal.class), any())).thenReturn(mockCall);
            mocked.when(() -> RetrofitClient.perform(mockCall)).thenReturn(response);
            
            BigDecimal result = exchangeRateRestAdapter.execute(PARAM_MOCK);
            
            assertThat(result).isNotNull().isEqualTo(BigDecimal.ONE);
            verify(exchangeService, times(1)).convert(anyString(), anyString(), any(BigDecimal.class), any());
        }
    }
    
    @Test
    void executeWithErrorResponse() {
        try (MockedStatic<RetrofitClient> mocked = mockStatic(RetrofitClient.class)) {
            mocked.when(() -> RetrofitClient.create(retrofitMock, ExchangeRateApi.class)).thenReturn(exchangeService);
            exchangeRateRestAdapter = new ExchangeRateRestAdapter(retrofitMock);
            
            ExchangeRestResponse response = ExchangeRestResponse.builder().error(ErrorRestResponse.builder().build()).build();
            when(exchangeService.convert(anyString(), anyString(), any(BigDecimal.class), any())).thenReturn(mockCall);
            mocked.when(() -> RetrofitClient.perform(mockCall)).thenReturn(response);
            
            Throwable throwable = catchThrowable( () -> exchangeRateRestAdapter.execute(PARAM_MOCK));
            
            assertThat(throwable).isInstanceOf(AdapterException.class);
            verify(exchangeService, times(1)).convert(anyString(), anyString(), any(BigDecimal.class), any());
        }
    }
    
    @Test
    void executeWithServiceError() {
        try (MockedStatic<RetrofitClient> mocked = mockStatic(RetrofitClient.class)) {
            mocked.when(() -> RetrofitClient.create(retrofitMock, ExchangeRateApi.class)).thenReturn(exchangeService);
            exchangeRateRestAdapter = new ExchangeRateRestAdapter(retrofitMock);
            
            when(exchangeService.convert(anyString(), anyString(), any(BigDecimal.class), any())).thenReturn(mockCall);
            mocked.when(() -> RetrofitClient.perform(mockCall)).thenReturn(null);
            
            Throwable throwable = catchThrowable( () -> exchangeRateRestAdapter.execute(PARAM_MOCK));
            
            assertThat(throwable).isInstanceOf(AdapterException.class);
            verify(exchangeService, times(1)).convert(anyString(), anyString(), any(BigDecimal.class), any());
        }
    }
    
    @Test
    void executeWithToParamNUll(){
        executeWithParamError(ExchangeData.builder()
            .from(Currency.USD)
            .to(null)
            .value(BigDecimal.ONE)
            .build());
    }
    
    @Test
    void executeWithFromParamNUll(){
        executeWithParamError(ExchangeData.builder()
            .from(null)
            .to(Currency.ARS)
            .value(BigDecimal.ONE)
            .build());
    }
    
    @Test
    void executeWithValueParamNUll(){
        executeWithParamError(ExchangeData.builder()
            .from(Currency.USD)
            .to(Currency.ARS)
            .value(null)
            .build());
    }
    
    @Test
    void executeWithParamNUll(){
        executeWithParamError(null);
    }
    
    void executeWithParamError(ExchangeData data) {
        try (MockedStatic<RetrofitClient> mocked = mockStatic(RetrofitClient.class)) {
            mocked.when(() -> RetrofitClient.create(retrofitMock, ExchangeRateApi.class)).thenReturn(exchangeService);
            exchangeRateRestAdapter = new ExchangeRateRestAdapter(retrofitMock);
            
            when(exchangeService.convert(anyString(), anyString(), any(BigDecimal.class), any())).thenReturn(mockCall);
            mocked.when(() -> RetrofitClient.perform(mockCall)).thenReturn(null);
            
            Throwable throwable = catchThrowable( () -> exchangeRateRestAdapter.execute(data));
            
            assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        }
    }
}
