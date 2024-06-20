package com.fangche.service1.service.impl;

import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.User;
import com.fangche.service1.entity.VerifyCode;
import com.fangche.service1.mapper.UserMapper;
import com.fangche.service1.service.UserService;
import com.fangche.service1.service.VerifyCodeService;
import com.fangche.service1.utils.EmailTemplate;
import com.fangche.service1.utils.RandomNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Response response = new Response();
        response.setCode(200);
        response.setMsg("注册成功");
        return response;
    }

    @Override
    public Response sendRegisterVerifyCode(String account) {
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
            response.setCode(400);
            response.setMsg("发送验证码邮件失败");
            return response;
        }
        response.setCode(200);
        response.setMsg("发送验证码邮件成功");
        return response;
    }
}
