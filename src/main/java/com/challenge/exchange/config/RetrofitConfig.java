package com.challenge.exchange.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {
    
    @Bean
    public Retrofit build(@Value("${rest.url}") String baseUrl) {
        final Gson gson = new GsonBuilder()
            .setLenient() // Permite ignorar campos desconocidas
            .create();
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    }
}
