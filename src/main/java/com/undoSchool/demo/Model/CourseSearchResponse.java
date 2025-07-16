package com.undoSchool.demo.Model;

import com.undoSchool.demo.dtos.CourseSummaryDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@Getter
@Setter

public class CourseSearchResponse {
    private Long total;
    private List<CourseSummaryDTO> courses;

    public CourseSearchResponse(Long total,List<CourseSummaryDTO> courses){
        this.total=total;
        this.courses=courses;
    }

}
