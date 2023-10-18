package com.midas.store.controller;

import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.request.ProductUpdateRequest;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.ProductUpdateResponse;
import com.midas.store.service.injectionDependency.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@SecurityRequirement(name = "bearer-key")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Crea un producto", description = "Crea un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado con exito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "El producto ya esta registrado",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProductRequest request){
        log.info("Entrando al controlador create" + request.toString());
        productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
    }

    @Operation(summary = "Buscar producto por id", description = "Busca un producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class))}),
            @ApiResponse(responseCode = "403", description = "",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        ProductResponse response = productService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Buscar productos", description = "Devuelve todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))}),
            @ApiResponse(responseCode = "403", description = "",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> responses = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductUpdateResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        log.info("Entrando al Controller update");
        ProductUpdateResponse response = productService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Borrar producto", description = "Borra producto por Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "403", description = "Acceso denegado. No tienes permiso para realizar esta acción.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "El producto no esta registrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleted(@PathVariable Long id){
        productService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).body("Producto borrado con exito");
    }
}
