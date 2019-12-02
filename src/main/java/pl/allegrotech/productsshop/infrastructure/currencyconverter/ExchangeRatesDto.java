package pl.allegrotech.productsshop.infrastructure.currencyconverter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
class ExchangeRatesDto {
    private final String base;
    private final String date;
    private final Map<String, BigDecimal> rates;

    @JsonCreator
    ExchangeRatesDto(
            @JsonProperty("base") String base,
            @JsonProperty("date") String date,
            @JsonProperty("rates") Map<String, BigDecimal> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }

    Map<String, BigDecimal> getRates() {
        return rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRatesDto that = (ExchangeRatesDto) o;
        return Objects.equals(base, that.base)
                && Objects.equals(date, that.date)
                && Objects.equals(rates, that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, date, rates);
    }

    @Override
    public String toString() {
        return "ExchangeRatesDto{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
