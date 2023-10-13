package com.midas.store.mapper;

import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapToProductRequest(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .count(request.getCount())
                .price(request.getPrice())
                .stock(request.getStock())
                .state(request.isState())
                .build();
    }
}
