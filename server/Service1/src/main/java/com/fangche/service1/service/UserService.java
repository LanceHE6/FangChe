package com.fangche.service1.service;

import com.fangche.service1.entity.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    Response getUserInfo(String uid);
    Response info(String authorization);
    Response register(String account, String password, String verifyCode);
    Response sendRegisterVerifyCode(String account);
    Response login(String account, String password);
    Response setAvatar(Long uid, MultipartFile file) throws IOException;
}
