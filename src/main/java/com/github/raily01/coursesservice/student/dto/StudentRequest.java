package com.github.raily01.coursesservice.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {

    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z]{2,50}$")
    private String middleName;

    @Email
    private String email;

    private List<UUID> courseIds;
}
