package com.midas.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.store.model.request.ProductRequest;
import com.midas.store.testutil.ProductUtil;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


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




}
