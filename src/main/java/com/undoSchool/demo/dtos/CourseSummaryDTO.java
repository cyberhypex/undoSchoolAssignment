package com.undoSchool.demo.dtos;

import lombok.*;

import java.time.Instant;


@Data
@Getter
@Setter
@NoArgsConstructor
public class CourseSummaryDTO {
    private String id;
    private String title;
    private String category;
    private Double price;
    private Instant nextSessionDate;

    public CourseSummaryDTO(
            String id,
            String title,
            String category,
            Double price,
            Instant nextSessionDate
    ){
        this.id=id;
        this.title=title;
        this.category=category;
        this.price=price;
        this.nextSessionDate=nextSessionDate;
    }
}
