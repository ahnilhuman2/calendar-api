package com.ilchan.calendar_api.api.response;

import com.ilchan.calendar_api.domain.entity.Event;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "이벤트 응답")
public record EventResponse(
    @Schema(description = "이벤트 ID", example = "1")
    Long id,

    @Schema(description = "소속 캘린더 ID", example = "1")
    Long calendarId,

    @Schema(description = "이벤트 제목", example = "주간 회의")
    String title,

    @Schema(description = "이벤트 설명", example = "팀 미팅 및 주간 리뷰", nullable = true)
    String description,

    @Schema(description = "시작 시간 (ISO-8601)", example = "2026-02-10T10:00:00")
    LocalDateTime startAt,

    @Schema(description = "종료 시간 (ISO-8601)", example = "2026-02-10T11:00:00")
    LocalDateTime endAt,

    @Schema(description = "종일 일정 여부", example = "false")
    boolean allDay,

    @Schema(description = "생성 시간 (ISO-8601)", example = "2026-02-08T21:00:00")
    LocalDateTime createdAt,

    @Schema(description = "수정 시간 (ISO-8601)", example = "2026-02-08T21:00:00")
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
