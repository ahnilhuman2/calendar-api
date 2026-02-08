package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.api.exception.EventNotFoundException;
import com.ilchan.calendar_api.domain.entity.Event;
import com.ilchan.calendar_api.domain.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetEventService {

    private final EventRepository eventRepository;

    public GetEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public Event getById(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(eventId));
    }
}
