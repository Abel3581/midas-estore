package com.midas.store.mapper;

import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.Order;
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

    public Order createOrder(CartEntity cart) {
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.systemDefault());

        return Order.builder()
                .total(cart.getTotal())
                .user(cart.getUser())
                .purchaseDate(currentDateTime)
                .build();
    }

    public List<OrderResponse> mapToOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(order -> mapToOrderResponse(order))
                .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .total(order.getTotal())
                .purchaseDate(order.getPurchaseDate())
                .userResponse(userMapper.mapToUserResponse(order.getUser()))
                .productResponses(productMapper.mapToProductResponseList(order.getProductEntities()))
                .build();
    }
}
