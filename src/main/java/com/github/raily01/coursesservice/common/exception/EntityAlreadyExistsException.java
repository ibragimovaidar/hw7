package com.github.raily01.coursesservice.common.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EntityAlreadyExistsException extends CoursesServiceException {
    public EntityAlreadyExistsException(UUID id) {
        super(HttpStatus.BAD_REQUEST, String.format("Entity with id: %s already exists", id));
    }
}
