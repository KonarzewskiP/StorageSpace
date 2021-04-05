package com.storage.config;

import com.storage.exceptions.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration
public class RestTemplateConfig {

    @Value("${connect.timeout}")
    private int connectionTimeout;
    @Value("${read.timeout}")
    private int readTimeout;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();

        return restTemplateBuilder
                .errorHandler(new RestTemplateErrorHandler())
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

}
