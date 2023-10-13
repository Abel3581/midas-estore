package com.midas.store.service.injectionDependency;

import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.response.ProductResponse;

public interface ProductService {
    void create(ProductRequest request);

    ProductResponse getById(Long id);
}
