package com.undoSchool.demo.Controllers;

import com.undoSchool.demo.Model.CourseDocument;
import com.undoSchool.demo.Repositories.CourseRepository;
import com.undoSchool.demo.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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

    @GetMapping("/condition")
    public List<CourseDocument> searchByMinAndMaxAge(@RequestParam Integer minAge,
                                                     @RequestParam Integer maxAge
                                                     ){
        return courseService.searchCriteria(minAge,maxAge);
    }
    @GetMapping("/price")
    public List<CourseDocument> searchByPriceRange(@RequestParam Double minPrice,
                                                   @RequestParam Double maxPrice) {
        return courseService.searchPrice(minPrice, maxPrice);
    }

    @GetMapping("/category")
    public List<CourseDocument> searchCategory(@RequestParam String category){
        return courseService.searchExact(category);
    }
    @GetMapping("/category/type")
    public List<CourseDocument> searchCategoryType(@RequestParam String type){
        return courseService.searchExactType(type);
    }
    @GetMapping("/search/date")
    public List<CourseDocument> searchCoursesFromDate(@RequestParam String date) {
        Instant fromDate = Instant.parse(date.trim());

        return courseService.findCoursesOnOrAfterDate(fromDate);
    }




}
