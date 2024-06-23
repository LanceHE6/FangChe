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
        routes.route("api/user/get/{uid}", r -> r.path("/api/user/get/{uid}").uri(service1URL));
        routes.route("api/user/register/sendCode", r -> r.path("/api/user/register/sendCode").uri(service1URL));
        routes.route("api/user/register/verifyCode", r -> r.path("/api/user/register/verifyCode").uri(service1URL));
        routes.route("api/user/login", r -> r.path("/api/user/login").uri(service1URL));

        String service2URL = "http://127.0.0.1:8082";

        routes.route("api/video/list", r -> r.path("/api/video/list").uri(service2URL));
        routes.route("api/video/add", r -> r.path("/api/video/add").uri(service2URL));
        routes.route("api/video/put", r -> r.path("/api/video/put").uri(service2URL));
        routes.route("api/video/delete", r -> r.path("/api/video/delete").uri(service2URL));
        routes.route("api/video/deleteBach", r -> r.path("/api/video/deleteBach").uri(service2URL));
        return routes.build();
    }

}
