package com.ilchan.calendar_api.api.controller;

import com.ilchan.calendar_api.api.request.CreateEventRequest;
import com.ilchan.calendar_api.api.request.UpdateEventRequest;
import com.ilchan.calendar_api.api.response.EventResponse;
import com.ilchan.calendar_api.application.service.*;
import com.ilchan.calendar_api.domain.entity.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Event", description = "이벤트 관리 API")
@RestController
@RequestMapping("/api/v1")
public class EventController {

    private final CreateEventService createEventService;
    private final GetEventService getEventService;
    private final UpdateEventService updateEventService;
    private final DeleteEventService deleteEventService;
    private final GetEventsByPeriodService getEventsByPeriodService;

    public EventController(
        CreateEventService createEventService,
        GetEventService getEventService,
        UpdateEventService updateEventService,
        DeleteEventService deleteEventService,
        GetEventsByPeriodService getEventsByPeriodService
    ) {
        this.createEventService = createEventService;
        this.getEventService = getEventService;
        this.updateEventService = updateEventService;
        this.deleteEventService = deleteEventService;
        this.getEventsByPeriodService = getEventsByPeriodService;
    }

    @Operation(
        summary = "이벤트 생성",
        description = "특정 캘린더에 새로운 이벤트를 생성합니다. startAt < endAt 조건을 만족해야 합니다."
    )
    @PostMapping("/calendars/{calendarId}/events")
    public ResponseEntity<EventResponse> createEvent(
        @Parameter(
            description = "이벤트를 생성할 캘린더의 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long calendarId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "이벤트 생성 정보 (시작 시간은 종료 시간보다 이전이어야 함)",
            required = true
        )
        @Valid @RequestBody CreateEventRequest request
    ) {
        Event event = createEventService.execute(
            calendarId,
            request.title(),
            request.description(),
            request.startAt(),
            request.endAt(),
            request.allDay()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(EventResponse.from(event));
    }

    @Operation(
        summary = "이벤트 기간 조회",
        description = "특정 캘린더의 기간 내 이벤트를 조회합니다. Overlap 조건(event.startAt < to AND event.endAt > from)으로 겹치는 이벤트를 반환하며, startAt 오름차순으로 정렬됩니다."
    )
    @GetMapping("/calendars/{calendarId}/events")
    public ResponseEntity<List<EventResponse>> getEventsByPeriod(
        @Parameter(
            description = "조회할 캘린더의 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long calendarId,
        @Parameter(
            description = "조회 시작 시간 (ISO-8601 포맷, inclusive)",
            required = true,
            example = "2026-02-10T00:00:00"
        )
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
        @Parameter(
            description = "조회 종료 시간 (ISO-8601 포맷, exclusive 권장)",
            required = true,
            example = "2026-02-10T23:59:59"
        )
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<EventResponse> events = getEventsByPeriodService.execute(calendarId, from, to).stream()
            .map(EventResponse::from)
            .toList();
        return ResponseEntity.ok(events);
    }

    @Operation(
        summary = "이벤트 단건 조회",
        description = "특정 이벤트를 ID로 조회합니다."
    )
    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> getEvent(
        @Parameter(
            description = "조회할 이벤트의 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long eventId
    ) {
        Event event = getEventService.getById(eventId);
        return ResponseEntity.ok(EventResponse.from(event));
    }

    @Operation(
        summary = "이벤트 수정",
        description = "이벤트 정보를 수정합니다. 부분 수정을 지원하며, null이 아닌 필드만 업데이트됩니다. startAt과 endAt을 모두 변경하는 경우 startAt < endAt 조건을 만족해야 합니다."
    )
    @PatchMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
        @Parameter(
            description = "수정할 이벤트의 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long eventId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "수정할 이벤트 정보 (null이 아닌 필드만 업데이트)",
            required = true
        )
        @Valid @RequestBody UpdateEventRequest request
    ) {
        Event event = updateEventService.execute(
            eventId,
            request.title(),
            request.description(),
            request.startAt(),
            request.endAt(),
            request.allDay()
        );
        return ResponseEntity.ok(EventResponse.from(event));
    }

    @Operation(
        summary = "이벤트 삭제",
        description = "이벤트를 삭제합니다. (Hard Delete)"
    )
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(
        @Parameter(
            description = "삭제할 이벤트의 ID",
            required = true,
            example = "1"
        )
        @PathVariable Long eventId
    ) {
        deleteEventService.execute(eventId);
        return ResponseEntity.noContent().build();
    }
}
