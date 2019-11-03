package pl.allegrotech.productsshop.domain;

import java.util.concurrent.ExecutionException;

public interface ProductFacade {

  ProductResponseDto get(String id) throws ExecutionException, InterruptedException;

  ProductResponseDto create(ProductRequestDto productRequest) throws ExecutionException, InterruptedException;

  ProductResponseDto update(ProductRequestDto productRequest);

  void delete(String id);
}
