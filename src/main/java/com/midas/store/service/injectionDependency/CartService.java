package com.midas.store.service.injectionDependency;

public interface CartService {
    void addProductToCart(Long cartId, Long productId);
}
