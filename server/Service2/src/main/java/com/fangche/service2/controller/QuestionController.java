package com.fangche.service2.controller;

import com.fangche.service2.Pojo.dto.Result;
import com.fangche.service2.Pojo.entity.Question;
import com.fangche.service2.Server.Imp.QuestionServerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RequestMapping("/question")
@RestController
public class QuestionController {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private QuestionServerImp questionServerImp;
    //添加题目
    @PostMapping("/add")
    public Result add(@RequestParam("file")MultipartFile file) {
        return questionServerImp.addFile(file);
    }

    @GetMapping("/searchTypes")
    public Result searchTypes(){
        List<String> types = mongoTemplate.findDistinct(new Query(), "type", "Question", String.class);
        return Result.success(types);
    }

    @GetMapping("/searchQuestion")
    public Result searchByType(@RequestParam("type") String type){
        return questionServerImp.selectByType(type);
    }
    //删除套题
    @DeleteMapping("/deleteByType")
    public Result DeleteQuestions(@RequestParam("type") String type){
        return questionServerImp.deleteByType(type);
    }
    //删除某一道题
    @DeleteMapping("/deleteById")
    public Result DeleteQuestion(@RequestParam("id") String id){
        return questionServerImp.deleteById(id);
    }


}
