package pl.allegrotech.productsshop.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequestDto {

    private final String id;
    private final String name;
    private final String price;

    @JsonCreator
    public ProductRequestDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("price") String price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @JsonIgnore
    public boolean isValid() {
        return !Strings.isNullOrEmpty(name);
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
  public String toString() {
    return "ProductRequestDto{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", price='" + price + '\'' +
            '}';
  }
}
