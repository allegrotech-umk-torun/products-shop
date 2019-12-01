package pl.allegrotech.productsshop.infrastructure.currencyconverter;

import org.springframework.stereotype.Component;
import pl.allegrotech.productsshop.domain.CurrencyConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
class RestCurrencyConverter implements CurrencyConverter {
    private final ExchangeRestApiClient exchangeClient;

    RestCurrencyConverter(ExchangeRestApiClient exchangeClient) {
        this.exchangeClient = exchangeClient;
    }

    @Override
    public BigDecimal convertCurrency(BigDecimal value, String fromCurrency, String toCurrency) {
        ExchangeRatesDto dto = exchangeClient.getExchangeRates(fromCurrency);
        BigDecimal targetRate = dto.getRates().get(toCurrency);
        if (targetRate == null) {
            throw new InvalidExchangeRateException(toCurrency);
        }
        return value.multiply(targetRate).setScale(2, RoundingMode.DOWN);
    }
}
