package com.etitc.smart_network_managment.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient()
    {
        return WebClient.builder()
                .baseUrl("https://api-colombia.com/api/v1")
                .build();
    }
}
