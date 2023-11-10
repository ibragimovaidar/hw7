package com.github.raily01.coursesservice.course.service.impl;

import com.github.raily01.coursesservice.common.exception.EntityNotFoundException;
import com.github.raily01.coursesservice.common.model.CoursesStudentsEntity;
import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.common.repository.CourseRepository;
import com.github.raily01.coursesservice.common.repository.CoursesStudentsRepository;
import com.github.raily01.coursesservice.common.repository.StudentRepository;
import com.github.raily01.coursesservice.course.dto.ApplyStudentsRequest;
import com.github.raily01.coursesservice.course.dto.CourseRequest;
import com.github.raily01.coursesservice.course.dto.CourseResponse;
import com.github.raily01.coursesservice.course.service.CourseMapper;
import com.github.raily01.coursesservice.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    private final CoursesStudentsRepository coursesStudentsRepository;

    @Override
    public CourseResponse getById(UUID id) {
        var course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return courseMapper.toResponse(course);
    }

    @Override
    public Page<CourseResponse> list(Pageable pageable) {
        return courseRepository.findAll(pageable).map(courseMapper::toResponse);
    }

    @Override
    public CourseResponse create(CourseRequest request) {
        var course = courseMapper.toEntity(request);
        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CourseResponse applyStudents(UUID courseId, ApplyStudentsRequest applyStudentsRequest) {
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(courseId));
        var students = studentRepository.findAllById(applyStudentsRequest.getStudentIds()).stream()
                .collect(Collectors.toMap(StudentEntity::getId, s -> s));
        var coursesStudentsEntities = applyStudentsRequest.getStudentIds().stream()
                .map(id -> {
                    var student = students.get(id);
                    if (student == null) {
                        throw new EntityNotFoundException(id);
                    }
                    return new CoursesStudentsEntity(course, student);
                }).toList();
        course.getCourseStudents().addAll(coursesStudentsEntities);
        coursesStudentsRepository.saveAll(coursesStudentsEntities);
        return courseMapper.toResponse(courseRepository.save(course));
    }
}
