package com.midas.store.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midas.store.model.request.LoginRequest;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.testutil.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegister() throws Exception {
        RegisterRequest request = TestUtil.createUserTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        // Verifica que la respuesta contenga el token y el mensaje
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("token");
        assertThat(content).contains("Registro exitoso");

    }
    @Test
    public void testRegisterFailure() throws Exception {
        RegisterRequest request = TestUtil.createUserTest();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // Intentar registrar al mismo usuario nuevamente (esto debería generar un fallo)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertThat(content).contains("El Cliente ya esta registrado");
                });
    }

    @Test
    public void testLoginSuccess() throws Exception {
        //Creo User en memoria
        RegisterRequest request = TestUtil.createUserTest();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // Me logueo
        LoginRequest loginRequest = TestUtil.createLoginTest();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Deserializar la respuesta JSON en un objeto LoginResponse
        String content = result.getResponse().getContentAsString();
        LoginResponse response = objectMapper.readValue(content, LoginResponse.class);
        // Verificar los atributos del objeto LoginResponse
        assertThat(response.getUserId()).isEqualTo(2L); // Se espera el id 2 porq cuando inicia la app se crea un user admin por defecto
        assertThat(response.getName()).isEqualTo("Abel");
        assertThat(response.getLastname()).isEqualTo("Acevedo");
        assertThat(response.getRole()).isEqualTo("[CUSTOMER]");
        assertThat(response.getToken()).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Logueo exitoso");
    }
    @Test
    public void testLoginIncorrectPass() throws Exception {
        //Creo User en memoria
        RegisterRequest request = TestUtil.createUserTest();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // Me logueo
        LoginRequest loginRequest = TestUtil.createLoginIncorrectPass();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andReturn();

        // Deserializar la respuesta JSON en un objeto LoginResponse
      String content = result.getResponse().getContentAsString();
      assertThat(content).contains("El nombre de usuario o contraseña es incorrecto");
    }

    @Test
    public void testLoginIncorrectUsername() throws Exception {
        //Creo User en memoria
        RegisterRequest request = TestUtil.createUserTest();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        // Me logueo
        LoginRequest loginRequest = TestUtil.createLoginIncorrectUsername();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();

        // Deserializar la respuesta JSON en un objeto LoginResponse
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("No hay ninguna cuenta asociada con la dirección de correo electrónico.");
    }

}
