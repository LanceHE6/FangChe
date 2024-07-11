package com.fangche.service2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class file {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void loadFile(){

    }
}
