package pl.allegrotech.productsshop.infrastructure.currencyconverter;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

public class ExchangeRatesCommunicationError extends RuntimeException {
    private final HttpStatus statusCode;
    public ExchangeRatesCommunicationError(HttpStatus statusCode, Exception cause) {
        super(String.format("Exchange rates communication failed with code: %s", statusCode.value()), cause);
        this.statusCode = statusCode;
    }
}
