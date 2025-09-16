package com.ride.wave.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

  @Bean
  public RouteLocator customRouteLocator(final RouteLocatorBuilder builder) {

    return builder
        .routes()
        .route("driver-service", r -> r.path("/api/drivers/**").uri("lb://DRIVER-SERVICE"))
        .route(
            "rider-service",
            r -> r.path("/api/riders/**", "/api/ride-requests/**").uri("lb://RIDER-SERVICE"))
        .route("trip-service", r -> r.path("/api/trips/**").uri("lb://TRIP-SERVICE"))
        .route(
            "eureka-server",
            r ->
                r.path("/eureka/main")
                    .filters(f -> f.rewritePath("/eureka/main", "/"))
                    .uri("http://localhost:8761"))
        .route("eureka-server-static", r -> r.path("/eureka/**").uri("http://localhost:8761"))
        .build();
  }
}
