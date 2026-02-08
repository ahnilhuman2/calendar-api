package com.ilchan.calendar_api.api.controller;

import com.ilchan.calendar_api.api.request.CreateCalendarRequest;
import com.ilchan.calendar_api.api.response.CalendarResponse;
import com.ilchan.calendar_api.application.service.CreateCalendarService;
import com.ilchan.calendar_api.application.service.GetCalendarService;
import com.ilchan.calendar_api.domain.entity.Calendar;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<CalendarResponse> createCalendar(@Valid @RequestBody CreateCalendarRequest request) {
        Calendar calendar = createCalendarService.execute(request.name(), request.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(CalendarResponse.from(calendar));
    }

    @GetMapping
    public ResponseEntity<List<CalendarResponse>> getCalendars() {
        List<CalendarResponse> calendars = getCalendarService.getAll().stream()
            .map(CalendarResponse::from)
            .toList();
        return ResponseEntity.ok(calendars);
    }

    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarResponse> getCalendar(@PathVariable Long calendarId) {
        Calendar calendar = getCalendarService.getById(calendarId);
        return ResponseEntity.ok(CalendarResponse.from(calendar));
    }
}
