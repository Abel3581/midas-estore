package com.midas.store.service;

import com.midas.store.exception.CartNotFoundException;
import com.midas.store.model.entity.Cart;
import com.midas.store.model.entity.Product;
import com.midas.store.repository.CartRepository;
import com.midas.store.service.injectionDependency.CartService;
import com.midas.store.service.injectionDependency.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    @Override
    public void addProductToCart(Long cartId, Long productId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if(cart.isEmpty()){
            throw new  CartNotFoundException("El carrito no esta creado con id: " + cartId);
        }
        Product product = productService.findById(productId);
        cart.get().addProduct(product);
        cartRepository.save(cart.get());

    }
}
