package pl.allegrotech.productsshop.domain;

import java.math.BigDecimal;

public interface CurrencyConverter {
    BigDecimal convertCurrency(BigDecimal value, String fromCurrency, String toCurrency);
}
