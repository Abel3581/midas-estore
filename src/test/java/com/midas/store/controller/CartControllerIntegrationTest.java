package com.midas.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.ProductEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.repository.CartRepository;
import com.midas.store.repository.ProductRepository;
import com.midas.store.repository.UserRepository;
import com.midas.store.service.injectionDependency.CartService;
import com.midas.store.testutil.AuthUtil;
import com.midas.store.testutil.ProductUtil;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class CartControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;




    @Before
    public void setUp(){
        UserEntity user = AuthUtil.createUserCartTest();
        userRepository.save(user);
        ProductEntity productEntity = ProductUtil.createProductEntityTest();
        productRepository.save(productEntity);
        CartEntity cart = new CartEntity(1L, 0, user, new ArrayList<>());
        user.setCart(cart);
        cartRepository.save(cart);

    }

    @Test
    @WithMockUser(authorities = "CUSTOMER")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testAddProductToCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/carts/1/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Producto agregado al cart con exito"));
    }
    @Test
    @WithMockUser(authorities = "CUSTOMER")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testAddProductToCartFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/carts/1/10"))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("El producto no esta registrado"));
    }

    @Test
    @WithMockUser(authorities = "CUSTOMER")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
    public void testGetCartById() throws Exception {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setId(2L);
        cartEntity.setTotal(2777);
        cartEntity.setProductEntities(new ArrayList<>());
        cartEntity.setUser(new UserEntity());
        cartRepository.save(cartEntity);
        // Realiza una solicitud GET al endpoint del controlador
        mockMvc.perform(MockMvcRequestBuilders.get("/carts/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));


    }

}
