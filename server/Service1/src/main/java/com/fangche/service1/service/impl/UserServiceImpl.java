package com.fangche.service1.service.impl;

import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.User;
import com.fangche.service1.mapper.UserMapper;
import com.fangche.service1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Response  getUserInfo(String uid) {
        User user =  userMapper.getUserInfo(uid);
        Response response = new Response();
        response.setCode(200);
        response.setMsg("success");
        response.setData(user);
        return response;
    }
}

