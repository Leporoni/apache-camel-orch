package com.leporonitech.orders.route;

import com.leporonitech.orders.model.Order;
import com.leporonitech.orders.service.OrderService;
import com.leporonitech.products.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OrderRoute extends RouteBuilder {

    @Autowired
    private OrderService orderService;

    @Override
    public void configure() throws Exception {
        from("timer://timer?period=5000")
                .to("http://localhost:8087/products")
                .unmarshal().json(JsonLibrary.Jackson, List.class)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        List<Map<String, Object>> list = exchange.getIn().getBody(List.class);
                        List<Product> products = new ArrayList<>();
                        for (Map<String, Object> map : list) {
                            Product product = new Product();
                            product.setName((String) map.get("name"));
                            product.setPrice(BigDecimal.valueOf((Double) map.get("price")));
                            products.add(product);
                        }
                        exchange.getIn().setBody(products);
                    }
                })
                .log("Received products from product API: ${body}")
                .to("direct:createOrder");

        from("direct:createOrder")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        List<Product> products = exchange.getIn().getBody(List.class);
                        List<Order> orders = orderService.createOrders(products);
                        exchange.getIn().setBody(orders);
                    }
                })
                .to("direct:saveOrder");

        from("direct:saveOrder")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        List<Order> orders = exchange.getIn().getBody(List.class);
                        orderService.saveOrders(orders);
                    }
                });
    }
}