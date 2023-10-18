package com.midas.store.service;

import com.midas.store.exception.CartEmptyException;
import com.midas.store.mapper.OrderMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.Order;
import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.OrderResponse;
import com.midas.store.repository.OrderRepository;
import com.midas.store.service.injectionDependency.CartService;
import com.midas.store.service.injectionDependency.OrderService;
import com.midas.store.service.injectionDependency.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @MockBean
    private  OrderRepository orderRepository;
    @MockBean
    private CartService cartService;
    @MockBean
    private  ProductService productService;
    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPurchase_NonEmptyCart() {
        Long cartId = 1L;
        CartEntity cart = new CartEntity(cartId, 0, new UserEntity(), new ArrayList<>());
        ProductEntity productEntity = new ProductEntity(2L, "ProductoExistente", 10.0, 100, true, 5, "Descripción");
        cart.addProduct(productEntity);
        List<ProductEntity> productsInCart = new ArrayList<>();
        productsInCart.add(productEntity);

        // Simula que el carrito contiene un producto
        Mockito.when(cartService.buyACar(cartId)).thenReturn(cart);
        //Mockito.when(cart.getProductEntities()).thenReturn(productsInCart);
        Mockito.when(orderMapper.createOrder(cart)).thenReturn(new Order());
        Mockito.when(productService.findById(productEntity.getId())).thenReturn(productEntity);

        // Verifica que el método funcione correctamente sin lanzar excepciones
        orderService.purchase(cartId);
    }

    @Test
    public void testPurchase_EmptyCart() {
        Long cartId = 1L;
        CartEntity emptyCart = new CartEntity(cartId, 0, new UserEntity(), new ArrayList<>());

        // Simula que el carrito está vacío
        Mockito.when(cartService.buyACar(cartId)).thenReturn(emptyCart);

        // Verifica que el método lance la excepción CartEmptyException
        assertThrows(CartEmptyException.class, () -> {
            orderService.purchase(cartId);
        });
    }

    @Test
    public void testGetAll() {
        List<Order> orders = new ArrayList<>();
        List<OrderResponse> expectedResponses = new ArrayList<>();

        // Simula la lista de órdenes
        Mockito.when(orderRepository.findAll()).thenReturn(orders);
        Mockito.when(orderMapper.mapToOrderResponseList(orders)).thenReturn(expectedResponses);

        // Verifica que el método devuelva la lista de respuestas de órdenes
        List<OrderResponse> actualResponses = orderService.getAll();
        assertEquals(expectedResponses, actualResponses);
    }
}
