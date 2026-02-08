package com.ilchan.calendar_api.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCalendarRequest(
    @NotBlank(message = "캘린더 이름은 필수입니다.")
    @Size(min = 1, max = 100, message = "캘린더 이름은 1~100자 이내여야 합니다.")
    String name,

    @Size(max = 255, message = "설명은 255자를 초과할 수 없습니다.")
    String description
) {
}
