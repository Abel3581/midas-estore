package com.midas.store.mapper;

import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product mapToProductRequest(ProductRequest request) {
        return Product.builder()
                .build();
    }
}
