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

import java.util.Date;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 基础的token校验
     * @param request 请求对象
     * @return int 1为通过 -1为不通过 0为token已过期
     */
    @Override
    public int baseAuthenticate(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        String token;
        try {
            String bearer = (authorization.split(" "))[0];
            if (!Objects.equals(bearer, "bearer")){
                return -1;
            }
            token = (authorization.split(" "))[1];
        } catch (Exception e) {
            return -1;
        }
        Claims claims = JWTUtil.getClaimsFromJwt(token);
        if (claims == null){
            return -1;
        }
        Long id = (Long) claims.get("id");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id).eq("token", token);
        User user = this.userMapper.selectOne(wrapper);
        if (user == null) {
            return -1;
        }
        // 判断token是否过期
        Date created_at = (Date) claims.get("created_at");
        Date now = new Date();
        // 计算两个时间之间的差值（毫秒）
        long diff = now.getTime() - created_at.getTime();

        // 判断是否超过3天（3天 = 3 * 24 * 60 * 60 * 1000毫秒）
        if (diff > 259200000) {
            return 0;
        }
        return 1;
    }

    /**
     * 判断用户权限是否大于等于发布者
     * @param request 请求对象
     * @return Boolean
     */
    @Override
    public boolean isAuthorAuthenticate(HttpServletRequest request) {
        if (baseAuthenticate(request) != 1){
            User user = getUser(request);
            return user != null && user.getRole() > 1;
        }
        return false;
    }

    /**
     * 判断用户权限是否为管理员
     * @param request 请求对象
     * @return Boolean
     */
    @Override
    public boolean isAdminAuthenticate(HttpServletRequest request){
        if (baseAuthenticate(request) != 1){
            User user = getUser(request);
            return user != null && user.getRole() == 3;
        }
        return false;
    }

    /**
     * 通过header头的token获取用户对象
     * @param request 请求对象
     * @return User对象
     */
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