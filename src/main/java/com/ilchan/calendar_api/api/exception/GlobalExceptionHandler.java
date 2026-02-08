package com.ilchan.calendar_api.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CalendarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCalendarNotFound(CalendarNotFoundException ex) {
        log.warn("Calendar not found: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
            "CALENDAR-404-001",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFound(EventNotFoundException ex) {
        log.warn("Event not found: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
            "EVENT-404-001",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidScheduleException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSchedule(InvalidScheduleException ex) {
        log.warn("Invalid schedule: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
            "EVENT-400-001",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(
            "CALENDAR-400-001",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors().stream()
            .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
            .toList();

        log.warn("Validation failed: {}", fieldErrors);
        ErrorResponse errorResponse = new ErrorResponse(
            "CALENDAR-400-002",
            "요청 값이 올바르지 않습니다.",
            fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        ErrorResponse errorResponse = new ErrorResponse(
            "SYSTEM-500-001",
            "서버 내부 오류가 발생했습니다."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
