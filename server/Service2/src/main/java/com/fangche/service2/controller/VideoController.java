package com.fangche.service2.controller;


import com.fangche.service2.Pojo.dto.Result;
import com.fangche.service2.Server.Imp.VideoServerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RequestMapping("/video")
@RestController
public class VideoController {
    @Autowired
    private VideoServerImp videoServer;


    //videoList是根据条件查询，如：发布时间查询，视频名称查询，视频时长
    @GetMapping("/list")
    public Result videoList(@RequestParam(value = "name",required = false) String name,
                            @RequestParam(value = "timeLength", defaultValue = "-1") Long timeLength,
                            @RequestParam(value = "days",required = false) String days) {
        return videoServer.videoList(name, timeLength, days);
    }

    @PostMapping("/add")
    public Result videoAdd(@RequestParam("video") MultipartFile video, @RequestParam("name") String name) {
        return videoServer.videoAdd(video, name);
    }

    @PutMapping("/put")
    public Result videoAlterById(@RequestParam("id") Long id, @RequestParam("name") String name) {
        return videoServer.updateById(id, name);
    }

    @DeleteMapping("/delete")
    public Result deleteById(Long id) {
        return videoServer.deleteById(id);
    }

    @DeleteMapping("/deleteBach")
    public Result deleteBach(ArrayList<Integer> list) {
        return videoServer.deleteBatchIds(list);
    }
}
