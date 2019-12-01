package pl.allegrotech.productsshop.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.Nullable;
import org.springframework.stereotype.Component;

import pl.allegrotech.productsshop.api.ProductNotFoundException;
import pl.allegrotech.productsshop.api.ProductRequestNotValidException;
import pl.allegrotech.productsshop.infrastructure.ProductRepository;

@Component
class ProductFacadeImpl implements ProductFacade {

  private final ProductRepository productRepository;
  private final CurrencyConverter currencyConverter;

  ProductFacadeImpl(ProductRepository productRepository, CurrencyConverter currencyConverter) {
    this.productRepository = productRepository;
    this.currencyConverter = currencyConverter;
  }

  @Override
  public ProductResponseDto create(ProductRequestDto productRequest) {
    if (!productRequest.isValid()) {
      throw new ProductRequestNotValidException(productRequest);
    }

    String id = UUID.randomUUID().toString();
    LocalDateTime createdAt = LocalDateTime.now();
    Product product =
        new Product(
            id, productRequest.getName(), createdAt, new BigDecimal(productRequest.getPrice()));

    productRepository.save(product);

    return new ProductResponseDto(
        product.getId(), product.getName(), product.getPrice().toString());
  }

  @Override
  public ProductResponseDto get(String id, @Nullable String currency) {
    return productRepository
        .findById(id)
        .map(
            product ->
                new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    convertPrice(product.getPrice(), currency).toString()))
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Override
  public ProductResponseDto update(ProductRequestDto productRequest) {
    if (!productRequest.isValid()) {
      throw new ProductRequestNotValidException(productRequest);
    }

    return productRepository
        .findById(productRequest.getId())
        .map(
            requestedProduct ->
                new Product(
                    requestedProduct.getId(),
                    productRequest.getName(),
                    requestedProduct.getCreatedAt(),
                    new BigDecimal(productRequest.getPrice())))
        .map(product -> productRepository.save(product))
        .map(
            updatedProduct ->
                new ProductResponseDto(
                    updatedProduct.getId(),
                    updatedProduct.getName(),
                    updatedProduct.getPrice().toString()))
        .orElseThrow(() -> new ProductNotFoundException(productRequest.getId()));
  }

  @Override
  public void delete(String id) {
    productRepository.deleteById(id);
  }

  private BigDecimal convertPrice(BigDecimal price, @Nullable String currency) {
    if (currency != null) {
      return currencyConverter.convertCurrency(price, "PLN", currency);
    }
    return price;
  }
}
