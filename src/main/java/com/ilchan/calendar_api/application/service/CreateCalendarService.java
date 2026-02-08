package com.ilchan.calendar_api.application.service;

import com.ilchan.calendar_api.domain.entity.Calendar;
import com.ilchan.calendar_api.domain.repository.CalendarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCalendarService {

    private final CalendarRepository calendarRepository;

    public CreateCalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    @Transactional
    public Calendar execute(String name, String description) {
        Calendar calendar = new Calendar(name, description);
        return calendarRepository.save(calendar);
    }
}
