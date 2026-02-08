package com.ilchan.calendar_api.api.controller;

import com.ilchan.calendar_api.api.request.CreateCalendarRequest;
import com.ilchan.calendar_api.api.response.CalendarResponse;
import com.ilchan.calendar_api.application.service.CreateCalendarService;
import com.ilchan.calendar_api.application.service.GetCalendarService;
import com.ilchan.calendar_api.domain.entity.Calendar;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Calendar", description = "캘린더 관리 API")
@RestController
@RequestMapping("/api/v1/calendars")
public class CalendarController {

    private final CreateCalendarService createCalendarService;
    private final GetCalendarService getCalendarService;

    public CalendarController(
        CreateCalendarService createCalendarService,
        GetCalendarService getCalendarService
    ) {
        this.createCalendarService = createCalendarService;
        this.getCalendarService = getCalendarService;
    }

    @Operation(
        summary = "캘린더 생성",
        description = "새로운 캘린더를 생성합니다. 이름은 필수이며 1~100자 이내여야 합니다."
    )
    @PostMapping
    public ResponseEntity<CalendarResponse> createCalendar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "캘린더 생성 정보",
            required = true
        )
        @Valid @RequestBody CreateCalendarRequest request
    ) {
        Calendar calendar = createCalendarService.execute(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(CalendarResponse.from(calendar));
    }

    @Operation(
        summary = "캘린더 목록 조회",
        description = "전체 캘린더 목록을 조회합니다. 생성 시간 순으로 정렬됩니다."
    )
    @GetMapping
    public ResponseEntity<List<CalendarResponse>> getCalendars() {
        List<CalendarResponse> calendars = getCalendarService.getAll().stream()
            .map(CalendarResponse::from)
            .toList();
        return ResponseEntity.ok(calendars);
    }

    @Operation(
        summary = "캘린더 단건 조회",
        description = "특정 캘린더를 ID로 조회합니다."
    )
    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarResponse> getCalendar(
        @Parameter(
            description = "조회할 캘린더의 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long calendarId
    ) {
        Calendar calendar = getCalendarService.getById(calendarId);
        return ResponseEntity.ok(CalendarResponse.from(calendar));
    }
}
