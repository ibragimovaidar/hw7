package com.github.raily01.coursesservice.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "student")
public class StudentEntity extends AbstractEntity {

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;

}

