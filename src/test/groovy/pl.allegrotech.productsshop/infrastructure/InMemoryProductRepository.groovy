package pl.allegrotech.productsshop.infrastructure

import pl.allegrotech.productsshop.domain.Product

class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>()

    @Override
    Product save(Product product) {
        products.put(product.getId(), product)
        return product
    }

    @Override
    Optional<Product> findById(String id) {
        return Optional
                .ofNullable(products.get(id))
    }

    @Override
    void deleteById(String id) {
        products.remove(id)
    }
}
