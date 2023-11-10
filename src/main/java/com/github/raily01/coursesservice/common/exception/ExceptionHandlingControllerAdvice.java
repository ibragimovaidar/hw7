package com.github.raily01.coursesservice.common.exception;

import com.github.raily01.coursesservice.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        log.info("Ошибка валидации запроса");
        log.debug("Ошибка валидации запроса", e);
        var fieldsString = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));
        var errorDescription = String.format("Поля [%s] заполненны некорректно", fieldsString);
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(errorDescription));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("Необрабатываемая ошибка", e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse("Необрабатываемая ошибка сервера"));
    }

    @ExceptionHandler(CoursesServiceException.class)
    public ResponseEntity<ErrorResponse> handleShopRestServiceException(CoursesServiceException e) {
        log.info("Неудачный ответ", e);
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getErrorDescription()));
    }
}
