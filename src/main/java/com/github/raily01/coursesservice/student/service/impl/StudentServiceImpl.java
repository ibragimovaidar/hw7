package com.github.raily01.coursesservice.student.service.impl;

import com.github.raily01.coursesservice.common.exception.EntityNotFoundException;
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

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;

    private final StudentRepository studentRepository;

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
}
