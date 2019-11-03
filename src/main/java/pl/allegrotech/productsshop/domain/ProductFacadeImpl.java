package pl.allegrotech.productsshop.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.allegrotech.productsshop.api.ProductNotFoundException;
import pl.allegrotech.productsshop.api.ProductRequestNotValidException;
import pl.allegrotech.productsshop.api.TaskException;
import pl.allegrotech.productsshop.infrastructure.ProductRepository;

@Component
class ProductFacadeImpl implements ProductFacade {

  private static Logger log = LoggerFactory.getLogger(ProductFacadeImpl.class);
  private static final ExecutorService createProductsThreadpool = new ThreadPoolExecutor(
      5,
      40,
      5,
      TimeUnit.SECONDS,
      new LinkedBlockingQueue<>(1),
      new ThreadFactoryBuilder()
          .setNameFormat("createProduct-%d")
          .build(),
      (Runnable runnable, ThreadPoolExecutor executor) -> {
          log.error("Cannot accept more create product tasks, work queue(size={}) was exceeded", executor.getQueue().size());
          throw new TaskException("Cannot accept more create product tasks");
      }
  );

    private static final ExecutorService getProductsThreadpool = new ThreadPoolExecutor(
        1,
        5,
        2,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(1),
        new ThreadFactoryBuilder()
            .setNameFormat("getProduct-%d")
            .build(),
        (Runnable runnable, ThreadPoolExecutor executor) -> {
            log.error("Cannot accept more get product tasks, work queue(size={}) was exceeded", executor.getQueue().size());
            throw new TaskException("Cannot accept more get product tasks");
        }
    );

  private final ProductRepository productRepository;

  ProductFacadeImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponseDto create(ProductRequestDto productRequest) throws ExecutionException, InterruptedException {
      final var createProductTask = createProductsThreadpool.submit(() -> {
          if (!productRequest.isValid()) {
              throw new ProductRequestNotValidException(productRequest);
          }

          String id = UUID.randomUUID().toString();
          LocalDateTime createdAt = LocalDateTime.now();
          Product product = new Product(id, productRequest.getName(), createdAt);

          productRepository.save(product);
          return new ProductResponseDto(product.getId(), product.getName());
      });

      return createProductTask.get();
  }

  @Override
  public ProductResponseDto get(String id) throws ExecutionException, InterruptedException {
      var getProductTask = getProductsThreadpool.submit(() -> productRepository
          .findById(id)
          .map(product -> new ProductResponseDto(product.getId(), product.getName()))
          .orElseThrow(() -> new ProductNotFoundException(id)));
    return getProductTask.get();
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
