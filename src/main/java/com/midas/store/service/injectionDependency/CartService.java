package com.midas.store.service.injectionDependency;

import com.midas.store.model.response.CartResponse;

public interface CartService {
    void addProductToCart(Long cartId, Long productId);

    CartResponse findById(Long id);
}
