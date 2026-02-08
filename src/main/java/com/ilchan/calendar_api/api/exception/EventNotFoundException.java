package com.ilchan.calendar_api.api.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long eventId) {
        super("이벤트를 찾을 수 없습니다. ID: " + eventId);
    }
}
