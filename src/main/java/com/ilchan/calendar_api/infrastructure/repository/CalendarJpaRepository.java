package com.ilchan.calendar_api.infrastructure.repository;

import com.ilchan.calendar_api.domain.entity.Calendar;
import com.ilchan.calendar_api.domain.repository.CalendarRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarJpaRepository extends JpaRepository<Calendar, Long>, CalendarRepository {
}
