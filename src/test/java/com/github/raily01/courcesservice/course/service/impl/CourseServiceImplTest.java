package com.github.raily01.courcesservice.course.service.impl;

import com.github.raily01.coursesservice.common.exception.EntityNotFoundException;
import com.github.raily01.coursesservice.common.model.CourseEntity;
import com.github.raily01.coursesservice.common.model.CoursesStudentsEntity;
import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.common.repository.CourseRepository;
import com.github.raily01.coursesservice.common.repository.CoursesStudentsRepository;
import com.github.raily01.coursesservice.common.repository.StudentRepository;
import com.github.raily01.coursesservice.course.dto.ApplyStudentsRequest;
import com.github.raily01.coursesservice.course.dto.CourseResponse;
import com.github.raily01.coursesservice.course.service.CourseMapper;
import com.github.raily01.coursesservice.course.service.CourseMapperImpl;
import com.github.raily01.coursesservice.course.service.impl.CourseServiceImpl;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import com.github.raily01.coursesservice.student.service.StudentMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {
        CourseServiceImpl.class,
        CourseMapperImpl.class,
        StudentMapperImpl.class})
class CourseServiceImplTest {

    private static final PodamFactory PODAM = new PodamFactoryImpl();

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    CourseServiceImpl courseService;

    @MockBean
    CourseRepository courseRepository;

    @MockBean
    CoursesStudentsRepository coursesStudentsRepository;

    @MockBean
    StudentRepository studentRepository;


    @Test
    void applyStudentsTestOk() {
        var course = new CourseEntity("title", "desc", new ArrayList<>());
        var courseId = UUID.randomUUID();
        course.setId(courseId);
        var studentIds = IntStream.range(0, 5).mapToObj(i -> UUID.randomUUID()).toList();
        var request = new ApplyStudentsRequest(studentIds);

        Mockito.when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.of(course));
        Mockito.when(studentRepository.findAllById(any(List.class))).thenAnswer(i -> {
            var ids = (List<UUID>) i.getArgument(0);
            return ids.stream().map(id -> {
                var student = PODAM.manufacturePojo(StudentEntity.class);
                student.setId(id);
                return student;

            }).toList();
        });
        Mockito.when(coursesStudentsRepository.save(any(CoursesStudentsEntity.class))).thenAnswer(i -> i.getArgument(0));
        Mockito.when(courseRepository.save(any(CourseEntity.class))).thenAnswer(i -> i.getArgument(0));

        var response = courseService.applyStudents(courseId, request);
        assertAll(
                () -> assertTrue(response.getStudents().stream().allMatch(s -> studentIds.contains(s.getId())))
        );
        verify(studentRepository, times(1)).findAllById(any());
        verify(courseRepository, times(1)).save(any(CourseEntity.class));
    }

    @Test
    void applyStudentsTestCourseNotFound() {
        var course = new CourseEntity("title", "desc", new ArrayList<>());
        var courseId = UUID.randomUUID();
        course.setId(courseId);
        var studentIds = IntStream.range(0, 5).mapToObj(i -> UUID.randomUUID()).toList();
        var request = new ApplyStudentsRequest(studentIds);

        Mockito.when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        var e = assertThrows(EntityNotFoundException.class, () -> courseService.applyStudents(courseId, request));
        verify(courseRepository, times(1)).findById(any(UUID.class));
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
    }
}
