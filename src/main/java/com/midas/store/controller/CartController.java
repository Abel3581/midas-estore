package com.midas.store.controller;

import com.midas.store.service.injectionDependency.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId){
        cartService.addProductToCart(cartId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Producto agregado al cart con exito");
    }
}
