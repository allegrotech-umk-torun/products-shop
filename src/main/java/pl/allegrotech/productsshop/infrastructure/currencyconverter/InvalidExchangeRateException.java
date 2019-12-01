package pl.allegrotech.productsshop.infrastructure.currencyconverter;

class InvalidExchangeRateException extends RuntimeException {
  InvalidExchangeRateException(String currency) {
    super(String.format("Invalid exchange rate for %s", currency));
  }
}
