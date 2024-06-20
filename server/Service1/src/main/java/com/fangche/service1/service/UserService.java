package com.fangche.service1.service;

import com.fangche.service1.entity.Response;
import jakarta.annotation.Resource;

public interface UserService {
    Response getUserInfo(String uid);
}
