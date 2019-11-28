package pl.allegrotech.productsshop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponseDto {

  private final String id;
  private final String name;
  private final String price;

  @JsonCreator
  public ProductResponseDto(@JsonProperty("id") String id, @JsonProperty("name") String name, String price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductResponseDto that = (ProductResponseDto) o;
    return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price);
  }

  @Override
  public String toString() {
    return "ProductResponseDto{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", price='" + price + '\'' +
            '}';
  }
}
