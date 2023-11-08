package com.ebenz.shoppingcartui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ShoppingcartUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingcartUiApplication.class, args);
	}

	@Bean
	RouteLocator routeBuilder(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route("catalog-service", r->r.path("/api/products/**")
						.uri("http://localhost:8181"))
				.route("inventory-service", r->r.path("/api/inventory/**")
						.uri("http://localhost://8282"))
				.route("order-service", r->r.path("/api/orders/**")
						.uri("http://localhost://8383")).build();
	}
}
