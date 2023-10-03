package com.samhcoco.projects.healthapp.springcloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private static final String USER_SERVICE = "health-app-user-service";
    private static final int USER_SERVICE_PORT = 9000;

    private static final String HEALTH_SERVICE = "health-app-health-service";
    private static final String STORE_SERVICE = "health-app-store-service";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        // 'filters' rewrites path so that 'user-service/' path is not passed on to k8 'user-service' in routing requests
        return builder.routes()
                .route(USER_SERVICE, r -> r.path("/" + USER_SERVICE + "/**")
                        .filters(f -> f.rewritePath("/" + USER_SERVICE + "/(?<segment>.*)", "/$\\{segment}"))
                        .uri("http://" + USER_SERVICE + ":" + USER_SERVICE_PORT))

//                .route(SERVICE_2, r -> r.path("/" + SERVICE_2 + "/**")
//                        .filters(f -> f.rewritePath("/" + SERVICE_2 + "/(?<segment>.*)", "/$\\{segment}"))
//                        .uri("http://" + SERVICE_2 + ":" + SERVICE_2_PORT))
                .build();
    }

}
