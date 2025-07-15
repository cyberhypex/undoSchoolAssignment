package com.undoSchool.demo.Controllers;

import com.undoSchool.demo.Model.CourseDocument;
import com.undoSchool.demo.Repositories.CourseRepository;
import com.undoSchool.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/course")

public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/keyword")
    public List<CourseDocument> searchWithKeyWord(@RequestParam String keyword){
        return courseService.searchCoursesWithKeyWord(keyword);
    }

}
