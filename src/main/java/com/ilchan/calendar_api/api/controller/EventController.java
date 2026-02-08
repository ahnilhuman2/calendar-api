package com.ilchan.calendar_api.api.controller;

import com.ilchan.calendar_api.api.request.CreateEventRequest;
import com.ilchan.calendar_api.api.request.UpdateEventRequest;
import com.ilchan.calendar_api.api.response.EventResponse;
import com.ilchan.calendar_api.application.service.*;
import com.ilchan.calendar_api.domain.entity.Event;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @PostMapping("/calendars/{calendarId}/events")
    public ResponseEntity<EventResponse> createEvent(
        @PathVariable Long calendarId,
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

    @GetMapping("/calendars/{calendarId}/events")
    public ResponseEntity<List<EventResponse>> getEventsByPeriod(
        @PathVariable Long calendarId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<EventResponse> events = getEventsByPeriodService.execute(calendarId, from, to).stream()
            .map(EventResponse::from)
            .toList();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId) {
        Event event = getEventService.getById(eventId);
        return ResponseEntity.ok(EventResponse.from(event));
    }

    @PatchMapping("/events/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(
        @PathVariable Long eventId,
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

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        deleteEventService.execute(eventId);
        return ResponseEntity.noContent().build();
    }
}
