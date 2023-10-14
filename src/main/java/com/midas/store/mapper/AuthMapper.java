package com.midas.store.mapper;

import com.midas.store.model.entity.Cart;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.LoginResponse;
import com.midas.store.model.response.RegisterResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthMapper {
    public UserEntity mapToRegisterRequest(RegisterRequest request) {
        return UserEntity.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .dni(request.getDni())
                .address(request.getAddress())
                .username(request.getUsername())
                .cart(new Cart()) // Se crea un cart para el usuario

                .build();
    }

    public RegisterResponse mapToRegisterResponse(UserEntity userCreate) {
        return RegisterResponse.builder()
                .message("Registro exitoso")
                .build();
    }

    public LoginResponse mapToLoginResponse(UserEntity user, String jwt) {
        return LoginResponse.builder()
                .name(user.getName())
                .lastname(user.getLastname())
                .message("Logueo exitoso")
                .userId(user.getId())
                .token(jwt)
                .role(user.getRoles().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toList()).toString())
                .build();
    }
}
