package com.ilchan.calendar_api.api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    String errorCode,
    String message,
    List<FieldError> fieldErrors
) {
    public ErrorResponse(String errorCode, String message) {
        this(errorCode, message, null);
    }
}
