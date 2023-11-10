package com.github.raily01.coursesservice.common.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "course")
public class CourseEntity extends AbstractEntity {

    private String title;

    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.PERSIST)
    private List<CoursesStudentsEntity> courseStudents;
}
