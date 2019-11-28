package pl.allegrotech.productsshop.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
class RestClientConfig {

    @Bean
    public RestTemplate exchangeRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${client.exchange.connection-timeout:100}") int connectionTimeout,
            @Value("${client.exchange.read-timeout:1000}") int readTimeout
    ) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

}
