package com.leporonitech.products.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getProducts")
                .to("bean:productService?method=getAllProducts")
                .log("Products retrieved from the product API: ${body}")
                .to("direct:sendToOrderAPI");
    }
}