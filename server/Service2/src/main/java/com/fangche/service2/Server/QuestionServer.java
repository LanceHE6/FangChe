package com.fangche.service2.Server;

import com.fangche.service2.Pojo.dto.Result;
import com.fangche.service2.Pojo.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface QuestionServer {
     Result addFile(MultipartFile file);

    List<Question> selectByType(String type);

    void deleteByType(String type);

    void deleteById(String id);
}
