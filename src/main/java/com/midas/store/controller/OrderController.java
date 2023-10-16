package com.midas.store.controller;

import com.midas.store.model.response.OrderResponse;
import com.midas.store.service.injectionDependency.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{cartId}")
    public ResponseEntity<String> purchase(@PathVariable Long cartId){
        orderService.purchase(cartId);
        return ResponseEntity.status(HttpStatus.OK).body("Compra exitosa");
    }
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(){
        List<OrderResponse> responses = orderService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }

}
