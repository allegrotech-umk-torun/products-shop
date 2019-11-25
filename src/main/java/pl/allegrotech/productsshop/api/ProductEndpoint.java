package pl.allegrotech.productsshop.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.web.bind.annotation.*;

import pl.allegrotech.productsshop.domain.ProductFacade;
import pl.allegrotech.productsshop.domain.ProductRequestDto;
import pl.allegrotech.productsshop.domain.ProductResponseDto;

@RestController
@RequestMapping("/products")
class ProductEndpoint {

  private final ProductFacade productFacade;

  ProductEndpoint(ProductFacade productFacade) {
    this.productFacade = productFacade;
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  ProductResponseDto getProduct(@PathVariable String id,
                                @RequestParam(value = "currency", required = false) String currency) {
    return productFacade.get(id, currency);
  }

  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
    return productFacade.create(productRequestDto);
  }

  @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  ProductResponseDto updateProduct(
      @PathVariable String id, @RequestBody ProductRequestDto productRequestDto) {
    if (!id.equals(productRequestDto.getId())) {
      throw new ProductIdIncorrectException(id, productRequestDto.getId());
    }
    return productFacade.update(productRequestDto);
  }

  @DeleteMapping("/{id}")
  void deleteProduct(@PathVariable String id) {
    productFacade.delete(id);
  }
}
