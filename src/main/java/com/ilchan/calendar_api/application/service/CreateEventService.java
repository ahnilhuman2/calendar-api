package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.api.exception.CalendarNotFoundException;
import com.ilchan.calendar_api.domain.entity.Event;
import com.ilchan.calendar_api.domain.repository.CalendarRepository;
import com.ilchan.calendar_api.domain.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateEventService {

    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;

    public CreateEventService(EventRepository eventRepository, CalendarRepository calendarRepository) {
        this.eventRepository = eventRepository;
        this.calendarRepository = calendarRepository;
    }

    @Transactional
    public Event execute(Long calendarId, String title, String description, LocalDateTime startAt, LocalDateTime endAt, boolean allDay) {
        if (!calendarRepository.existsById(calendarId)) {
            throw new CalendarNotFoundException(calendarId);
        }

        Event event = new Event(calendarId, title, description, startAt, endAt, allDay);
        return eventRepository.save(event);
    }
}
