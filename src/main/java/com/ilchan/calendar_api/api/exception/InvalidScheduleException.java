package com.ilchan.calendar_api.api.exception;

public class InvalidScheduleException extends RuntimeException {

    public InvalidScheduleException(String message) {
        super(message);
    }
}
