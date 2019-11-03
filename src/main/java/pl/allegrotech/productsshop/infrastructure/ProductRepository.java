package pl.allegrotech.productsshop.infrastructure;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;
import pl.allegrotech.productsshop.domain.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

  Product save(Product product);

  Optional<Product> findById(String id);

  void deleteById(String id);
}
