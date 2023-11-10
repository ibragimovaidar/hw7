package com.github.raily01.coursesservice.student.service;

import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StudentService {

    StudentResponse getById(UUID id);

    Page<StudentResponse> list(Pageable pageable);

    StudentResponse create(StudentRequest request);

}
