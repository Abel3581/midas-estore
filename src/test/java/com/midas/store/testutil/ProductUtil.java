package com.midas.store.testutil;

import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.request.ProductUpdateRequest;


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

    public static List<ProductEntity> createProductListTest() {
        return List.of(
                new ProductEntity("Java", 19.99, 100, true, 10, "17"),
                new ProductEntity("Spring Boot", 20.99, 100, true, 19, "3.14")
        );
    }

    public static ProductEntity createProductEntityTest(){
        return ProductEntity.builder()
                .name("Python")
                .price(9000)
                .count(39)
                .state(true)
                .stock(18)
                .description("12")
                .build();
    }
    public static ProductUpdateRequest createProductUpdateTest(){
        return ProductUpdateRequest.builder()
                .name("JavaScript")
                .price(7000)
                .stock(60)
                .state(true)
                .count(30)
                .description("29")
                .build();
    }


    public static ProductUpdateRequest createProductUpdateFailureTest() {
        return ProductUpdateRequest.builder()
                .name("Angular")
                .price(7000)
                .stock(60)
                .state(true)
                .count(30)
                .description("29")
                .build();

    }

    public static ProductEntity createProducUpdatetEntityTest() {
        return ProductEntity.builder()
                .name("PHP")
                .price(9000)
                .count(39)
                .state(true)
                .stock(18)
                .description("12")
                .build();

    }
}
