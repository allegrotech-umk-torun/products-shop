package pl.allegrotech.productsshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class ProductsshopApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductsshopApplication.class, args);
  }
}
