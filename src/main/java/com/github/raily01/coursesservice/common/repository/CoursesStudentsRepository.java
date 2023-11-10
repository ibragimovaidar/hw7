package com.github.raily01.coursesservice.common.repository;

import com.github.raily01.coursesservice.common.model.CoursesStudentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoursesStudentsRepository extends JpaRepository<CoursesStudentsEntity, UUID> {

}
