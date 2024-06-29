package com.fangche.service2.Pojo.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Data
public class VideoFileAddVo {

    MultipartFile video;
    String name;
}
