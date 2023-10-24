package com.midas.store.mapper;

import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.OrderEntity;
import com.midas.store.model.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserMapper userMapper;
    private final ProductMapper productMapper;

    public OrderEntity createOrder(CartEntity cart) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());

        return OrderEntity.builder()
                .total(cart.getTotal())
                .user(cart.getUser())
                .purchaseDate(currentDateTime)
                .build();
    }

    public List<OrderResponse> mapToOrderResponseList(List<OrderEntity> orderEntities) {
        return orderEntities.stream()
                .map(order -> mapToOrderResponse(order))
                .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .id(orderEntity.getId())
                .total(orderEntity.getTotal())
                .purchaseDate(orderEntity.getPurchaseDate())
                .userResponse(userMapper.mapToUserResponse(orderEntity.getUser()))
                .productResponses(productMapper.mapToProductResponseList(orderEntity.getProductEntities()))
                .build();
    }
}
