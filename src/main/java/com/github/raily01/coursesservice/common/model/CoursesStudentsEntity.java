package com.github.raily01.coursesservice.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursesStudentsEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;
}
