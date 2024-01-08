package com.leporonitech.payments.camel;

import com.leporonitech.payments.config.PaymentLineGenerator;
import com.leporonitech.payments.model.Payment;
import com.leporonitech.products.model.Product;
import org.apache.camel.builder.RouteBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;


public class PaymentRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .to("http://localhost:8087/products")
                .process(exchange -> {
                    List<Product> products = exchange.getIn().getBody(List.class);
                    Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

                    Payment payment = new Payment();
                    payment.setId(UUID.randomUUID());
                    payment.setGenerationDate(LocalDate.now());
                    payment.setDueDate(LocalDate.now().plusDays(30));

                    Product lastProduct = products.get(products.size() - 1);
                    payment.setProductName(lastProduct.getName());
                    payment.setProductPrice(lastProduct.getPrice());

                    payment.setPaymentLine(PaymentLineGenerator.generatePaymentLine(payment));

                    exchange.getIn().setBody(payment);
                })
                .to("direct:payment-line");

        from("direct:payment-line")
                .process(exchange -> {
                    // Set the payment line
                    exchange.getIn().setHeader("paymentLine", exchange.getIn().getBody(Payment.class).getPaymentLine());
                })
                .to("http://localhost:8088/payments");
    }
}
