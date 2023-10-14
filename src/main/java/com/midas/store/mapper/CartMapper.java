package com.midas.store.mapper;

import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.response.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    public CartResponse mapToCartResponse(CartEntity cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .total(cart.getTotal())
                .user(userMapper.mapToUserResponse(cart.getUser()))
                .productResponses(productMapper.mapToProductResponseList(cart.getProducts()))
                .build();
    }
}
