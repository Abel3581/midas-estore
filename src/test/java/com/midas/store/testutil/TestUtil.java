package com.midas.store.testutil;

import com.midas.store.model.request.LoginRequest;
import com.midas.store.model.request.RegisterRequest;

public class TestUtil {

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
}
