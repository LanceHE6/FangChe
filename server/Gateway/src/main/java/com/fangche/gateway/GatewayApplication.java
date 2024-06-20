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
    public RouteLocator service1RouteLocator(RouteLocatorBuilder builder) {
        String service1URL = "http://127.0.0.1:8081";
        RouteLocatorBuilder.Builder routes = builder.routes();
        routes.route("hello1", r -> r.path("/hello1").uri(service1URL));
        routes.route("user/get/{uid}", r -> r.path("/user/get/{uid}").uri(service1URL));
        routes.route("user/register/sendCode", r -> r.path("/user/register/sendCode").uri(service1URL));

        return routes.build();
    }
    @Bean
    public RouteLocator service2RouteLocator(RouteLocatorBuilder builder) {
        return builder.routes().route("hello2", r -> r.path("/hello2").uri("http://127.0.0.1:8082")).build();
    }
}
