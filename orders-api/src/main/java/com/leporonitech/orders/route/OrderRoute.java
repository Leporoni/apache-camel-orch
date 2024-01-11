package com.leporonitech.orders.route;

import com.leporonitech.orders.service.OrderService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("/orders")
                .post().to("direct:createOrder");

        from("direct:createOrder")
                .bean(OrderService.class, "createOrder");

        from("direct:getAllProductsFromProductAPI")
                .to("sql:SELECT product_id, product_name, product_price FROM product?dataSource=postgres")
                .log("Retrieved all products from product API: ${body}");
    }
}
