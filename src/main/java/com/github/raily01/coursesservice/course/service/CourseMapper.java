package com.github.raily01.coursesservice.course.service;

import com.github.raily01.coursesservice.common.model.CourseEntity;
import com.github.raily01.coursesservice.common.model.CoursesStudentsEntity;
import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.course.dto.CourseRequest;
import com.github.raily01.coursesservice.course.dto.CourseResponse;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import com.github.raily01.coursesservice.student.service.StudentMapper;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = StudentMapper.class,
        imports = StudentMapper.class)
public abstract class CourseMapper {

    @Autowired
    protected StudentMapper studentMapper;

    @Mapping(target = "students", expression = "java(studentMapper.toResponseList(mapStudents(entity)))")
    public abstract CourseResponse toResponse(CourseEntity entity);

    public abstract CourseEntity toEntity(CourseRequest courseRequest);

    protected List<StudentEntity> mapStudents(CourseEntity entity) {
        if (entity.getCourseStudents() != null) {
            return entity.getCourseStudents().stream().map(CoursesStudentsEntity::getStudent).toList();
        }
        return Collections.emptyList();
    }
}
