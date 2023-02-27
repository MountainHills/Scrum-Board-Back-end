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
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

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
                badRequest,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDetails, badRequest);
    }

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

    @ExceptionHandler(value = {NoAvailableTaskListException.class})
    public ResponseEntity<Object> handleNoAvailableTaskListException(NoAvailableTaskListException natle) {

        HttpStatus okRequest = HttpStatus.OK;
        ExceptionMessage exceptionMessage = new ExceptionMessage(
                natle.getMessage(),
                natle.getClass().getSimpleName(),
                okRequest,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(exceptionMessage, okRequest);
    }
}
