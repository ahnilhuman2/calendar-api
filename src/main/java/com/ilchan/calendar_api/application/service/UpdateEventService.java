package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.api.exception.EventNotFoundException;
import com.ilchan.calendar_api.domain.entity.Event;
import com.ilchan.calendar_api.domain.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UpdateEventService {

    private final EventRepository eventRepository;

    public UpdateEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event execute(Long eventId, String title, String description, LocalDateTime startAt, LocalDateTime endAt, Boolean allDay) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(eventId));

        event.updateEvent(title, description, startAt, endAt, allDay);
        return eventRepository.save(event);
    }
}
