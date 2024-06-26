package com.fangche.service2.controller;

import com.fangche.service2.Pojo.dto.QuestionDto;
import com.fangche.service2.Pojo.dto.Result;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

@RequestMapping("/question")
@RestController
public class QuestionController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/add")
    public Result add(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()){
            return Result.error("请上传文件");
        }
        try {
            String str = new String(file.getBytes(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            QuestionDto[] questions = gson.fromJson(str, QuestionDto[].class);
            ArrayList<QuestionDto> list = new ArrayList<>(Arrays.asList(questions));
            mongoTemplate.insertAll(list);
        }catch (Exception e){
            return Result.error("传入的不是有效的json文件");
        }

        return Result.success();
    }



}
