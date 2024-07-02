package com.fangche.service1.service;

import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.requestParam.course.CourseAddParam;
import com.fangche.service1.entity.requestParam.course.CourseSearchParam;
import jakarta.servlet.http.HttpServletRequest;

public interface CourseService {
    Response addCourse(CourseAddParam param);
    Response searchCourse(CourseSearchParam param);
    Response deleteCourse(Long cid);
    Response collectCourse(Long cid, HttpServletRequest request);
    Response removeCollectCourse(Long cid, HttpServletRequest request);
}
