package com.github.raily01.coursesservice.common.repository;

import com.github.raily01.coursesservice.common.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}
