package com.fangche.service1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello1")
    public  String hello() {
        return "Hello from service1!";
    }
}
