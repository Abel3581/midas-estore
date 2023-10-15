package com.midas.store.service;

import com.midas.store.exception.CartEmptyException;
import com.midas.store.mapper.OrderMapper;
import com.midas.store.model.entity.CartEntity;
import com.midas.store.model.entity.Order;
import com.midas.store.model.entity.Product;
import com.midas.store.model.response.OrderResponse;
import com.midas.store.repository.OrderRepository;
import com.midas.store.service.injectionDependency.CartService;
import com.midas.store.service.injectionDependency.OrderService;
import com.midas.store.service.injectionDependency.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public void purchase(Long cartId) {
        CartEntity cart = cartService.buyACar(cartId);
        if(cart.getProducts().isEmpty()){
            log.error(String.format("El carro con id: %s esta vacio", cartId));
            throw new CartEmptyException("El carrito esta vacio");
        }else {
            Order order = orderMapper.createOrder(cart);

            List<Product> productsInCart = cart.getProducts();
            List<Product> savedProducts = new ArrayList<>();

            for (Product product : productsInCart) {
                Product existingProduct = productService.findById(product.getId());
                if (existingProduct != null) {

                    savedProducts.add(existingProduct);
                }
            }
            order.setProducts(savedProducts);
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> responses = orderMapper.mapToOrderResponseList(orders);
        return responses;
    }
}