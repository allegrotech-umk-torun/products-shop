package pl.allegrotech.productsshop.infrastructure.currency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class ExchangeRestClient {
    private final RestTemplate exchangeRestTemplate;
    private final String baseUrl;

    public ExchangeRestClient(RestTemplate exchangeRestTemplate,
                              @Value("${client.exchange.url}") String baseUrl) {
        this.exchangeRestTemplate = exchangeRestTemplate;
        this.baseUrl = baseUrl;
    }

    public ExchangeRatesDto getRates(String baseCurrency) {
        var url = String.format("%s/latest?base=%s", baseUrl, baseCurrency);
        var response = exchangeRestTemplate.getForEntity(url, ExchangeRatesDto.class);
        // FIXME: obsługa błędów
        return response.getBody();
    }
}
