package com.leporonitech.orders.service;

import com.leporonitech.orders.model.Order;
import com.leporonitech.orders.repository.OrderRepository;
import com.leporonitech.products.model.Product;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> createOrders(List<Product> products) {
        List<Order> orders = new ArrayList<>();

        for (Product product : products) {
            Order order = new Order();
            order.setOrderDate(new Date());
            order.setProductName(product.getName());
            order.setProductPrice(product.getPrice());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            calendar.add(Calendar.DAY_OF_MONTH, 5);
            order.setDeliveryDate(calendar.getTime());

            orders.add(order);
        }

        return orders;
    }

    public void saveOrders(List<Order> orders) {
        for (Order order : orders) {
            orderRepository.save(order);
        }
    }
}