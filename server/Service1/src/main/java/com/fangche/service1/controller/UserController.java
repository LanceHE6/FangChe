package com.fangche.service1.controller;

import com.fangche.service1.entity.Response;
import com.fangche.service1.service.UserService;
import com.fangche.service1.utils.authority.Authority;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/get/{uid}")
    public Response getUserInfo(@PathVariable("uid") String uid) {
        return userService.getUserInfo(uid); // 调用UserService的getUserInfo方法
    }
    @GetMapping("/info")
    public Response info(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        return userService.info(auth);
    }
    @PostMapping("/register/send-code")
    public Response sendRegisterCode(@RequestParam("account") String account) {
        return userService.sendRegisterVerifyCode(account);
    }
    @PostMapping("/register/verify-code")
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
    @PostMapping("/set-avatar")
    @Authority
    public Response setAvatar(@RequestParam("uid") Long uid,
                              @RequestParam("file") MultipartFile file) throws IOException {
        return userService.setAvatar(uid, file);
    }
    @PutMapping("/update")
    @Authority
    public Response update(@RequestParam("uid") Long uid,
                           @RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
                           @RequestParam(value = "gender", required = false, defaultValue = "-2") Integer gender,
                           @RequestParam(value = "signature", required = false, defaultValue = "") String signature,
                           @RequestParam(value = "introduction", required = false, defaultValue = "") String introduction) {
        return userService.updateUser(uid, nickname, gender, signature, introduction);
    }

    @PostMapping("/reset-password/send-code")
    public Response sendResetPasswordCode(@RequestParam("account") String account) {
        return userService.sendResetPswVerifyCode(account);
    }
    @PostMapping("/reset-password/verify-code")
    public Response verifyResetPasswordCode(@RequestParam("account") String account,
                                            @RequestParam("verify_code") String verifyCode,
                                            @RequestParam("new_password") String newPassword) {
        return userService.verifyResetPsw(account, newPassword, verifyCode);
    }
}
