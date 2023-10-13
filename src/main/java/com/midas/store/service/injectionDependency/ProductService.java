package com.midas.store.service.injectionDependency;

import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.response.ProductResponse;

import java.util.List;

public interface ProductService {
    void create(ProductRequest request);

    ProductResponse getById(Long id);

    List<ProductResponse> getAllProduct();
}
