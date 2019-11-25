package pl.allegrotech.productsshop.infrastructure.currency;

import org.springframework.stereotype.Component;
import pl.allegrotech.productsshop.domain.CurrencyConverter;

import java.math.BigDecimal;

@Component
class FakeCurrencyConverter implements CurrencyConverter {
    private final ExchangeRestClient exchangeRestClient;

    FakeCurrencyConverter(ExchangeRestClient exchangeRestClient) {
        this.exchangeRestClient = exchangeRestClient;
    }

    @Override
    public BigDecimal convert(BigDecimal value, String fromCurrency, String toCurrency) {
        var response = exchangeRestClient.getRates(fromCurrency);
        var rate = response.getRates().get(toCurrency);
        return value.multiply(rate);
    }
}
