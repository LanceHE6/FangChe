package com.fangche.service1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangche.service1.entity.Course;
import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.User;
import com.fangche.service1.entity.requestParam.course.CourseAddParam;
import com.fangche.service1.entity.requestParam.course.CourseSearchParam;
import com.fangche.service1.mapper.CourseMapper;
import com.fangche.service1.mapper.UserMapper;
import com.fangche.service1.service.CourseService;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Response addCourse(CourseAddParam param) {
        Course newCourse = new Course();
        newCourse.setName(param.getName());
        newCourse.setIntroduction(param.getIntroduction());
        newCourse.setResource(param.getResources());
        newCourse.setTeacherId(param.getTeacher_id());
        newCourse.setCategory(param.getCategory());

        Gson gson = new Gson();
        // 将Chapter对象的列表转换为JSON字符串
        String chaptersJson = gson.toJson(param.getChapters());
        newCourse.setChapters(chaptersJson);  // 存储JSON字符串

        courseMapper.insert(newCourse);
        return new Response(200, "课程添加成功", null);
    }

    @Override
    public Response searchCourse(CourseSearchParam param){
        if (param.getPage() < 1) {
            param.setPage(1);
        }
        if (param.getPageSize() < 1){
            param.setPageSize(10);
        }
        Page<Course> page = new Page<>(param.getPage(), param.getPageSize());

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        if  (!Objects.equals(param.getName(), "")) {
            queryWrapper.like("name", param.getName());
        }

        if  (!Objects.equals(param.getCategory(), "")) {
            queryWrapper.like("category", param.getCategory());
        }
        if (!Objects.equals(param.getTeacher(), "")){
            // 查询教师名称为param.getTeacher()的教师id
            try{
                queryWrapper.like("teacher_id", userMapper.selectOne(new QueryWrapper<User>().like("nickname", param.getTeacher())).getId());
            } catch (Exception e){
                return new Response(400, "教师不存在", null);
            }
        }
        // 查询课程名称为param.getKeyword()或课程简介包含param.getKeyword()或课程章节包含param.getKeyword()的课程
        if (param.getKeyword()!= null){
            queryWrapper.and(wq-> wq.like("name", param.getKeyword()).or()
                    .like("introduction", param.getKeyword()).or()
                    .like("chapters",param.getKeyword()));
        }

        Page<Course> courseIPage = courseMapper.selectPage(page, queryWrapper);
        List<Course> courseList = courseIPage.getRecords();

        for (Course course : courseList) {
            System.out.println(course.getName());
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("rows",courseList);
        data.put("total",courseIPage.getTotal());
        data.put("page",param.getPage());
        data.put("page_size", param.getPageSize());
        return new Response(200, "查询成功", data);
    }

    @Override
    public Response deleteCourse(Long id) {
        Course course = courseMapper.selectById(id);
        if (course == null){
            return new Response(400, "课程不存在", null);
        }
        courseMapper.deleteById(id);
        return new Response(200, "删除成功", null);
    }
}
