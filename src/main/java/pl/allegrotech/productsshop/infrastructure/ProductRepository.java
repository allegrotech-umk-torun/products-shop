package pl.allegrotech.productsshop.infrastructure;

import java.util.Optional;
import org.springframework.data.repository.Repository;

import pl.allegrotech.productsshop.domain.Product;

public interface ProductRepository extends Repository<Product, String> {

  Product save(Product product);

  Optional<Product> findById(String id);

  void deleteById(String id);
}
