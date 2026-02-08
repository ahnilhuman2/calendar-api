package com.ilchan.calendar_api.api.response;

import com.ilchan.calendar_api.domain.entity.Calendar;

import java.time.LocalDateTime;

public record CalendarResponse(
    Long id,
    String name,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CalendarResponse from(Calendar calendar) {
        return new CalendarResponse(
            calendar.getId(),
            calendar.getName(),
            calendar.getDescription(),
            calendar.getCreatedAt(),
            calendar.getUpdatedAt()
        );
    }
}
