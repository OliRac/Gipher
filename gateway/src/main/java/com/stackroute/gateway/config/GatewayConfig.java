package com.stackroute.gateway.config;

import com.stackroute.gateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes().route(  r -> r.path("/auth/**")
                                        .filters(f -> f.filter(filter)).uri("http://userservice:9999"))
                                .route( r -> r.path("/favorite-service/**")
                                        .filters(f -> f.filter(filter)).uri("http://favoriteservice:8080"))
                                .build();
    }

}