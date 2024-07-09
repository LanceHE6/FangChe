package com.fangche.service1.entity;

import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Table
@Data
public class Course extends BaseEntity{
    private String name; // 课程名
    @Column(length = 2048)
    private String introduction; // 课程简介
    @Column(length = 2048)
    private String chapters; // 课程目录/章节
    private String resource; //  课程资源
    private Long teacherId; //  教师ID
    private String category; //  课程分类
    private String image; //   课程图片
    private int visits; //  访问量
}
