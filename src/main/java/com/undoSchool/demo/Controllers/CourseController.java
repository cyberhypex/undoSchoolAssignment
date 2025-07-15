package com.undoSchool.demo.Controllers;

import com.undoSchool.demo.Model.CourseDocument;
import com.undoSchool.demo.Repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/course")

public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/all")
    public Iterable<CourseDocument> getAllCourses() {
        return courseRepository.findAll();
    }

}
