package com.github.raily01.coursesservice.course.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyStudentsRequest {

    @NotEmpty
    private List<@NotNull UUID> studentIds;
}
