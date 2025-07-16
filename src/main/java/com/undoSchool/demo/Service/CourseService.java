package com.undoSchool.demo.Service;


import com.undoSchool.demo.Model.CourseDocument;

import com.undoSchool.demo.Model.CourseSearchResponse;
import com.undoSchool.demo.dtos.CourseSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public CourseSearchResponse searchCourses(
            String keyword,
            Integer minAge,
            Integer maxAge,
            String category,
            String type,
            Double minPrice,
            Double maxPrice,
            Instant startDate,
            String sort,
            int page,
            int size
    ) {
        Criteria criteria = new Criteria();

        if (keyword != null && !keyword.isBlank()) {
            criteria = criteria.and(new Criteria().or("title").matches(keyword).or("description").matches(keyword));
        }
        if (minAge != null) criteria = criteria.and("minAge").greaterThanEqual(minAge);
        if (maxAge != null) criteria = criteria.and("maxAge").lessThanEqual(maxAge);
        if (category != null) criteria = criteria.and("category").is(category);
        if (type != null) criteria = criteria.and("type").is(type);
        if (minPrice != null) criteria = criteria.and("price").greaterThanEqual(minPrice);
        if (maxPrice != null) criteria = criteria.and("price").lessThanEqual(maxPrice);
        if (startDate != null) criteria = criteria.and("nextSessionDate").greaterThanEqual(startDate);

        Query query = new CriteriaQuery(criteria);

        // Sorting
        if (sort != null) {
            switch (sort) {
                case "priceAsc" -> query.addSort(Sort.by("price").ascending());
                case "priceDesc" -> query.addSort(Sort.by("price").descending());
                case "upcoming" -> query.addSort(Sort.by("nextSessionDate").ascending());
            }
        }

        // Pagination
        query.setPageable(PageRequest.of(page, size));

        SearchHits<CourseDocument> hits = elasticsearchOperations.search(query, CourseDocument.class);

        List<CourseSummaryDTO> summaries = hits.stream()
                .map(hit -> {
                    CourseDocument doc = hit.getContent();
                    return new CourseSummaryDTO(
                            doc.getId(),
                            doc.getTitle(),
                            doc.getCategory(),
                            doc.getPrice(),
                            doc.getNextSessionDate()
                    );
                })
                .collect(Collectors.toList());

        return new CourseSearchResponse(hits.getTotalHits(), summaries);
    }
}







