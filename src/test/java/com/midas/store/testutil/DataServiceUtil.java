package com.midas.store.testutil;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.model.entity.UserEntity;
import com.midas.store.model.request.LoginRequest;
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
    public static LoginRequest createLoginRequest(){
        return LoginRequest.builder()
                .username("abel@gmail.com")
                .password("12345678")
                .build();
    }
    public static LoginRequest createLoginRequestFailure(){
        return LoginRequest.builder()
                .username("failure@gmail.com")
                .password("12345678")
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
    public static List<UserEntity> createUsersTest(){
        return List.of(
                new UserEntity(1L,"Abel","Acevedo","user@gmail.com","12345678","1234567890","Garin",
                        null, new CartEntity()),
                new UserEntity(2L,"Abel","Acevedo","user2@gmail.com","12345678","1234567890","Garin",
                        null, new CartEntity())
        );
    }
}
