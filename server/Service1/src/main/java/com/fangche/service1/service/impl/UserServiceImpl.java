package com.fangche.service1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.User;
import com.fangche.service1.entity.VerifyCode;
import com.fangche.service1.mapper.UserMapper;
import com.fangche.service1.service.UserService;
import com.fangche.service1.service.VerifyCodeService;
import com.fangche.service1.utils.EmailTemplate;
import com.fangche.service1.utils.JWTUtil;
import com.fangche.service1.utils.RandomNumber;
import com.fangche.service1.utils.SaltMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private VerifyCodeService verifyCodeService;


    @Override
    public Response  getUserInfo(String uid) {
        User user =  userMapper.selectById(uid);
        Response response = new Response();
        response.setCode(200);
        response.setMsg("success");
        response.setData(user);
        return response;
    }

    @Override
    public Response register(String account, String password, String verifyCode) {
        // 验证验证码
        VerifyCode code = verifyCodeService.selectByAccount(account);
        if (code == null || code.isUsed() ||!code.getCode().equals(verifyCode)) {
            return new Response(400, "验证码错误", null);
        }
        // 获取当前时间
        Date now = new Date();

        // 计算过期时间（创建时间加上指定的分钟数） 5分钟过期
        long expiryTimeMillis = code.getCreatedAt().getTime() + (5 * 60 * 1000);
        Date expiryTime = new Date(expiryTimeMillis);

        // 如果当前时间大于或等于过期时间，那么验证码就过期了
        if (now.after(expiryTime) || now.equals(expiryTime)) {
            return new Response(401, "验证码已过期", null);
        }

        // 验证码验证通过，执行注册逻辑
        User user = new User();
        user.setAccount(account);
        // 密码进行md加盐加密
        user.setPassword(SaltMD5Util.generateSaltPassword(password));
        user.setNickname(account);
        userMapper.insert(user);

        // 设置验证码已使用
        code.setUsed(true);
        verifyCodeService.update(code);

//        System.out.println(code);
        Response response = new Response();
        response.setCode(200);
        response.setMsg("注册成功");
        return response;
    }

    @Override
    public Response sendRegisterVerifyCode(String account) {
        // 检验账号是否被注册
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", account));
        if (user != null) {
            return new Response(400, "账号已被注册", null);
        }
        //  生成验证码
        String code = RandomNumber.generateRandomNumber(6);

        VerifyCode verifyCode = new  VerifyCode();
        verifyCode.setAccount(account);
        verifyCode.setCode(code);
        verifyCode.setUsed(false);
        // 插入新的验证码,如果存在则更新
        verifyCodeService.insert(verifyCode);

        String subject = String.format("注册验证码: %S", code);
        int status = emailService.sendHTMLMailMessage(account, subject, EmailTemplate.getVerifyCodeTemplate(code));
        Response response = new Response();
        if (status != 0) {
            response.setCode(500);
            response.setMsg("发送验证码邮件失败");
            return response;
        }
        response.setCode(200);
        response.setMsg("发送验证码邮件成功");
        return response;
    }

    @Override
    public Response login(String account, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("account", account));
        if (!SaltMD5Util.verifySaltPassword(password, user.getPassword())) {
            return new Response(400, "账号或密码错误", null);
        }
        String token = JWTUtil.generateJwtToken(user);
        user.setToken(token);

        userMapper.updateById(user);

        Response response = new Response();
        response.setCode(200);
        response.setMsg("登录成功");
        response.setData(user);
        return response;
    }
}
