package com.github.raily01.courcesservice.student.service.impl;

import com.github.raily01.coursesservice.common.exception.EntityNotFoundException;
import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.common.repository.StudentRepository;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import com.github.raily01.coursesservice.student.service.StudentMapperImpl;
import com.github.raily01.coursesservice.student.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        StudentServiceImpl.class,
        StudentMapperImpl.class})
class StudentServiceImplTest {

    private static final PodamFactory PODAM = new PodamFactoryImpl();

    @Autowired
    StudentMapperImpl studentMapper;

    @Autowired
    StudentServiceImpl studentService;

    @MockBean
    StudentRepository studentRepository;


    @Test
    void findByIdTestOk() {
        var studentRequest = PODAM.manufacturePojo(StudentRequest.class);
        var entity = studentMapper.toEntity(studentRequest);
        when(studentRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.of(entity));

        var studentResponse = studentService.getById(UUID.randomUUID());

        assertAll(
                () -> assertEquals(studentRequest.getMiddleName(), studentResponse.getMiddleName()),
                () -> assertEquals(studentRequest.getLastName(), studentResponse.getLastName()),
                () -> assertEquals(studentRequest.getEmail(), studentResponse.getEmail())
        );
        verify(studentRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void findByIdTestNotFound() {
        when(studentRepository.findById(Mockito.any(UUID.class)))
                .thenReturn(Optional.empty());
        var id = UUID.randomUUID();
        var e = assertThrows(EntityNotFoundException.class, () -> studentService.getById(id));
        verify(studentRepository, times(1)).findById(any(UUID.class));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }

    @Test
    void createStudentTest() {
        var studentRequest = PODAM.manufacturePojo(StudentRequest.class);
        var entity = studentMapper.toEntity(studentRequest);
        entity.setId(UUID.randomUUID());
        when(studentRepository.save(Mockito.any(StudentEntity.class)))
                .thenReturn(entity);

        var studentResponse = studentService.create(studentRequest);

        assertAll(
                () -> assertEquals(studentRequest.getMiddleName(), studentResponse.getMiddleName()),
                () -> assertEquals(studentRequest.getLastName(), studentResponse.getLastName()),
                () -> assertEquals(studentRequest.getEmail(), studentResponse.getEmail())
        );
        verify(studentRepository, times(1)).save(any(StudentEntity.class));
    }

    @Test
    void studentsListTest() {
        Pageable pageable = PageRequest.of(0, 5, Sort.unsorted());
        var students = IntStream.range(0, pageable.getPageSize())
                .mapToObj(i -> PODAM.manufacturePojo(StudentEntity.class))
                .toList();
        var ids = students.stream().map(StudentEntity::getId).collect(Collectors.toSet());
        when(studentRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(students, pageable, students.size()));

        var response = studentService.list(pageable);
        assertAll(
                () -> assertEquals(pageable.getPageSize(), response.getContent().size()),
                () -> assertTrue(response.getContent().stream().map(StudentResponse::getId)
                        .allMatch(ids::contains))
        );
        verify(studentRepository, times(1)).findAll(any(Pageable.class));
    }
}
