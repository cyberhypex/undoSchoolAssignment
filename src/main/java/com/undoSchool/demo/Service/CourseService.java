package com.undoSchool.demo.Service;


import com.undoSchool.demo.Model.CourseDocument;
import com.undoSchool.demo.Repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public List<CourseDocument> searchCoursesWithKeyWord(String keyword){
       NativeQuery query=NativeQuery.builder().withQuery(
               q->q.multiMatch(mm->mm.query(keyword)
                       .fields("title","description")
               )
       )
               .build();
        SearchHits<CourseDocument> hits=elasticsearchOperations.search(query,CourseDocument.class);
        return hits.stream().map(
                SearchHit::getContent
        ).collect(Collectors.toList());
    }


}
