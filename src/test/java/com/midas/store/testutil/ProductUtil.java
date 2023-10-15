package com.midas.store.testutil;

import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductUtil {

    public static ProductRequest createProductTest(){
        return ProductRequest.builder()
                .name("Pepsi")
                .description("Bebida")
                .price(2699)
                .count(10)
                .stock(100)
                .state(true)
                .build();
    }

        public static List<Product> createProductListTest() {
            return List.of(
                    new Product("Java", 19.99, 100, true, 10, "17"),
                    new Product("Spring Boot", 20.99, 100, true, 19, "3.14")
            );
        }
        /*List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setName("Java");
        product.setPrice(19.99);
        product.setStock(100);
        product.setState(true);
        product.setCount(10);
        product.setDescription("17");
        Product product1 = new Product();
        product1.setName("Spring boot");
        product1.setPrice(10000);
        product1.setStock(100);
        product1.setState(true);
        product1.setCount(19);
        product1.setDescription("3.14");
        productList.addAll(productList);
        return productList;*/

}
