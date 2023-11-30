package com.github.raily01.courcesservice.student.service.impl;

import com.github.raily01.coursesservice.CoursesServiceApplication;
import com.github.raily01.coursesservice.common.model.CourseEntity;
import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.common.repository.CourseRepository;
import com.github.raily01.coursesservice.common.repository.CoursesStudentsRepository;
import com.github.raily01.coursesservice.common.repository.StudentRepository;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.service.StudentMapperImpl;
import com.github.raily01.coursesservice.student.service.StudentService;
import com.github.raily01.coursesservice.student.service.impl.StudentServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {
        CoursesServiceApplication.class,
        StudentServiceImpl.class,
        StudentMapperImpl.class,
})
class StudentServiceIntegrationTest {
    static Logger log = LoggerFactory.getLogger(StudentServiceIntegrationTest.class);

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    StudentMapperImpl studentMapper;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CoursesStudentsRepository coursesStudentsRepository;

    StudentRequest studentRequest;

    CourseEntity course;

    @BeforeEach
    void init() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        course = CourseEntity.builder()
                .title("title")
                .description("description")
                .build();
        course = courseRepository.save(course);

        studentRequest = StudentRequest.builder()
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .email("ivan@email.com")
                .build();
    }

    @Test
    @Transactional(value = Transactional.TxType.NEVER)
    void updateParallelUpdateTestOptimisticLockException() {
        var student = StudentEntity.builder()
                .firstName("Ivan")
                .lastName("Ivanovich")
                .middleName("Ivanov")
                .email("ivan@email.com")
                .build();

        var savedStudent = studentRepository.save(student);
        var student1 = studentRepository.findById(savedStudent.getId()).orElseThrow();
        var student2 = studentRepository.findById(savedStudent.getId()).orElseThrow();
        assertNotEquals(student1.hashCode(), student2.hashCode());
        assertEquals(student1.getVersion(), student2.getVersion());
        student1.setFirstName("Dmitry");
        student2.setFirstName("Zakhar");
        studentRepository.save(student1);
        assertThrows(ObjectOptimisticLockingFailureException.class, () -> studentRepository.save(student2));
    }

    @Test
    void updateTestOk() {
        var student = studentService.create(studentRequest);

        studentRequest.setFirstName("Vladimir");
        studentService.update(student.getId(), studentRequest);

        var updatedStudent = studentRepository.findById(student.getId()).orElseThrow();
        assertEquals(studentRequest.getFirstName(), updatedStudent.getFirstName());
    }

    @Test
    void updateTestCoursesOk() {
        var student = studentService.create(studentRequest);

        studentRequest.setCourseIds(List.of(course.getId()));
        studentService.update(student.getId(), studentRequest);

        var updatedStudent = studentRepository.findById(student.getId()).orElseThrow();
        assertAll(
                () -> assertEquals(1, updatedStudent.getCourses().size()),
                () -> assertEquals(course.getId(), updatedStudent.getCourses().get(0).getCourse().getId())
        );
    }
}
