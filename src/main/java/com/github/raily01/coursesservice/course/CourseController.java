package com.github.raily01.coursesservice.course;

import com.github.raily01.coursesservice.course.dto.ApplyStudentsRequest;
import com.github.raily01.coursesservice.course.dto.CourseRequest;
import com.github.raily01.coursesservice.course.dto.CourseResponse;
import com.github.raily01.coursesservice.course.service.CourseService;
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
@RequestMapping(value = "/api/v1/course", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/{id}")
    public CourseResponse view(@PathVariable UUID id) {
        log.debug("course view request: {}", id);
        var response = courseService.getById(id);
        log.debug("course view response: {}", response);
        return response;
    }

    @GetMapping
    public Page<CourseResponse> view(Pageable pageable) {
        log.debug("course list request: {}", pageable);
        var response = courseService.list(pageable);
        log.debug("course view response: {}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CourseResponse create(@RequestBody CourseRequest request) {
        log.debug("course create request: {}", request);
        var response = courseService.create(request);
        log.debug("course create response: {}", response);
        return response;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/{id}/applyStudents")
    public CourseResponse applyStudents(@PathVariable UUID id, @Validated @RequestBody ApplyStudentsRequest request) {
        return courseService.applyStudents(id, request);
    }
}
