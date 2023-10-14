package com.midas.store.service;

import com.midas.store.exception.ProductAlreadyExistsException;
import com.midas.store.exception.ProductNotFoundException;
import com.midas.store.mapper.ProductMapper;
import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.request.ProductUpdateRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.ProductUpdateResponse;
import com.midas.store.repository.ProductRepository;
import com.midas.store.service.injectionDependency.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public void create(ProductRequest request) {
        log.info("Entrando al metodo crear producto en servicio");
        Product product = productRepository.findByNameAndDescription(request.getName(),request.getDescription());
        if(product == null ){
            Product productCreate = productMapper.mapToProductRequest(request);
            productRepository.save(productCreate);

        }else {
            log.error("Error al crear producto");
            throw new ProductAlreadyExistsException("El producto ya esta registrado");
        }


    }

    @Override
    public ProductResponse getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        ProductResponse response = productMapper.mapToProductResponse(product.get());
        return response;
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = productMapper.mapToProductResponseList(products);
        return responses;
    }

    @Transactional
    @Override
    public ProductUpdateResponse update(ProductUpdateRequest request, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        Product product = productOptional.get();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setState(request.isState());
        product.setCount(request.getCount());
        ProductUpdateResponse response = productMapper.mapToProductUpdate(product);
        return response;
    }

    @Override
    public void deleted(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        productRepository.delete(product.get());
    }


}
