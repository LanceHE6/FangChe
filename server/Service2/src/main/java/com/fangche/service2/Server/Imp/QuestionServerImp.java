package com.fangche.service2.Server.Imp;

import com.fangche.service2.Pojo.dto.QuestionDto;
import com.fangche.service2.Pojo.entity.Question;
import com.fangche.service2.Server.QuestionServer;
import com.google.gson.Gson;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServerImp implements QuestionServer {
    @Autowired
    private MongoTemplate mongoTemplate;

    public  Boolean addFile(MultipartFile file) {
        try {
            String str = new String(file.getBytes(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            QuestionDto[] questions = gson.fromJson(str, QuestionDto[].class);
            ArrayList<QuestionDto> list = new ArrayList<>(Arrays.asList(questions));
            mongoTemplate.insertAll(list);

        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public List<Question> selectByType(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        return mongoTemplate.find(query, Question.class);
    }

    @Override
    public void deleteByType(String type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("type").is(type));
        DeleteResult test = mongoTemplate.remove(query, "Question");
        System.out.println(test);
    }

    @Override
    public void deleteById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        DeleteResult test = mongoTemplate.remove(query, "Question");
        System.out.println(test);

    }
}
