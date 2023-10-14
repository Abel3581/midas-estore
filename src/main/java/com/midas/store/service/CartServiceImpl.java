package com.midas.store.service;

import com.midas.store.exception.CartNotFoundException;
import com.midas.store.mapper.CartMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.Product;
import com.midas.store.model.response.CartResponse;
import com.midas.store.repository.CartRepository;
import com.midas.store.service.injectionDependency.CartService;
import com.midas.store.service.injectionDependency.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartMapper cartMapper;

    @Transactional
    @Override
    public void addProductToCart(Long cartId, Long productId) {
        Optional<CartEntity> cart = cartRepository.findById(cartId);
        if(cart.isEmpty()){
            log.error(String.format("El carrito con id %s no está registrado", cartId));
            throw new  CartNotFoundException(String.format("El carrito con id %s no está registrado", cartId));
        }
        Product product = productService.findById(productId);
        cart.get().addProduct(product);
        cartRepository.save(cart.get());

    }

    @Override
    public CartResponse findById(Long id) {
        Optional<CartEntity> cart = cartRepository.findById(id);
        if(cart.isEmpty()){
            log.error(String.format("El carrito con id %s no está registrado", id));
            throw new  CartNotFoundException(String.format("El carrito con id %s no está registrado", id));
        }
        CartResponse response = cartMapper.mapToCartResponse(cart.get());
        return response;
    }

}
