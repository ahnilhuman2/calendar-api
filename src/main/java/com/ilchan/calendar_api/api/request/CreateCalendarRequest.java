package com.ilchan.calendar_api.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "캘린더 생성 요청")
public record CreateCalendarRequest(
    @Schema(description = "캘린더 이름", example = "내 캘린더", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "캘린더 이름은 필수입니다.")
    @Size(min = 1, max = 100, message = "캘린더 이름은 1~100자 이내여야 합니다.")
    String name,

    @Schema(description = "캘린더 설명", example = "개인 일정 관리용", nullable = true)
    @Size(max = 255, message = "설명은 255자를 초과할 수 없습니다.")
    String description
) {
}
