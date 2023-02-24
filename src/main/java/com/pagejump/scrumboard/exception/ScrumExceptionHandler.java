package com.pagejump.scrumboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ScrumExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException tnfe) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                tnfe.getMessage(),
                tnfe.getClass().getSimpleName(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
}
