package com.midas.store.mapper;

import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.ProductUpdateResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public ProductEntity mapToProductRequest(ProductRequest request) {
        return ProductEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .count(request.getCount())
                .price(request.getPrice())
                .stock(request.getStock())
                .state(request.isState())
                .build();
    }

    public ProductResponse mapToProductResponse(ProductEntity productEntity) {
        return ProductResponse.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .description(productEntity.getDescription())
                .count(productEntity.getCount())
                .state(productEntity.isState())
                .stock(productEntity.getStock())
                .price(productEntity.getPrice())
                .build();
    }

    public List<ProductResponse> mapToProductResponseList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(product -> mapToProductResponse(product))
                .collect(Collectors.toList());
    }


    public ProductUpdateResponse mapToProductUpdate(ProductEntity productEntity) {
        return ProductUpdateResponse.builder()
                .name(productEntity.getName())
                .stock(productEntity.getStock())
                .count(productEntity.getCount())
                .price(productEntity.getPrice())
                .description(productEntity.getDescription())
                .message("Producto actualizado")
                .state(productEntity.isState())
                .build();
    }
}
