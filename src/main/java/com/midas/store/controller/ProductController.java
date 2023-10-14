package com.midas.store.controller;

import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.request.ProductUpdateRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.ProductUpdateResponse;
import com.midas.store.service.injectionDependency.ProductService;
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
public class ProductController {

    private final ProductService productService;
    private final HttpServletRequest request;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProductRequest request){
        log.info("Entrando al controlador create" + request.toString());
        productService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado con exito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        ProductResponse response = productService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(){
        List<ProductResponse> responses = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> update(@PathVariable Long id, @RequestBody ProductUpdateRequest request){
        log.info("Entrando al Controller update");
        ProductUpdateResponse response = productService.update(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleted(@PathVariable Long id){
        productService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).body("Producto borrado con exito");
    }
}
