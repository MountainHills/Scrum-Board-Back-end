package com.pagejump.scrumboard.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private final String message;
    private final String exception;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;
}
