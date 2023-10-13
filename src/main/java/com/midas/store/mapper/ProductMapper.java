package com.midas.store.mapper;

import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .description(product.getDescription())
                .count(product.getCount())
                .state(product.isState())
                .stock(product.getStock())
                .build();
    }

    public List<ProductResponse> mapToProductResponseList(List<Product> products) {
        return products.stream()
                .map(product -> mapToProductResponse(product))
                .collect(Collectors.toList());
    }
}
