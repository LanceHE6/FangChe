package com.fangche.service1.controller;

import com.fangche.service1.entity.Response;
import com.fangche.service1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService UserService;
    @GetMapping("/get/{uid}")
    public Response getUserInfo(@PathVariable("uid") String uid) {
        return UserService.getUserInfo(uid); // 调用UserService的getUserInfo方法
    }
}
