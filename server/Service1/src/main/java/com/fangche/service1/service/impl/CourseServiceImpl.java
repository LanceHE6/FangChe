package com.fangche.service1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fangche.service1.entity.Course;
import com.fangche.service1.entity.CourseCollection;
import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.User;
import com.fangche.service1.entity.requestParam.course.CourseAddParam;
import com.fangche.service1.entity.requestParam.course.CourseSearchParam;
import com.fangche.service1.entity.requestParam.course.CourseSetImageParam;
import com.fangche.service1.mapper.CourseCollectionMapper;
import com.fangche.service1.mapper.CourseMapper;
import com.fangche.service1.mapper.UserMapper;
import com.fangche.service1.service.CourseService;
import com.fangche.service1.utils.StaticResourcesUtil;
import com.fangche.service1.utils.UserUtil;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseCollectionMapper courseCollectionMapper;
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
        if (param.getId()!= null){
            queryWrapper.eq("id", param.getId());
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
    @Override
    public Response collectCourse(Long cid, HttpServletRequest request){
        Long uid = UserUtil.getUserIdByRequest(request);
        // 判断collection表中是否有该用户的记录, 没有的话就插入一条,有就更新
        QueryWrapper<CourseCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        CourseCollection  courseCollection = courseCollectionMapper.selectOne(queryWrapper);
        if (courseCollection == null){
            CourseCollection newCollection = new CourseCollection();
            newCollection.setUid(uid);
            Gson gson = new Gson();
            // 将cid插入数组中并转化为json字符串
            List<Long> courses = new ArrayList<>();
            courses.add(cid);
            String coursesJson = gson.toJson(courses);
            newCollection.setCourses(coursesJson);
            courseCollectionMapper.insert(newCollection);
            return new Response(200, "收藏成功", null);
        }
        // 更新操作
        // 先将json字符串转为list
        Gson gson = new Gson();
        List<Long> courses = gson.fromJson(courseCollection.getCourses(), new TypeToken<List<Long>>(){}.getType());
        // 判断cid是否在list中, 不在就添加
        if (!courses.contains(cid)){
            courses.add(cid);
            String coursesJson = gson.toJson(courses);
            courseCollection.setCourses(coursesJson);
            courseCollectionMapper.updateById(courseCollection);
            return new Response(200, "收藏成功", null);
        } else return new Response(400, "已收藏该课程", null);

    }
    @Override
    public Response removeCollectCourse(Long cid, HttpServletRequest request){
        Long uid = UserUtil.getUserIdByRequest(request);
        // 判断collection表中是否有该用户的记录, 没有的话直接返回错误,有就更新
        QueryWrapper<CourseCollection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        CourseCollection  courseCollection = courseCollectionMapper.selectOne(queryWrapper);
        if (courseCollection == null){
            return new Response(400, "未收藏该课程", null);
        }
        // 更新操作
        // 先将json字符串转为list
        Gson gson = new Gson();
        List<Long> courses = gson.fromJson(courseCollection.getCourses(), new TypeToken<List<Long>>(){}.getType());
        // 判断cid是否在list中, 不在就返回错误消息
        if (!courses.contains(cid)){
            return new Response(400, "未收藏该课程", null);
        } else {
            courses.remove(cid);
            String coursesJson = gson.toJson(courses);
            courseCollection.setCourses(coursesJson);
            courseCollectionMapper.updateById(courseCollection);
            return new Response(200, "成功取消收藏该课程", null);
        }

    }

    @Override
    public Response setCourseImage(CourseSetImageParam param) {
        MultipartFile image = param.getImage();
        if (image.isEmpty()) {
            return new Response(400, "未选择文件", null);
        }

        try {
            File filePath = StaticResourcesUtil.getCourseImagePath(image.getOriginalFilename());
            image.transferTo(filePath);
        } catch (IOException e) {
            return new Response(500, "上传失败", e.getMessage());
        }

        Long id = param.getId();

        // 更新数据库
        Course course = courseMapper.selectOne(new QueryWrapper<Course>().eq("id", id));
        course.setImage(StaticResourcesUtil.COURSE_IMAGE_UPLOAD_DIR + image.getOriginalFilename());
        courseMapper.updateById(course);

        HashMap<String, String> data = new HashMap<>();
        data.put("id", String.valueOf(id));
        data.put("image_name", image.getOriginalFilename());
        data.put("path", StaticResourcesUtil.USER_AVATAR_UPLOAD_DIR + image.getOriginalFilename());

        return new Response(200, "上传成功", data);
    }
}

