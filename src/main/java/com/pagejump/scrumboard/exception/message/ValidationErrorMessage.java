package com.pagejump.scrumboard.exception.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorMessage {

    private final Map<String, String> message;
    private final String exception;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}
