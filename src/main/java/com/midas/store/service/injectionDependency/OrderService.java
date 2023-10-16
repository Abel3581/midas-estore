package com.midas.store.service.injectionDependency;

import com.midas.store.model.response.OrderResponse;

import java.util.List;

public interface OrderService {
    void purchase(Long cartId);

    List<OrderResponse> getAll();
}
