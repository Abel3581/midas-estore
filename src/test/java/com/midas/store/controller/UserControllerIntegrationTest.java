package com.midas.store.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.ProductResponse;
import com.midas.store.model.response.UserResponse;
import com.midas.store.repository.UserRepository;
import com.midas.store.testutil.AuthUtil;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {

        List<UserEntity> userEntities = AuthUtil.createUserEntityList();
        userRepository.saveAll(userEntities);
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testGetAllUserList() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Obtener la respuesta JSON como una cadena
        String content = result.getResponse().getContentAsString();

        // Analizar la respuesta JSON a objetos Java (usando ObjectMapper)
        List<UserResponse> userResponses = objectMapper.readValue(
                content,
                new TypeReference<List<UserResponse>>() {}
        );
        // Realizar verificaciones en la lista de Usuarios
        assertEquals(2, userResponses.size());
        assertEquals("Abel", userResponses.get(0).getName());
        assertEquals("Mafer", userResponses.get(1).getName());

    }
    @Test
    @WithMockUser(authorities = "CUSTOMER")
    public void testGetAllUserListWithCustomerRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(result -> {

                    String content = result.getResponse().getContentAsString();
                    assertThat(content).contains("Acceso denegado. No tienes permiso para realizar esta acción.");
                });

    }
}
