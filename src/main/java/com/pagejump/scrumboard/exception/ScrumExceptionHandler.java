package com.pagejump.scrumboard.exception;

import com.pagejump.scrumboard.exception.message.ExceptionMessage;
import com.pagejump.scrumboard.exception.message.ValidationErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ScrumExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errorMap = new HashMap<>();
        HttpStatus unprocessableEntity = HttpStatus.UNPROCESSABLE_ENTITY;
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        error -> {
                            errorMap.put(error.getField(), error.getDefaultMessage());
                        }
                );

        ValidationErrorMessage errorDetails = new ValidationErrorMessage(
                errorMap,
                ex.getClass().getSimpleName(),
                unprocessableEntity,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDetails, unprocessableEntity);
    }

    @ExceptionHandler(value = {TaskNotFoundException.class})
    public ResponseEntity<Object> handleTaskNotFoundException(TaskNotFoundException tnfe) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                tnfe.getMessage(),
                tnfe.getClass().getSimpleName(),
                notFound,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionMessage, notFound);
    }

    @ExceptionHandler(value = {TaskDeletedException.class})
    public ResponseEntity<Object> handleTaskDeletedException(TaskDeletedException tde) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                tde.getMessage(),
                tde.getClass().getSimpleName(),
                badRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionMessage, badRequest);
    }
}
