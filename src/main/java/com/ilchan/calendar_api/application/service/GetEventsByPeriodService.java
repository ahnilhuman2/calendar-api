package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.api.exception.CalendarNotFoundException;
import com.ilchan.calendar_api.domain.entity.Event;
import com.ilchan.calendar_api.domain.repository.CalendarRepository;
import com.ilchan.calendar_api.domain.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GetEventsByPeriodService {

    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;

    public GetEventsByPeriodService(EventRepository eventRepository, CalendarRepository calendarRepository) {
        this.eventRepository = eventRepository;
        this.calendarRepository = calendarRepository;
    }

    @Transactional(readOnly = true)
    public List<Event> execute(Long calendarId, LocalDateTime from, LocalDateTime to) {
        if (!calendarRepository.existsById(calendarId)) {
            throw new CalendarNotFoundException(calendarId);
        }

        return eventRepository.findByCalendarIdAndPeriod(calendarId, from, to);
    }
}
