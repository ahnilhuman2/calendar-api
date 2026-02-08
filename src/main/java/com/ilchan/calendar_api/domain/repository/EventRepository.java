package com.ilchan.calendar_api.domain.repository;

import com.ilchan.calendar_api.domain.entity.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Event save(Event event);

    Optional<Event> findById(Long id);

    List<Event> findByCalendarIdAndPeriod(Long calendarId, LocalDateTime from, LocalDateTime to);

    void deleteById(Long id);

    boolean existsById(Long id);
}
