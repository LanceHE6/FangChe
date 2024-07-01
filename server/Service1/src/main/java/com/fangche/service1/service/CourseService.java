package com.fangche.service1.service;

import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.requestParam.course.CourseAddParam;
import com.fangche.service1.entity.requestParam.course.CourseSearchParam;

public interface CourseService {
    Response addCourse(CourseAddParam param);
    Response searchCourse(CourseSearchParam param);
}
