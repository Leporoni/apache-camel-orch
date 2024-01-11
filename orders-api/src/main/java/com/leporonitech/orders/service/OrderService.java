package com.leporonitech.orders.service;

import com.leporonitech.orders.exception.ProductNotFoundException;
import com.leporonitech.orders.model.Order;
import com.leporonitech.orders.repository.OrderRepository;
import com.leporonitech.products.model.Product;
import com.leporonitech.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Long productId, int quantity) {
        Optional<Product> productOptional = productService.getProductById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            Order order = new Order();
            order.setProductId(product.getId());
            order.setQuantity(quantity);
            order.setOrderDate(LocalDateTime.now());
            order.setDeliveryDate(LocalDateTime.now().plusDays(5));

            Order savedOrder = orderRepository.save(order);

            return savedOrder;
        } else {
            throw new ProductNotFoundException("Product not found...");
        }
    }
}