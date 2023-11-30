package com.github.raily01.coursesservice.common.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "student")
@SuperBuilder
public class StudentEntity extends AbstractEntity {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "student")
    private List<CoursesStudentsEntity> courses;

    @Version
    private Integer version;
}

