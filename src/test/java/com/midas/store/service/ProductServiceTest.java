package com.midas.store.service;

import com.midas.store.exception.ProductAlreadyExistsException;
import com.midas.store.exception.ProductNotFoundException;
import com.midas.store.mapper.ProductMapper;
import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.repository.ProductRepository;
import com.midas.store.service.injectionDependency.ProductService;
import com.midas.store.testutil.AuthUtil;
import com.midas.store.testutil.ProductUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

 @Autowired
 ProductServiceImpl productService;



    @BeforeEach
    public void setUp() {

        // Inicializa los mocks y comportamientos necesarios
        when(productMapper.mapToProductRequest(any(ProductRequest.class))).thenCallRealMethod();
    }

    @Test
    public void testCreate_ProductSuccessful() {
        // Simula un producto que aún no existe
        ProductRequest request = new ProductRequest("NuevoProducto", "Descripción del nuevo producto", 19.99, 50, true, 10);
        when(productRepository.findByNameAndDescription(request.getName(), request.getDescription())).thenReturn(null);

        // Simula el producto que se crea
        Product createdProduct = new Product(request.getName(),request.getPrice(),request.getCount(),request.isState(),request.getStock(),request.getDescription());
        when(productRepository.save(any(Product.class))).thenReturn(createdProduct);

        // Llama al servicio para crear el producto
        productService.create(request);

        // Verifica que el método de repositorio save se haya llamado una vez
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @Transactional
    public void testCreate_ProductAlreadyExists() {
        // Simula un producto que ya existe con el mismo nombre y descripción
        ProductRequest request = new ProductRequest("ProductoExistente", "DescripciónExistente", 10.0, 100, true, 5);
        when(productRepository.findByNameAndDescription(request.getName(), request.getDescription())).thenReturn(new Product());

        // Prueba la creación de un producto que ya existe
        assertThrows(ProductAlreadyExistsException.class, () -> {
            productService.create(request);
        });

        // Verifica que el método de repositorio findByNameAndDescription se haya llamado una vez
        verify(productRepository, times(1)).findByNameAndDescription(request.getName(), request.getDescription());
    }

    @Test
    public void testGetById_ProductFound() {
        // Simula un producto que existe en la base de datos
        long productId = 1L;
        Product product = new Product("ProductoExistente",  10.0, 100, true, 5,"Descripción del producto");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Simula el mapeo del producto a ProductResponse
        ProductResponse expectedResponse = new ProductResponse(1L,"ProductoExistente","Descripción del producto",10.0,100,true,5);
        when(productMapper.mapToProductResponse(product)).thenReturn(expectedResponse);

        // Prueba el método getById y verifica que devuelve la respuesta esperada
        ProductResponse actualResponse = productService.getById(productId);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testGetById_ProductNotFound() {
        // Simula un producto que no existe en la base de datos
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Prueba el método getById y espera una excepción ProductNotFoundException
        assertThrows(ProductNotFoundException.class, () -> {
            productService.getById(productId);
        });
    }



}
