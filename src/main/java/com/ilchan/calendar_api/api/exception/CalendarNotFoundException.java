package com.ilchan.calendar_api.api.exception;

public class CalendarNotFoundException extends RuntimeException {

    public CalendarNotFoundException(Long calendarId) {
        super("캘린더를 찾을 수 없습니다. ID: " + calendarId);
    }
}
