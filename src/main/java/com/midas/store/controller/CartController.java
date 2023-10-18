package com.midas.store.controller;

import com.midas.store.model.response.CartResponse;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.service.injectionDependency.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Agregar producto al cart", description = "Agrega un producto al carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado al cart con exito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))}),
            @ApiResponse(responseCode = "400", description = "El password debe tener entre 8 y 25 carcateres",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El carrito con id %s no está registrado.",
                    content = @Content)})
    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId){
        cartService.addProductToCart(cartId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Producto agregado al cart con exito");
    }
    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id){
        CartResponse response = cartService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
