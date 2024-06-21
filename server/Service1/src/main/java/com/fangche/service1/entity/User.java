package com.fangche.service1.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Getter;
import lombok.Setter;

@Table
@Setter
@Getter
public class User extends BaseEntity{
    private String account;
    private String password;
    private String nickname;
    private String avatar;
    private String token;

    @TableField(fill = FieldFill.INSERT)
    private int role;
}
