package com.leporonitech.orders.service;

import com.leporonitech.orders.model.Order;
import com.leporonitech.orders.repository.OrderRepository;
import com.leporonitech.products.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(List<Long> productIds) {
        List<Product> products = getProductDataFromAPI(productIds);

        Order order = new Order();
        order.setOrderDate(new Date());
        order.setProducts(products);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getOrderDate());
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        order.setDeliveryDate(calendar.getTime());

        orderRepository.save(order);

        return order;
    }

    private List<Product> getProductDataFromAPI(List<Long> productIds) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("productIds", productIds);
        Exchange exchange = producerTemplate.send("direct:getProductsByIds", ExchangePattern.InOut, new Processor() {
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setHeaders(headers);
            }
        });

        return exchange.getMessage().getBody(List.class);
    }
}