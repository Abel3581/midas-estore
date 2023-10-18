package com.midas.store.service;

import com.midas.store.exception.CartNotFoundException;
import com.midas.store.mapper.CartMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.CartResponse;
import com.midas.store.repository.CartRepository;
import com.midas.store.service.injectionDependency.CartService;
import com.midas.store.service.injectionDependency.ProductService;
import com.midas.store.testutil.DataServiceUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
public class CartServiceTest {

    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private ProductService productService;
    @Mock
    private CartMapper cartMapper;
    @Autowired
    private CartService cartService;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddProductToCart_Success() {
        Long cartId = 1L;
        Long productId = 2L;


        ProductEntity productEntity = new ProductEntity(productId,"ProductoExistente",  10.0, 100, true, 5,"Descripción del producto");
        CartEntity cartEntity = new CartEntity(cartId,0, DataServiceUtil.createUserTest(), new ArrayList<>());

        when(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity));
        when(productService.findById(productId)).thenReturn(productEntity);

        cartService.addProductToCart(cartId, productId);

        // Verificar que el producto se ha añadido al carrito
        assertEquals(1, cartEntity.getProductEntities().size());
        assertEquals(productEntity, cartEntity.getProductEntities().get(0));
    }

    @Test
    public void testAddProductToCart_Failure_ProductAlreadyInCart() {
        Long cartId = 1L;
        Long productId = 2L;

        // Simula que el carrito no existe en el repositorio
        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // Verifica que el método lance la excepción CartNotFoundException
        assertThrows(CartNotFoundException.class, () -> {
            cartService.addProductToCart(cartId, productId);
        });
    }

    @Test
    public void testBuyACar_CartFound() {
        Long cartId = 1L;
        CartEntity cartEntity = new CartEntity(cartId, 0, null, null);

        // Simula que el carrito existe en el repositorio
        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity));

        // Verifica que el método devuelva el carrito encontrado
        CartEntity result = cartService.buyACar(cartId);
        assertEquals(cartEntity, result);
    }

    @Test
    public void testBuyACar_CartNotFound() {
        Long cartId = 1L;

        // Simula que el carrito no existe en el repositorio
        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // Verifica que el método lance la excepción CartNotFoundException
        assertThrows(CartNotFoundException.class, () -> {
            cartService.buyACar(cartId);
        });
    }

    @Test
    public void testFindById_CartFound() {
        Long cartId = 1L;
        CartEntity cartEntity = new CartEntity(cartId, 0, new UserEntity(), new ArrayList<>());

        // Simula que el carrito existe en el repositorio
        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.of(cartEntity));
        Mockito.when(cartMapper.mapToCartResponse(cartEntity)).thenReturn(new CartResponse());

        // Verifica que el método devuelva una respuesta de carrito
        CartResponse response = cartService.findById(cartId);
        assertEquals(cartEntity.getId(), response.getId());
    }

    @Test
    public void testFindById_CartNotFound() {
        Long cartId = 1L;

        // Simula que el carrito no existe en el repositorio
        Mockito.when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

        // Verifica que el método lance la excepción CartNotFoundException
        assertThrows(CartNotFoundException.class, () -> {
            cartService.findById(cartId);
        });
    }

}
