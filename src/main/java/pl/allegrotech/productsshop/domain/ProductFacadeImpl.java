package pl.allegrotech.productsshop.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;

import pl.allegrotech.productsshop.api.ProductNotFoundException;
import pl.allegrotech.productsshop.api.ProductRequestNotValidException;
import pl.allegrotech.productsshop.infrastructure.ProductRepository;

@Component
class ProductFacadeImpl implements ProductFacade {

  private final ProductRepository productRepository;

  ProductFacadeImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponseDto create(ProductRequestDto productRequest) {
    if (!productRequest.isValid()) {
      throw new ProductRequestNotValidException(productRequest);
    }

    String id = UUID.randomUUID().toString();
    LocalDateTime createdAt = LocalDateTime.now();
    Product product = new Product(id, productRequest.getName(), createdAt);

    productRepository.save(product);

    return new ProductResponseDto(product.getId(), product.getName());
  }

  @Override
  public ProductResponseDto get(String id) {
    return productRepository
        .findById(id)
        .map(product -> new ProductResponseDto(product.getId(), product.getName()))
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
                    requestedProduct.getCreatedAt()))
        .map(product -> productRepository.save(product))
        .map(
            updatedProduct ->
                new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName()))
        .orElseThrow(() -> new ProductNotFoundException(productRequest.getId()));
  }

  @Override
  public void delete(String id) {
    productRepository.deleteById(id);
  }
}
