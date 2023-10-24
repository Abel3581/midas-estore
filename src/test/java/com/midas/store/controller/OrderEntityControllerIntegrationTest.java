package com.midas.store.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.OrderEntity;
import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.OrderResponse;
import com.midas.store.repository.CartRepository;
import com.midas.store.repository.OrderRepository;
import com.midas.store.repository.ProductRepository;
import com.midas.store.repository.UserRepository;
import com.midas.store.testutil.OrderUtil;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class OrderEntityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Before
    public void setUp(){
        UserEntity user = OrderUtil.createUserOrderTest();
        userRepository.save(user);

        ProductEntity productEntity = OrderUtil.createProductOrderTest();
        productRepository.save(productEntity);

        CartEntity cart = new CartEntity(1L, 0, user, List.of(productEntity));
        user.setCart(cart);
        cartRepository.save(cart);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setTotal(cart.getTotal());
        orderEntity.setProductEntities(cart.getProductEntities());
        orderEntity.setUser(user);
        orderEntity.setId(1L);
        orderEntity.setPurchaseDate(LocalDateTime.now());
        orderRepository.save(orderEntity);
    }

    @Test
    @WithMockUser(authorities = "CUSTOMER")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testPurchaseCart() throws Exception {
        Long cartId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{cartId}", cartId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Compra exitosa"));

    }

    @Test
    @WithMockUser(authorities = "CUSTOMER")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testPurchaseCartFailure() throws Exception {
        Long cartId = 2L;
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(String.format("El carrito con id %s no está registrado", cartId)));

    }

    @Test
    @WithMockUser(authorities = "CUSTOMER")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testPurchaseCartIsEmpty() throws Exception {
        UserEntity user = OrderUtil.createUserOrderTest();
        userRepository.save(user);
        CartEntity cart = new CartEntity(1L,0,user,List.of());
        user.setCart(cart);
        cartRepository.save(cart);

        Long cartId = cart.getId();
        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{cartId}", cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("El carrito esta vacio"));

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testGetAllOrders() throws Exception {
        // Realizar la solicitud GET al controlador para obtener todos los productos
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andExpect(status().isOk())
                .andReturn();

        // Obtener la respuesta JSON como una cadena
        String content = result.getResponse().getContentAsString();

        // Analizar la respuesta JSON a objetos Java (usando ObjectMapper)
        List<OrderResponse> orderResponses = objectMapper.readValue(
                content,
                new TypeReference<List<OrderResponse>>() {}
        );

        // Realizar verificaciones en la lista de Productos
        assertEquals(1, orderResponses.size());
        assertEquals("Abel", orderResponses.get(0).getUserResponse().getName());
        assertEquals("Python", orderResponses.get(0).getProductResponses().get(0).getName());

    }

}
