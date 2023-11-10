package com.github.raily01.coursesservice.course.service;

import com.github.raily01.coursesservice.course.dto.ApplyStudentsRequest;
import com.github.raily01.coursesservice.course.dto.CourseRequest;
import com.github.raily01.coursesservice.course.dto.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseService {

    CourseResponse getById(UUID id);

    Page<CourseResponse> list(Pageable pageable);

    CourseResponse create(CourseRequest request);

    CourseResponse applyStudents(UUID courseId, ApplyStudentsRequest applyStudentsRequest);
}
