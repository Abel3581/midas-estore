package com.midas.store.service;

import com.midas.store.exception.ProductAlreadyExistsException;
import com.midas.store.exception.ProductNotFoundException;
import com.midas.store.mapper.ProductMapper;
import com.midas.store.model.entity.ProductEntity;
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
        ProductEntity productEntity = productRepository.findByNameAndDescription(request.getName(),request.getDescription());
        if(productEntity == null ){
            ProductEntity productEntityCreate = productMapper.mapToProductRequest(request);
            productRepository.save(productEntityCreate);

        }else {
            log.error("Error al crear producto");
            throw new ProductAlreadyExistsException("El producto ya esta registrado");
        }


    }

    @Override
    public ProductResponse getById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        ProductResponse response = productMapper.mapToProductResponse(product.get());
        return response;
    }

    @Override//No lleva exception ya que si no encuentra productos devuelve una lista vacia
    public List<ProductResponse> getAllProduct() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductResponse> responses = productMapper.mapToProductResponseList(productEntities);
        return responses;
    }

    @Transactional
    @Override
    public ProductUpdateResponse update(ProductUpdateRequest request, Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        ProductEntity productEntity = productOptional.get();
        productEntity.setName(request.getName());
        productEntity.setDescription(request.getDescription());
        productEntity.setPrice(request.getPrice());
        productEntity.setStock(request.getStock());
        productEntity.setState(request.isState());
        productEntity.setCount(request.getCount());
        ProductUpdateResponse response = productMapper.mapToProductUpdate(productEntity);
        return response;
    }

    @Override
    public void deleted(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + id);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        productRepository.delete(product.get());
    }

    @Override
    public ProductEntity findById(Long productId) {
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isEmpty()){
            log.error("Producto no encontrado con id: " + productId);
            throw new ProductNotFoundException("El producto no esta registrado");
        }
        return product.get();
    }


}
