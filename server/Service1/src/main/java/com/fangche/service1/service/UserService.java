package com.fangche.service1.service;

import com.fangche.service1.entity.Response;

public interface UserService {
    Response getUserInfo(String uid);
    Response register(String account, String password, String verifyCode);
    Response sendRegisterVerifyCode(String account);
    Response login(String account, String password);
}
