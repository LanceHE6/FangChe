package com.fangche.service1.controller;

import com.fangche.service1.entity.Response;
import com.fangche.service1.entity.requestParam.course.CourseAddParam;
import com.fangche.service1.entity.requestParam.course.CourseSearchParam;
import com.fangche.service1.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService CourseService;
    @PostMapping("/add")
    public Response addCourse(@Valid @RequestBody CourseAddParam param) {
        return CourseService.addCourse(param);
    }

    @GetMapping("/search")
    public Response searchCourse(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                 @RequestParam(value = "page_size", required = false, defaultValue = "10") int page_size,
                                 @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                 @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                 @RequestParam(value = "teacher", required = false, defaultValue = "") String teacher) {
        CourseSearchParam param = new CourseSearchParam();
        param.setKeyword(keyword);
        param.setPage(page);
        param.setPageSize(page_size);
        param.setCategory(category);
        param.setName(name);
        param.setTeacher(teacher);
        return CourseService.searchCourse(param);
    }
}
