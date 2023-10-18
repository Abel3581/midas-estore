package com.midas.store.controller;

import com.midas.store.model.response.CartResponse;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.service.injectionDependency.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Agregar producto al cart", description = "Agrega un producto al carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado al cart con exito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))}),
            @ApiResponse(responseCode = "400", description = "El password debe tener entre 8 y 25 carcateres",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El carrito con id %s no está registrado.",
                    content = @Content)})
    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId){
        cartService.addProductToCart(cartId, productId);
        return ResponseEntity.status(HttpStatus.OK).body("Producto agregado al cart con exito");
    }

    @Operation(summary = "Buscar cart por Id", description = "Muestra informacion del cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El carrito con id %s no está registrado.",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long id){
        CartResponse response = cartService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
