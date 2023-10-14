package com.midas.store.service.injectionDependency;

import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.request.ProductUpdateRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.ProductUpdateResponse;

import java.util.List;

public interface ProductService {
    void create(ProductRequest request);

    ProductResponse getById(Long id);

    List<ProductResponse> getAllProduct();


    ProductUpdateResponse update(ProductUpdateRequest request, Long id);

    void deleted(Long id);

    Product findById(Long productId);
}
