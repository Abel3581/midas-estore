package com.midas.store.controller;

import com.midas.store.model.request.ProductRequest;
import com.midas.store.service.injectionDependency.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProductRequest request){
        log.info("Entrando al controlador create" + request.toString());
        productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
    }
}
