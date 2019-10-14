package pl.allegrotech.productsshop.infrastructure;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import pl.allegrotech.productsshop.domain.Product;

public interface ProductRepository extends CrudRepository<Product, String> {

  Product save(Product product);

  Optional<Product> findById(String id);

  void deleteById(String id);
}
