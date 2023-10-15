package com.midas.store.service.injectionDependency;

import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.response.CartResponse;

public interface CartService {
    void addProductToCart(Long cartId, Long productId);

    CartResponse findById(Long id);

    CartEntity buyACar(Long cartId);
}
