package com.github.raily01.coursesservice.student.service.impl;

import com.github.raily01.coursesservice.common.exception.EntityNotFoundException;
import com.github.raily01.coursesservice.common.model.AbstractEntity;
import com.github.raily01.coursesservice.common.model.CoursesStudentsEntity;
import com.github.raily01.coursesservice.common.repository.CourseRepository;
import com.github.raily01.coursesservice.common.repository.CoursesStudentsRepository;
import com.github.raily01.coursesservice.common.repository.StudentRepository;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import com.github.raily01.coursesservice.student.service.StudentMapper;
import com.github.raily01.coursesservice.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final CoursesStudentsRepository coursesStudentsRepository;

    @Override
    public StudentResponse getById(UUID id) {
        var student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        return studentMapper.toResponse(student);
    }

    @Override
    public Page<StudentResponse> list(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toResponse);
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        return studentMapper.toResponse(
                studentRepository.save(
                        studentMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public void update(UUID id, StudentRequest request) {
        var student = studentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        studentMapper.mapToEntity(request, student);
        if (request.getCourseIds() != null) {
            var foundCourses = courseRepository.findAllById(request.getCourseIds());
            var foundCoursesIds = foundCourses.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
            var allCoursesFound = foundCoursesIds.containsAll(request.getCourseIds());
            if (!allCoursesFound) {
                throw new EntityNotFoundException();
            }
            var coursesStudents = foundCourses.stream()
                    .map(course -> new CoursesStudentsEntity(course, student))
                    .toList();
            coursesStudentsRepository.deleteAllByStudentId(student.getId());
            if (student.getCourses() == null) {
                student.setCourses(new ArrayList<>());
            }
            student.getCourses().clear();
            student.getCourses().addAll(coursesStudents);
        }
        studentRepository.save(student);
    }
}
