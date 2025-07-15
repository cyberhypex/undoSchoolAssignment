package com.undoSchool.demo.Service;


import com.undoSchool.demo.Model.CourseDocument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;

import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.nio.charset.CoderResult;
import java.time.Instant;
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


    public List<CourseDocument> searchCriteria(Integer minAge,Integer maxAge){
        Criteria criteria=new Criteria("minAge").is(minAge).and("maxAge").is(maxAge);
        Query query = new CriteriaQuery(criteria);

        SearchHits<CourseDocument> hits=elasticsearchOperations.search(query,CourseDocument.class);
        return hits.stream().map(
                SearchHit::getContent
        ).collect(Collectors.toList());

    }

    public List<CourseDocument> searchPrice(Double minPrice, Double maxPrice) {
        Criteria criteria = new Criteria("price")
                .greaterThanEqual(minPrice)
                .and("price").lessThanEqual(maxPrice);

        Query query = new CriteriaQuery(criteria);
        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<CourseDocument> searchExact(String category){
        Criteria criteria=new Criteria("category").is(category);
        Query query=new CriteriaQuery(criteria);
        SearchHits<CourseDocument> hits=elasticsearchOperations.search(query,CourseDocument.class);
        return hits.stream().map(
                SearchHit::getContent
        ).collect(Collectors.toList());
    }
    public List<CourseDocument> searchExactType(String type){
        Criteria criteria=new Criteria("type").is(type);
        Query query=new CriteriaQuery(criteria);
        SearchHits<CourseDocument> hits=elasticsearchOperations.search(query,CourseDocument.class);
        return hits.stream().map(
                SearchHit::getContent
        ).collect(Collectors.toList());
    }

    public List<CourseDocument> findCoursesOnOrAfterDate(Instant fromDate) {
        Criteria criteria = new Criteria("nextSessionDate").greaterThanEqual(fromDate);
        CriteriaQuery query = new CriteriaQuery(criteria);

        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);

        return hits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }



}
