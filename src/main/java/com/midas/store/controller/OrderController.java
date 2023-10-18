package com.midas.store.controller;

import com.midas.store.model.response.OrderResponse;
import com.midas.store.service.injectionDependency.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@SecurityRequirement(name = "bearer-key")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Realiza la compra al cart", description = "Crea la compra por id del cart del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra exitosa",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El carrito esta vacio",
                    content = @Content),})
    @PostMapping("/{cartId}")
    public ResponseEntity<String> purchase(@PathVariable Long cartId){
        orderService.purchase(cartId);
        return ResponseEntity.status(HttpStatus.OK).body("Compra exitosa");
    }

    @Operation(summary = "Devuelve todas las compras", description = "Devuelve todas las compras sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class)))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(){
        List<OrderResponse> responses = orderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

}
