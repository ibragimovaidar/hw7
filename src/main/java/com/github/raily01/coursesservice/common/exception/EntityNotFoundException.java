package com.github.raily01.coursesservice.common.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EntityNotFoundException extends CoursesServiceException {

    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Entity not found");
    }

    public EntityNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, String.format("Entity with id: %s not found", id));
    }
}
