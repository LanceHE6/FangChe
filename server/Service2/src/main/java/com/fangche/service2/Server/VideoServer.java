package com.fangche.service2.Server;


import com.fangche.service2.Pojo.entity.Video;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface VideoServer {
    public List<Video> videoList(String name ,Long timeLength,String days);
    public boolean videoAdd(MultipartFile video, String name);

    Boolean updateById(Long id, String name);
}
