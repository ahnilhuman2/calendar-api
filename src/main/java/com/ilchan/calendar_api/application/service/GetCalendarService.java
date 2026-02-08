package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.api.exception.CalendarNotFoundException;
import com.ilchan.calendar_api.domain.entity.Calendar;
import com.ilchan.calendar_api.domain.repository.CalendarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetCalendarService {

    private final CalendarRepository calendarRepository;

    public GetCalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Transactional(readOnly = true)
    public Calendar getById(Long calendarId) {
        return calendarRepository.findById(calendarId)
            .orElseThrow(() -> new CalendarNotFoundException(calendarId));
    }

    @Transactional(readOnly = true)
    public List<Calendar> getAll() {
        return calendarRepository.findAll();
    }
}
