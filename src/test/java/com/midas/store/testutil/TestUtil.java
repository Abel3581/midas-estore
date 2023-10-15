package com.midas.store.testutil;

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
}
