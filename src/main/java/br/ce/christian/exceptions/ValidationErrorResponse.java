package br.ce.christian.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationErrorResponse {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final List<ValidationError> errors;

    public ValidationErrorResponse(int status, String message, LocalDateTime timestamp, List<ValidationError> errors) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }
}

