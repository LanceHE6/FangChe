package com.fangche.service1.mapper;

import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserInfo(String uid);
}
