package pl.allegrotech.productsshop.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;

import pl.allegrotech.productsshop.api.ProductNotFoundException;
import pl.allegrotech.productsshop.api.ProductRequestNotValidException;
import pl.allegrotech.productsshop.infrastructure.ProductRepository;

import javax.annotation.Nullable;

@Component
class ProductFacadeImpl implements ProductFacade {

  private final ProductRepository productRepository;
  private final CurrencyConverter currencyConverter;

  ProductFacadeImpl(ProductRepository productRepository,
                    CurrencyConverter currencyConverter) {
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
    Product product = new Product(id, productRequest.getName(), createdAt, new BigDecimal(productRequest.getPrice()));

    productRepository.save(product);

    return new ProductResponseDto(product.getId(), product.getName(), product.getPrice().toString());
  }

  @Override
  public ProductResponseDto get(String id, @Nullable String currency) {
    var product = productRepository.findById(id);

    if (product == null) {
      throw new ProductNotFoundException(id);
    }

    var price = product.getPrice();
    if (currency != null) {
      price = currencyConverter.convertCurrency(price, "PLN", currency);
    }

    return new ProductResponseDto(product.getId(), product.getName(), price.toString());
  }

  @Override
  public ProductResponseDto update(ProductRequestDto productRequest) {
    if (!productRequest.isValid()) {
      throw new ProductRequestNotValidException(productRequest);
    }

    var product = productRepository.findById(productRequest.getId());
    var productToUpdate =
        new Product(product.getId(), productRequest.getName(), product.getCreatedAt(), new BigDecimal(productRequest.getPrice()));
    var updatedProduct = productRepository.save(productToUpdate);

    return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getPrice().toString());
  }

  @Override
  public void delete(String id) {
    productRepository.removeById(id);
  }
}
