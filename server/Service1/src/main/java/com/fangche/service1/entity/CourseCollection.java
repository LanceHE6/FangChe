package com.fangche.service1.entity;

import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Table
@Data
public class CourseCollection extends BaseEntity{
    private  Long uid;
    @Column(length = 2048)
    private  String courses;
}
