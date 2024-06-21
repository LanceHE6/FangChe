package com.fangche.service2.controller;

import com.fangche.service2.Mapper.VideoMapper;
import com.fangche.service2.Pojo.dto.Result;
import com.fangche.service2.Pojo.entity.Video;
import com.fangche.service2.Server.Imp.VideoServerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController("/video")
public class VideoController {
    @Autowired
    private VideoServerImp videoServer;
    @Autowired
    private VideoMapper videoMapper;

    //videoList是根据条件查询，如：发布时间查询，视频名称查询，视频时长
    @GetMapping("/list")
    public Result videoList(@RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "timeLength", defaultValue = "-1") Long timeLength,
                            @RequestParam(value = "days",required = false) String days) {
        List<Video> videos = videoServer.videoList(name, timeLength, days);
        return Result.success(videos);
    }
    @PostMapping("/add")
    public Result videoAdd(@RequestParam("video") MultipartFile video, @RequestParam("name") String name) {
        if (video.isEmpty()) {
            return Result.error("Please select a file to upload");
        }
        boolean flag = videoServer.videoAdd(video, name);
        if (flag) {
            return Result.success();
        }
        return Result.error("插入失败");
    }

    @PutMapping("/put")
    public Result videoAlterById(@RequestParam("id") String id, @RequestParam("name") String name) {
        Boolean flag = videoServer.updateById(id, name);
        if (flag) {
            return Result.success();
        }
        return Result.error("更新失败");
    }

    @DeleteMapping("/delete")
    public Result deleteById(String id) {
        int flag = videoMapper.deleteById(id);
        if (flag == 1) {
            return Result.success();
        }
        return Result.error("删除失败");
    }

    @DeleteMapping("/deleteBach")
    public Result deleteBach(ArrayList<Integer> list) {
        int flag = videoMapper.deleteBatchIds(list);
        if (flag == 1) {
            return Result.success();
        }
        return Result.error("删除失败");
    }
}
