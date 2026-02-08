package com.ilchan.calendar_api.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateEventRequest(
    @NotBlank(message = "이벤트 제목은 필수입니다.")
    @Size(min = 1, max = 200, message = "이벤트 제목은 1~200자 이내여야 합니다.")
    String title,

    String description,

    @NotNull(message = "시작 시간은 필수입니다.")
    LocalDateTime startAt,

    @NotNull(message = "종료 시간은 필수입니다.")
    LocalDateTime endAt,

    boolean allDay
) {
}
