package com.challenge.exchange.adapter.rest.util;

import com.challenge.exchange.adapter.rest.exception.RetrofitException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RetrofitClient {
    
    public static <T> T create(Retrofit retrofit, Class<T> service) {
        return retrofit.create(service);
    }
    
    public static <T> T perform(Call<T> request) {
        try {
            Response<T> response = request.execute();
            return response.body();
        } catch (Exception e) {
            log.error("Error inesperado", e);
            throw new RetrofitException("Retrofit service exception", e);
        }
    }
    
}
