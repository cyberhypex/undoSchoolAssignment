package com.undoSchool.demo.Repositories;

import com.undoSchool.demo.Model.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument,String> {

}
