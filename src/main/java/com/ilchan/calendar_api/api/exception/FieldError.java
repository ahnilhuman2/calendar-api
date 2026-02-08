package com.ilchan.calendar_api.api.exception;

public record FieldError(
    String field,
    String reason
) {
}
