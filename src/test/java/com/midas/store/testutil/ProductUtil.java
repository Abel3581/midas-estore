package com.midas.store.testutil;

import com.midas.store.model.request.ProductRequest;

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
}
