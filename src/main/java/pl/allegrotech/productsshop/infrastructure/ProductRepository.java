package pl.allegrotech.productsshop.infrastructure;

import org.springframework.data.repository.Repository;
import pl.allegrotech.productsshop.domain.Product;

import java.util.Optional;

public interface ProductRepository extends Repository<Product, String> {

    Product save(Product product);

    Optional<Product> findById(String id);

    void deleteById(String id);
}
