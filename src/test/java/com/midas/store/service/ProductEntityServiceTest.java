package com.midas.store.service;

import com.midas.store.exception.ProductAlreadyExistsException;
import com.midas.store.exception.ProductNotFoundException;
import com.midas.store.mapper.ProductMapper;
import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.request.ProductUpdateRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.ProductUpdateResponse;
import com.midas.store.model.response.UserResponse;
import com.midas.store.repository.ProductRepository;
import com.midas.store.service.injectionDependency.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class ProductEntityServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

     @Autowired
     private ProductService productService;



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
        ProductEntity createdProductEntity = new ProductEntity(request.getName(),request.getPrice(),request.getCount(),request.isState(),request.getStock(),request.getDescription());
        when(productRepository.save(any(ProductEntity.class))).thenReturn(createdProductEntity);

        // Llama al servicio para crear el producto
        productService.create(request);

        // Verifica que el método de repositorio save se haya llamado una vez
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    @Transactional
    public void testCreate_ProductAlreadyExists() {
        // Simula un producto que ya existe con el mismo nombre y descripción
        ProductRequest request = new ProductRequest("ProductoExistente", "DescripciónExistente", 10.0, 100, true, 5);
        when(productRepository.findByNameAndDescription(request.getName(), request.getDescription())).thenReturn(new ProductEntity());

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
        ProductEntity productEntity = new ProductEntity(productId,"ProductoExistente",  10.0, 100, true, 5,"Descripción del producto");
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        // Simula el mapeo del producto a ProductResponse
        ProductResponse expectedResponse = new ProductResponse(1L,"ProductoExistente","Descripción del producto",10.0,100,true,5);
        when(productMapper.mapToProductResponse(productEntity)).thenReturn(expectedResponse);

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

    @Test

    public void testGetAllProduct() {
        // Simula una lista de productos
        List<ProductEntity> productEntities = List.of(
            new ProductEntity( 1L,"Producto1", 10.0, 10, true, 50, "Descripción1"),
            new ProductEntity( 2L,"Producto2", 109.0, 10, true, 50, "Descripción2")
        );
        when(productRepository.findAll()).thenReturn(productEntities);

        // Simula el mapeo de los productos a ProductResponse
        List<ProductResponse> expectedResponses = List.of(
            new ProductResponse(1L, "Producto1", "Descripción1", 10.0, 10, true, 50),
            new ProductResponse(2L, "Producto2", "Descripción2", 109.0, 10, true, 50)
        );
        when(productMapper.mapToProductResponseList(productEntities)).thenReturn(expectedResponses);


        // Prueba el método getAllProduct y verifica que devuelva la lista esperada
        List<ProductResponse> actualResponses = productService.getAllProduct();
        assertEquals(expectedResponses, actualResponses);
    }

    @Test
    public void testUpdate_ProductFound() {
        // Simula un producto que existe en la base de datos
        long productId = 1L;
        ProductEntity product = new ProductEntity(1L,"ProductoExistente", 10.0, 10, true, 50, "Descripción");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Simula el mapeo del producto a ProductUpdateResponse
        ProductUpdateRequest updateRequest = new ProductUpdateRequest("ProductoModificado", "Nueva descripción", 20.0, 200, false, 10);
        ProductUpdateResponse expectedResponse = new ProductUpdateResponse("Producto actualizado",updateRequest.getName(), updateRequest.getDescription(), updateRequest.getPrice(), updateRequest.getCount(), updateRequest.isState(), updateRequest.getStock());
        when(productMapper.mapToProductUpdate(product)).thenReturn(expectedResponse);

        // Prueba el método update y verifica que devuelva la respuesta esperada
        ProductUpdateResponse actualResponse = productService.update(updateRequest, productId);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testUpdate_ProductNotFound() {
        // Simula un producto que no existe en la base de datos
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Prueba el método update y espera una excepción ProductNotFoundException
        assertThrows(ProductNotFoundException.class, () -> {
            productService.update(new ProductUpdateRequest(), productId);
        });
    }

    @Test
    public void testDelete_ProductFound() {
        // Simula un producto que existe en la base de datos
        long productId = 1L;
        ProductEntity product = new ProductEntity("ProductoExistente",  10.0, 100, true, 5,"Descripción del producto");

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Prueba el método deleted y verifica que el producto se elimine
        productService.deleted(productId);

        // Verifica que el método de repositorio delete se haya llamado una vez
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDelete_ProductNotFound() {
        // Simula un producto que no existe en la base de datos
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Prueba el método deleted y espera una excepción ProductNotFoundException
        assertThrows(ProductNotFoundException.class, () -> {
            productService.deleted(productId);
        });
    }

    @Test
    public void testFindProductById_Success() {
        Long productId = 1L;
        ProductEntity expectedProduct = new ProductEntity(productId, "Producto1", 10.0, 10, true, 50, "Descripción1");

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        ProductEntity actualProduct = productService.findById(productId);

        // Verificar que el producto encontrado coincida con el esperado
        assertEquals(expectedProduct, actualProduct);
    }


}
