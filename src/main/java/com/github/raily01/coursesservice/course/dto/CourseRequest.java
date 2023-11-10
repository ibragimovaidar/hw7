package com.github.raily01.coursesservice.course.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    @Pattern(regexp = "^.{2,50}$")
    private String title;

    @Pattern(regexp = "^.{2,255}$")
    private String description;
}
