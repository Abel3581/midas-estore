package com.midas.store.service;

import com.midas.store.mapper.UserMapper;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.response.UserResponse;
import com.midas.store.repository.UserRepository;
import com.midas.store.service.injectionDependency.UserService;
import com.midas.store.testutil.DataServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        // Inicializa los mocks y comportamientos necesarios
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
    }

    @Test
    public void testGetAllUsers() {

        // Simula la lista de usuarios que se espera devolver desde el repositorio
        List<UserEntity> userEntities = DataServiceUtil.createUsersTest();

        // Mock del UserRepository para devolver la lista simulada
        when(userRepository.findAll()).thenReturn(userEntities);

        // Mockear el comportamiento de UserMapper para mapear las entidades a respuestas de usuario
        List<UserResponse> expectedUserResponses = List.of(
                new UserResponse(1L,"Abel","Acevedo","user@gmail.com","1234567890","Garin"),
                new UserResponse(2L,"Abel","Acevedo","user2@gmail.com","1234567890","Garin")
        );

        when(userMapper.mapToUserEntityList(userEntities)).thenReturn(expectedUserResponses);

        // Llama al servicio para obtener la lista de usuarios
        List<UserResponse> actualUserResponses = userService.getAllUsers();

        // Verifica que el servicio devuelva la lista esperada
        assertEquals(expectedUserResponses, actualUserResponses);

    }
}
