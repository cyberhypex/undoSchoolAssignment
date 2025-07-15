package com.undoSchool.demo.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.undoSchool.demo.Model.CourseDocument;
import com.undoSchool.demo.Repositories.CourseRepository;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class JsonDataLoader {
    @Autowired
    private CourseRepository courseRepository;

    @PostConstruct
    public void loadData(){
        try{
            ObjectMapper mapper=new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            InputStream inputStream=new ClassPathResource("sample-courses.json").getInputStream();
            List<CourseDocument> courseDocuments=mapper.readValue(inputStream, new TypeReference<List<CourseDocument>>() {
            });
            courseRepository.saveAll(courseDocuments);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
