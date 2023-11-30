package com.github.raily01.coursesservice.student.service;

import com.github.raily01.coursesservice.common.model.CourseEntity;
import com.github.raily01.coursesservice.common.model.CoursesStudentsEntity;
import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.student.dto.StudentCourseResponse;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    @Mapping(target = "courses", expression = "java(mapCourses(entity.getCourses()))")
    StudentResponse toResponse(StudentEntity entity);

    StudentEntity toEntity(StudentRequest request);

    List<StudentResponse> toResponseList(List<StudentEntity> entities);

    StudentCourseResponse toCourseResponse(CourseEntity entity);

    void mapToEntity(StudentRequest request, @MappingTarget StudentEntity entity);

    default List<StudentCourseResponse> mapCourses(List<CoursesStudentsEntity> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return entities.stream()
                .map(entity -> toCourseResponse(entity.getCourse()))
                .collect(Collectors.toList());
    }
}
