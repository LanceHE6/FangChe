package com.fangche.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    // 配置网关路由
    @Bean
    public RouteLocator RouteLocator(RouteLocatorBuilder builder) {
        String service1URL = "http://127.0.0.1:8081";
        String service2URL = "http://127.0.0.1:8082";

        return builder.routes()
                .route("user_routes", r -> r.path("/api/user/**").uri(service1URL))
                .route("video_routes", r -> r.path("/api/video/**").uri(service2URL))
                .build();
    }

}
