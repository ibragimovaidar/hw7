package com.github.raily01.coursesservice.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourseResponse {

    private UUID id;

    private String title;

    private String description;
}
