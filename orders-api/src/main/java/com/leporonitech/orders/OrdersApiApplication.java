package com.leporonitech.orders;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrdersApiApplication {

	@Autowired
	private CamelContext context;

	public static void main(String[] args) {
		SpringApplication.run(OrdersApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			ProducerTemplate template = context.createProducerTemplate();
			template.sendBody("direct:start", null);
		};
	}
}