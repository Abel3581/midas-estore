package com.midas.store.service;


import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.RegisterResponse;
import com.midas.store.testutil.DataServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;



import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthServiceImpl authService;



    @BeforeEach
    public void setUp() {
        /*
        role = RoleEntity.builder()
                .id(2L)
                .name(RoleEnum.CUSTOMER)
                .build();
        // Inicializa los mocks
        user = UserEntity.builder()
                .name("ABel")
                .dni("1234567890")
                .address("Address")
                .username("abel@gmail.com")
                .lastname("Acevedo")
                .password("12345678")
                .roles(Set.of(role))
                .cart(new CartEntity())
                .build();
*/
    }

    @Test
    public void testRegister_Success() {
        RegisterRequest registerRequest = DataServiceUtil.createUserRequestTest();

        RegisterResponse response = authService.register(registerRequest);
        // Verifica que la respuesta (response) sea la esperada.
        // Puedes usar aserciones (assertions) para verificar los resultados.
        // Por ejemplo:
         assertEquals("Registro exitoso", response.getMessage());
         assertNotNull(response.getToken());

    }

    @Test
    public void testRegister_UserAlreadyExists() {

        RegisterRequest request = DataServiceUtil.createUserRequestTest();
        authService.register(request);

        // Utilizo assertThrows para verificar que el servicio lanza una excepción ResponseStatusException.
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authService.register(request);
        });

        // Verifica que la excepción tenga el código de estado HttpStatus.NOT_FOUND.
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        // Verifica el mensaje de la excepción si es necesario.
        assertEquals("El Cliente ya esta registrado", exception.getReason());
    }
/*
    @Test
    public void testRegister_DuplicateUser() {
        // Configurar el comportamiento esperado de los mocks

        // Crear una solicitud de registro con un nombre de usuario que ya existe
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existing_user@gmail.com");
        // Configura otros campos según sea necesario

        // Simular el UserRepository para que encuentre un usuario con el mismo nombre
        UserEntity existingUser = new UserEntity();
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(existingUser));

        // Verificar que se lance una excepción ResponseStatusException
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> authService.register(request));

        // Verificar que la excepción tenga el código de estado HTTP NOT_FOUND (404)
        assertEquals("El Cliente ya esta registrado", exception.getReason());
    }

 */
/*
    @Test
    public void testLogin_Success() {
        // Configurar el comportamiento esperado de los mocks para probar el inicio de sesión

        // Crear una solicitud de inicio de sesión de ejemplo
        LoginRequest request = new LoginRequest();
        request.setUsername("example@gmail.com");
        request.setPassword("password");
        // Configura otros campos según sea necesario

        // Simula la autenticación con el AuthenticationManager
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);

        // Simula la búsqueda de un usuario en el UserRepository
        UserEntity userEntity = new UserEntity();
        // Configura los campos del usuario según sea necesario
        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(userEntity));

        // Simula la generación de un token JWT
        when(jwtUtil.generate(request.getUsername())).thenReturn("token");

        // Llamar al método que deseas probar
        LoginResponse response = authService.login(request);

        // Realizar aserciones sobre la respuesta
        assertNotNull(response);
        assertEquals("token", response.getToken());
        // Puedes realizar más aserciones según las expectativas de tu servicio.
    }

 */
}
