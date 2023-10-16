package com.midas.store.testutil;

import com.midas.store.enums.RoleEnum;
import com.midas.store.model.entity.Order;
import com.midas.store.model.entity.Product;
import com.midas.store.model.entity.RoleEntity;
import com.midas.store.model.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class OrderUtil {

    public static UserEntity createUserOrderTest(){
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
    public static Product createProductOrderTest(){
        return Product.builder()
                .name("Python")
                .price(9000)
                .count(39)
                .state(true)
                .stock(18)
                .description("12")
                .build();
    }
    public static List<Product> productListOrder(){
        return List.of(
                new Product("Java", 19.99, 100, true, 10, "17"),
                new Product("Spring Boot", 20.99, 100, true, 19, "3.14")
        );
    }

}
