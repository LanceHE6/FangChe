package com.fangche.service2;

import com.fangche.service2.Pojo.entity.Option;
import com.fangche.service2.Pojo.entity.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@SpringBootTest
public class file {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void loadFile(){
        Question q1 = new Question();
        q1.setId("1");
        q1.setText("什么是Java?");
        q1.setType("TestPaper1");
        q1.setOptions(Arrays.asList(
                new Option("A. 一种编程语言", true),
                new Option("B. 一种操作系统", false),
                new Option("C. 一种数据库系统", false),
                new Option("D. 一种游戏", false)
        ));
        q1.setCorrectAnswer("A");

        Question q2 = new Question();
        q2.setId("2");
        q2.setText("在Java中，以下哪个是合法的标识符?");
        q2.setType("TestPaper1");
        q2.setOptions(Arrays.asList(
                new Option("A. 123variable", false),
                new Option("B. variable123", true),
                new Option("C. #variable", false),
                new Option("D. variable-name", false)
        ));
        q2.setCorrectAnswer("B");

        System.out.println(q1);
        System.out.println(q2);
    }

}
