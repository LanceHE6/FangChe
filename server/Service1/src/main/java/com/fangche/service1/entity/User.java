package com.fangche.service1.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
@Table
@Setter
@Getter
public class User {
    @TableId("uid")
    private String uid;
    private String account;
    private String password;
    private String nickname;
    private String token;
    private int role;
    private Date createAt;
}
