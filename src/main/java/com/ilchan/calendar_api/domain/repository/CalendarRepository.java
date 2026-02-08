package com.ilchan.calendar_api.domain.repository;

import com.ilchan.calendar_api.domain.entity.Calendar;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository {

    Calendar save(Calendar calendar);

    Optional<Calendar> findById(Long id);

    List<Calendar> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
