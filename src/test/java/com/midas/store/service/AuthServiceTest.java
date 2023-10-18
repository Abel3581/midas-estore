package com.midas.store.service;


import com.midas.store.exception.UserNotFoundException;
import com.midas.store.model.request.LoginRequest;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.model.response.RegisterResponse;
import com.midas.store.testutil.DataServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;



import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Autowired
    private AuthServiceImpl authService;


    @BeforeEach
    public void setUp() {

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
        assertEquals(NOT_FOUND, exception.getStatusCode());
        // Verifica el mensaje de la excepción si es necesario.
        assertEquals("El Cliente ya esta registrado", exception.getReason());
    }

    @Test
    public void testLogin_Success() {
        authService.register(DataServiceUtil.createUserRequestTest());
        // Aquí puedes realizar pruebas de inicio de sesión de usuario a nivel de servicio.
        LoginRequest loginRequest = DataServiceUtil.createLoginRequest();
        // Inicializa loginRequest con datos de prueba.
        LoginResponse response = authService.login(loginRequest);

        assertEquals("Logueo exitoso", response.getMessage());
    }

    @Test
    public void testLogin_NotFoundException(){
        authService.register(DataServiceUtil.createUserRequestTest());

        LoginRequest request = DataServiceUtil.createLoginRequestFailure();
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->{
            authService.login(request);
        });
        // Verifica que la excepción tenga el código de estado HttpStatus.NOT_FOUND.
        assertEquals(NOT_FOUND, NOT_FOUND );
        // Verifica el mensaje de la excepción si es necesario.
        assertEquals("No hay ninguna cuenta asociada con la dirección de correo electrónico.", exception.getMessage());

    }


}
