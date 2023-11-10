package com.github.raily01.coursesservice.student;

import com.github.raily01.coursesservice.course.dto.CourseRequest;
import com.github.raily01.coursesservice.course.dto.CourseResponse;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import com.github.raily01.coursesservice.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/student", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public StudentResponse view(@PathVariable UUID id) {
        log.debug("student view request: {}", id);
        var response = studentService.getById(id);
        log.debug("student view response: {}", response);
        return response;
    }

    @GetMapping
    public Page<StudentResponse> view(Pageable pageable) {
        log.debug("student list request: {}", pageable);
        var response = studentService.list(pageable);
        log.debug("student list response: {}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public StudentResponse create(@Validated @RequestBody StudentRequest request) {
        log.debug("student create request: {}", request);
        var response = studentService.create(request);
        log.debug("student create response: {}", response);
        return response;
    }
}
