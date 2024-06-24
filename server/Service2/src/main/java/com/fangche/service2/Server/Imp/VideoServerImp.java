package com.fangche.service2.Server.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fangche.service2.Mapper.VideoMapper;

import com.fangche.service2.Pojo.entity.Video;
import com.fangche.service2.Server.VideoServer;


import com.fangche.service2.Utility.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

import static com.fangche.service2.Utility.VideoUtils.getVideoDurationInSeconds;

@Service
public class VideoServerImp implements VideoServer {

    private static final String UPLOAD_DIR="D:\\VideoFile";

    @Autowired
    private VideoMapper videoMapper;



    //根据视频发布时间，视频长度，视频名称来动态查询视频
    @Override
    public List<Video> videoList(String name ,Long timeLength,String days){
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();

        if (name!=null && !name.equals("")){
            queryWrapper = queryWrapper.like("name", name);
        }
        if (timeLength!=-1){
            queryWrapper = queryWrapper.le("time_length",timeLength);
        }
        if (days!=null && !days.equals("")){
            LocalDateTime now = LocalDateTime.now();
            int day = Integer.parseInt(days);
            LocalDateTime min = now.minusDays(day);
            queryWrapper.between("release_time",min,now);
        }

        return videoMapper.selectList(queryWrapper);
    }

    @Override
    public boolean videoAdd(MultipartFile video,String name) {

        String fileName = UUID.randomUUID() + ".mp4";
        File dest = new File(UPLOAD_DIR + File.separator + fileName);
        String url = dest.getAbsolutePath();
        String durationInSeconds;
        long length;
        try {
            video.transferTo(dest);
            durationInSeconds= String.valueOf(getVideoDurationInSeconds(dest.getAbsolutePath()));
            int dotIndex = durationInSeconds.indexOf('.');
            // 去掉小数点及其后面的部分
            String integerStr = dotIndex != -1 ? durationInSeconds.substring(0, dotIndex) : durationInSeconds;
            // 转换回整数（注意可能会抛出NumberFormatException，需要处理）
             length = Long.parseLong(integerStr);


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        SnowFlake snowFlake = new SnowFlake(1, 1);
        LocalDateTime dateTime = LocalDateTime.now();
        Long id = snowFlake.nextId();
        Video v = new Video();
        v.setUrl(url);
        v.setName(name);
        v.setReleaseTime(dateTime);
        v.setUpdateTime(dateTime);
        v.setId(id);
        v.setTimeLength(length);
        int flag = videoMapper.insert(v);
        return flag == 1;
    }

    @Override
    public Boolean updateById(Long id, String name) {
        Video video = new Video();
        LocalDateTime dateTime = LocalDateTime.now();
        video.setUpdateTime(dateTime);
        video.setId(id);
        video.setName(name);
        int i = videoMapper.updateById(video);
        return i==1;
    }


}
