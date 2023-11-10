package com.github.raily01.coursesservice.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoursesServiceException extends RuntimeException {

    private final HttpStatus status;

    private final String errorDescription;

    public CoursesServiceException(HttpStatus status, String errorDescription) {
        this.status = status;
        this.errorDescription = errorDescription;
    }

    public CoursesServiceException(String message, HttpStatus status, String errorDescription) {
        super(message);
        this.status = status;
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        return String.format("CoursesServiceException(status=%s,errorDescription=%s,message=%s)",
                status, errorDescription, super.getMessage());
    }
}
