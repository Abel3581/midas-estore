package com.midas.store.service;

import com.midas.store.mapper.CartMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.ProductEntity;
import com.midas.store.repository.CartRepository;
import com.midas.store.service.injectionDependency.ProductService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CartServiceTest {

    @MockBean
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private CartMapper cartMapper;
    @Autowired
    private CartServiceImpl cartService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProductToCart_Success() {
        Long cartId = 1L;
        Long productId = 2L;

        CartEntity cartEntity = new CartEntity(cartId);
        ProductEntity productEntity = new ProductEntity(productId);

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity));
        when(productService.findById(productId)).thenReturn(productEntity);

        cartService.addProductToCart(cartId, productId);

        // Verificar que el producto se ha añadido al carrito
        assertEquals(1, cartEntity.getProducts().size());
        assertEquals(productEntity, cartEntity.getProducts().get(0));
    }
}
