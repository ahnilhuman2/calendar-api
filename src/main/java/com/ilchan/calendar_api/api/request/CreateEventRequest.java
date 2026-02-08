package com.ilchan.calendar_api.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "이벤트 생성 요청")
public record CreateEventRequest(
    @Schema(description = "이벤트 제목", example = "주간 회의", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "이벤트 제목은 필수입니다.")
    @Size(min = 1, max = 200, message = "이벤트 제목은 1~200자 이내여야 합니다.")
    String title,

    @Schema(description = "이벤트 설명", example = "팀 미팅 및 주간 리뷰", nullable = true)
    String description,

    @Schema(description = "시작 시간 (ISO-8601)", example = "2026-02-10T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "시작 시간은 필수입니다.")
    LocalDateTime startAt,

    @Schema(description = "종료 시간 (ISO-8601)", example = "2026-02-10T11:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "종료 시간은 필수입니다.")
    LocalDateTime endAt,

    @Schema(description = "종일 일정 여부", example = "false", defaultValue = "false")
    boolean allDay
) {
}
