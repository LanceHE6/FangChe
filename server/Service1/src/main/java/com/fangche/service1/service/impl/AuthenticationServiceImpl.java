package com.fangche.service1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangche.service1.entity.User;
import com.fangche.service1.mapper.UserMapper;
import com.fangche.service1.service.AuthenticationService;
import com.fangche.service1.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean baseAuthenticate(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String token;
        try {
            token = (authorization.split(" "))[1];
        } catch (Exception e) {
            return false;
        }
        Claims claims = JWTUtil.getClaimsFromJwt(token);
        if (claims == null){
            return false;
        }
        Long id = (Long) claims.get("id");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("token", token);
        User user = this.userMapper.selectOne(wrapper);
        return user != null;
    }
    @Override
    public boolean isAuthorAuthenticate(HttpServletRequest request) {
        if (baseAuthenticate(request)){
            User user = getUser(request);
            return user != null && user.getRole() > 1;
        }
        return false;
    }
    @Override
    public boolean isAdminAuthenticate(HttpServletRequest request){
        if (baseAuthenticate(request)){
            User user = getUser(request);
            return user != null && user.getRole() == 3;
        }
        return false;
    }
    private User getUser(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        String token;
        try {
            token = (authorization.split(" "))[1];
        } catch (Exception e) {
            return null;
        }
        Claims claims = JWTUtil.getClaimsFromJwt(token);
        if (claims == null){
            return null;
        }
        Long id = (Long) claims.get("id");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("token", token);
        return this.userMapper.selectOne(wrapper);
    }
}
