package com.fangche.service1.controller;

import com.fangche.service1.entity.Response;
import com.fangche.service1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/get/{uid}")
    public Response getUserInfo(@PathVariable("uid") String uid) {
        return userService.getUserInfo(uid); // 调用UserService的getUserInfo方法
    }

    @PostMapping("/register/sendCode")
    public Response sendRegisterCode(@RequestParam("account") String account) {
        return userService.sendRegisterVerifyCode(account);
    }
    @PostMapping("/register/verifyCode")
    public Response verifyRegisterCode(@RequestParam("account") String account,
                                       @RequestParam("verify_code") String verifyCode,
                                       @RequestParam("password") String password) {
        return userService.register(account, password, verifyCode);
    }
    @PostMapping("/login")
    public Response login(@RequestParam("account") String account,
                          @RequestParam("password") String password) {
        return userService.login(account, password);
    }
}
