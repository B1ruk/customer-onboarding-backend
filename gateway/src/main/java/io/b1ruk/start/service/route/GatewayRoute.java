package io.b1ruk.start.service.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoute {

    @Bean
    public RouteLocator customLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("route_to_customer_ms", r -> r.path("/onboarding/**").uri("lb://customer-ms"))
                .build();
    }
}
