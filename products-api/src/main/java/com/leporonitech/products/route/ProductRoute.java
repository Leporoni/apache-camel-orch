package com.leporonitech.products.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getAllProducts")
                .log("Products retrieved from the product API: ${body}")
                .to("http://localhost:8088/orders");
    }
}