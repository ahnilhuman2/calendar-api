package com.ilchan.calendar_api.api.response;

import com.ilchan.calendar_api.domain.entity.Event;

import java.time.LocalDateTime;

public record EventResponse(
    Long id,
    Long calendarId,
    String title,
    String description,
    LocalDateTime startAt,
    LocalDateTime endAt,
    boolean allDay,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static EventResponse from(Event event) {
        return new EventResponse(
            event.getId(),
            event.getCalendarId(),
            event.getTitle(),
            event.getDescription(),
            event.getStartAt(),
            event.getEndAt(),
            event.isAllDay(),
            event.getCreatedAt(),
            event.getUpdatedAt()
        );
    }
}
