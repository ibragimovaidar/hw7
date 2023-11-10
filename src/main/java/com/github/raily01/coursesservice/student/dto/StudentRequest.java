package com.github.raily01.coursesservice.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String middleName;

    @Email
    private String email;
}
