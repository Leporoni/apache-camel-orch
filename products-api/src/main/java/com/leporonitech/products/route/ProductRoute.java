package com.leporonitech.products.route;

import com.leporonitech.products.service.ProductService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("servlet");

        rest("/products")
                .get().to("direct:getAllProducts")
                .post().to("direct:createProduct")
                .delete().to("direct:deleteAllProducts");

        rest("/products/{id}")
                .get().to("direct:getProductById")
                .delete().to("direct:deleteProduct");

        rest("/products/addProducts")
                .post().to("direct:addProducts");

        from("direct:getAllProducts")
                .bean(ProductService.class, "getAllProducts");

        from("direct:getProductById")
                .bean(ProductService.class, "getProductById(${header.id})");

        from("direct:createProduct")
                .bean(ProductService.class, "createProduct");

        from("direct:addProducts")
                .bean(ProductService.class, "addProducts");

        from("direct:deleteProduct")
                .bean(ProductService.class, "deleteProduct(${header.id})");

        from("direct:deleteAllProducts")
                .bean(ProductService.class, "deleteAllProducts");
    }
}
