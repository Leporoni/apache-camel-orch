package com.leporonitech.orders.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class OrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:sendToOrderAPI")
                .to("http://localhost:8087/products")
                .log("Received products from product API: ${body}")
                .to("direct:processOrderData");
    }
}