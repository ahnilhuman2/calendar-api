package com.ilchan.calendar_api.api.response;

import com.ilchan.calendar_api.domain.entity.Calendar;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "캘린더 응답")
public record CalendarResponse(
    @Schema(description = "캘린더 ID", example = "1")
    Long id,

    @Schema(description = "캘린더 이름", example = "내 캘린더")
    String name,

    @Schema(description = "캘린더 설명", example = "개인 일정 관리용", nullable = true)
    String description,

    @Schema(description = "생성 시간 (ISO-8601)", example = "2026-02-08T15:00:00")
    LocalDateTime createdAt,

    @Schema(description = "수정 시간 (ISO-8601)", example = "2026-02-08T15:00:00")
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
