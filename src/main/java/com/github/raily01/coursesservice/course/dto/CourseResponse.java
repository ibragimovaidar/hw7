package com.github.raily01.coursesservice.course.dto;

import com.github.raily01.coursesservice.student.dto.StudentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {

    private UUID id;

    private String title;

    private String description;

    private String createDate;

    private String updateDate;

    private List<StudentResponse> students;
}
