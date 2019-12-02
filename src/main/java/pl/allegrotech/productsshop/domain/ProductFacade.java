package pl.allegrotech.productsshop.domain;

import javax.annotation.Nullable;

public interface ProductFacade {

    ProductResponseDto get(String id, @Nullable String currency);

    ProductResponseDto create(ProductRequestDto productRequest);

    ProductResponseDto update(ProductRequestDto productRequest);

    void delete(String id);
}
