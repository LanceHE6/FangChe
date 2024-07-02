package com.fangche.service2.Pojo.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.tangzc.mpe.autotable.annotation.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Table
@Setter
@Getter
public class Video {
    private Long id;
    private String name;
    private String url;
    private Long timeLength;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime releaseTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
