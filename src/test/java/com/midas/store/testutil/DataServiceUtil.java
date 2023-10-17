package com.midas.store.testutil;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.RegisterRequest;

import java.util.List;
import java.util.Set;

public class DataServiceUtil {

    public static RegisterRequest createUserRequestTest(){
        return RegisterRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .address("Tortuguitas")
                .dni("1234567890")
                .password("12345678")
                .username("abel@gmail.com")
                .build();
    }
    public static UserEntity createUserTest(){
        return UserEntity.builder()
                .id(2L)
                .name("Abel")
                .lastname("Acevedo")
                .address("Tortuguitas")
                .dni("1234567890")
                .username("abel@gmail.com")
                .password("12345678")
                .roles(Set.of(new RoleEntity(1L, RoleEnum.CUSTOMER)))
                .build();
    }
    public static List<RoleEntity> createRolesTest(){
        return List.of(
                new RoleEntity(1L,RoleEnum.CUSTOMER),
                new RoleEntity(2L,RoleEnum.ADMIN)
        );
    }
    public static RoleEntity createRoleCustomerTest(){
        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setName(RoleEnum.CUSTOMER);
        return role;
    }
}
