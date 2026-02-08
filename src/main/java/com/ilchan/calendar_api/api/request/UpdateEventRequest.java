package com.ilchan.calendar_api.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "이벤트 수정 요청 (부분 수정 지원)")
public record UpdateEventRequest(
    @Schema(description = "이벤트 제목", example = "주간 회의 (변경)", nullable = true)
    @Size(min = 1, max = 200, message = "이벤트 제목은 1~200자 이내여야 합니다.")
    String title,

    @Schema(description = "이벤트 설명", example = "회의 내용 업데이트", nullable = true)
    String description,

    @Schema(description = "시작 시간 (ISO-8601)", example = "2026-02-10T10:30:00", nullable = true)
    LocalDateTime startAt,

    @Schema(description = "종료 시간 (ISO-8601)", example = "2026-02-10T11:30:00", nullable = true)
    LocalDateTime endAt,

    @Schema(description = "종일 일정 여부", example = "false", nullable = true)
    Boolean allDay
) {
}
