package com.fangche.service2.Pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class VideoFileAddVo {
    private MultipartFile video;
    @NotBlank(message ="视频名称不能为空" )
    private String name;
}
