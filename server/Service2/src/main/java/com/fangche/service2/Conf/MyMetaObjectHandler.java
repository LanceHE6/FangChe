package com.fangche.service2.Conf;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fangche.service2.Utility.SnowFlake;
import org.apache.ibatis.reflection.MetaObject;

public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        SnowFlake snowFlake = new SnowFlake(1, 1);
        Long id = snowFlake.nextId();
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("releaseTime",now,metaObject);
        this.setFieldValByName("updateTime",now,metaObject);
        this.setFieldValByName("id",id,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("updateTime",now,metaObject);
    }
}
