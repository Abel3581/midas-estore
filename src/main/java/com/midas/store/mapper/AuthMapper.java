package com.midas.store.mapper;

import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.RegisterRequest;
import com.midas.store.model.response.RegisterResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public UserEntity mapToRegisterRequest(RegisterRequest request) {
        return UserEntity.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .dni(request.getDni())
                .address(request.getAddress())
                .username(request.getUsername())
                .build();
    }

    public RegisterResponse mapToRegisterResponse(UserEntity userCreate) {
        return RegisterResponse.builder()
                .message("Registro exitoso")
                .build();
    }
}
