package com.ilchan.calendar_api.api.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateEventRequest(
    @Size(min = 1, max = 200, message = "이벤트 제목은 1~200자 이내여야 합니다.")
    String title,

    String description,

    LocalDateTime startAt,

    LocalDateTime endAt,

    Boolean allDay
) {
}
