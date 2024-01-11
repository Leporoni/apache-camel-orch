package com.leporonitech.orders.controller;

import com.leporonitech.orders.model.Order;
import com.leporonitech.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long productId, @RequestParam int quantity) {
        Order createdOrder = orderService.createOrder(productId, quantity);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
}