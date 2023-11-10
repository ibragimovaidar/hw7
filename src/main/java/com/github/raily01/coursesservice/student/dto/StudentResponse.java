package com.github.raily01.coursesservice.student.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String email;
}
