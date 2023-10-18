package com.midas.store.testutil;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.LoginRequest;
import com.midas.store.model.request.RegisterRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AuthUtil {

    public static RegisterRequest createUserTest(){
        return RegisterRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .address("Tortuguitas BS AS")
                .dni("1234567890")
                .password("12345678")
                .username("cliente@gmail.com")
                .build();
    }
    public static LoginRequest createLoginTest(){
        return LoginRequest.builder()
                .username("cliente@gmail.com")
                .password("12345678")
                .build();
    }
    public static LoginRequest createLoginIncorrectPass(){
        return LoginRequest.builder()
                .username("cliente@gmail.com")
                .password("123456789we")
                .build();
    }
    public static LoginRequest createLoginIncorrectUsername(){
        return LoginRequest.builder()
                .username("cliente123@gmail.com")
                .password("12345678")
                .build();
    }
    public static LoginRequest createLoginAdmin(){
        return LoginRequest.builder()
                .username("admin@gmail.com")
                .password("12345678")
                .build();
    }
    public static List<UserEntity> createUserEntityList(){
        return List.of(
                new UserEntity(1L,"Abel","Accevedo","customer@gmail.com","12345678","dni1234567","Tortuguitas",
                        Set.of(new RoleEntity(1L, RoleEnum.ADMIN)),new CartEntity()),
                new UserEntity(2L,"Mafer","Palencia","customer@gmail.com","12345678","1234567890","Colombia",
                        Set.of(new RoleEntity(2L,RoleEnum.CUSTOMER)),new CartEntity())
        );
    }
    public static UserEntity createUserCartTest(){
        return UserEntity.builder()
                .id(1L)
                .name("Abel")
                .lastname("Acevedo")
                .address("Tortuguitas")
                .dni("1234567890")
                .username("abel@gmail.com")
                .password("12345678")
                .roles(Set.of(new RoleEntity(1L, RoleEnum.CUSTOMER)))
                .build();
    }

}
