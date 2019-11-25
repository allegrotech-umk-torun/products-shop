package pl.allegrotech.productsshop.infrastructure.currencyconverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
class ExchangeRestApiClient {
    private final RestTemplate http;
    private final String baseUrl;

    ExchangeRestApiClient(RestTemplate exchangeRestTemplate,
                          @Value("${client.exchange.url}") String baseUrl) {
        this.http = exchangeRestTemplate;
        this.baseUrl = baseUrl;
    }

    @Retryable
    ExchangeRatesDto getExchangeRates(String base) {
        try {
            String url = String.format("%s/latest?base=%s", baseUrl, base);
            ResponseEntity<ExchangeRatesDto> response = http.getForEntity(url, ExchangeRatesDto.class);
            return response.getBody();
        }
        catch(HttpClientErrorException | HttpServerErrorException ex) {
            throw new ExchangeRatesCommunicationError(ex.getStatusCode(), ex);
        }
    }
}
