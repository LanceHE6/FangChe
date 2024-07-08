package com.fangche.service2.Server;


import com.fangche.service2.Pojo.dto.Result;
import com.fangche.service2.Pojo.entity.Video;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

public interface VideoServer {
    Result videoList(String name, Long timeLength, String days);

    Result videoAdd(MultipartFile video, String name);

    Result updateById(Long id, String name);

    Result deleteById(Long id);

    Result deleteBatchIds(ArrayList<Integer> list);


    Result videoClick(Long id);
}
