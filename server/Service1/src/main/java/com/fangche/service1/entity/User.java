package com.fangche.service1.entity;

import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Table
@Setter
@Getter
public class User {
    private String uid;
    private String account;
    private String password;
    private String nickname;
    private  String email;
    private String token;
    private int role;
    private String createAt;
}
