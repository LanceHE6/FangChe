package com.fangche.service1.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table
@Setter
@Getter
public class User extends BaseEntity{
    private String account; // 账号(邮箱)
    private String password;
    private String nickname; // 昵称
    private String avatar; // 头像
    private int sex; // 性别 0为女 1为男 -1为保密
    @Column(length = 512)
    private String signature; // 个性签名
    @Column(length = 512)
    private String token;

    @TableField(fill = FieldFill.INSERT)
    private int role; // 权限

    @Column(length = 1024)
    private String introduction; // 简介

}
