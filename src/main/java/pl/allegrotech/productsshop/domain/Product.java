package pl.allegrotech.productsshop.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;

public final class Product {

  @Id private final String id;
  private final String name;
  private final LocalDateTime createdAt;
  private final BigDecimal price;

  Product(String id, String name, LocalDateTime createdAt, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.createdAt = createdAt;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id)
        && Objects.equals(name, product.name)
        && Objects.equals(createdAt, product.createdAt)
        && Objects.equals(price, product.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, createdAt, price);
  }

  @Override
  public String toString() {
    return "Product{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", createdAt="
        + createdAt
        + ", price="
        + price
        + '}';
  }
}
