package com.github.raily01.coursesservice.student.service;

import com.github.raily01.coursesservice.common.model.StudentEntity;
import com.github.raily01.coursesservice.student.dto.StudentRequest;
import com.github.raily01.coursesservice.student.dto.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    StudentResponse toResponse(StudentEntity entity);

    StudentEntity toEntity(StudentRequest request);

    List<StudentResponse> toResponseList(List<StudentEntity> entities);
}
