package com.fangche.service1.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response {
    private int code;
    private String msg;
    private Object data;
}
