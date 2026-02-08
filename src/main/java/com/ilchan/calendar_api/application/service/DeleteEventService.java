package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.api.exception.EventNotFoundException;
import com.ilchan.calendar_api.domain.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteEventService {

    private final EventRepository eventRepository;

    public DeleteEventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void execute(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(eventId);
        }
        eventRepository.deleteById(eventId);
    }
}
