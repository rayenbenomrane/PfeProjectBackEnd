package com.example.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@Configuration
@Data
public class KonnectConfig {

    @Value("${konnect.api.url}")
    private String apiUrl;

    @Value("${konnect.api.key}")
    private String apiKey;

    @Value("${konnect.wallet.id}")
    private String walletId;
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
