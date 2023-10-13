package com.midas.store.service;

import com.midas.store.mapper.ProductMapper;
import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.repository.ProductRepository;
import com.midas.store.service.injectionDependency.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public void create(ProductRequest request) {
        log.info("Entrando al metodo create de producto en servicio");
        Product product = productMapper.mapToProductRequest(request);
        productRepository.save(product);
    }
}
