package com.midas.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.store.model.entity.Product;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.repository.ProductRepository;
import com.midas.store.service.injectionDependency.ProductService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Before
    public void setUp() {
        // Cargar datos iniciales en la base de datos de prueba
        List<Product> product = ProductUtil.createProductListTest();
        productRepository.saveAll(product);
    }
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testGetProductById() throws Exception {
        Long productId = 1L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ProductResponse productResponse = objectMapper.readValue(responseJson, ProductResponse.class);
        
        assertEquals(productId, productResponse.getId());
        assertEquals("Java", productResponse.getName());
        assertEquals(19.99, productResponse.getPrice(), 0.01);
        assertEquals("17", productResponse.getDescription());
        assertEquals(true, productResponse.isState());


    }



    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testCreateProductWithAdminRole() throws Exception {
        // Preparar una solicitud POST con el cuerpo adecuado
        ProductRequest request = ProductUtil.createProductTest();
        String content = objectMapper.writeValueAsString(request);

        // Realizar la solicitud POST al controlador
        mockMvc.perform(post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Producto creado con exito"));
    }
    @Test
    @WithMockUser(authorities = {"CUSTOMER"})
    public void testCreateProductWithCustomerRole() throws Exception {
        // Preparar una solicitud POST con el cuerpo adecuado
        ProductRequest request = ProductUtil.createProductTest();


        // Realizar la solicitud POST al controlador
        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertThat(content).contains("Acceso denegado. No tienes permiso para realizar esta acción.");
                });

    }
    @Test
    @WithMockUser(authorities = {"ADMIN"})
    public void testCreateProductDuplicate() throws Exception {

        ProductRequest request = ProductUtil.createProductTest();

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/products")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> {
                    String content2 = result.getResponse().getContentAsString();
                    assertThat(content2).contains("El producto ya esta registrado");
                });
    }






}
