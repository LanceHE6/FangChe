package com.fangche.service2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class Mongo {
    @Autowired
    MongoTemplate mongoTemplate;


//    集合的操作
    @Test
    void mongotest(){

        if (mongoTemplate.collectionExists("test")){
            mongoTemplate.dropCollection("test");
        }
        mongoTemplate.createCollection("question");

    }
}
