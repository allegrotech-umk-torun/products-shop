package pl.allegrotech.productsshop.domain;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal convert(BigDecimal value, String fromCurrency, String toCurrency);
}
