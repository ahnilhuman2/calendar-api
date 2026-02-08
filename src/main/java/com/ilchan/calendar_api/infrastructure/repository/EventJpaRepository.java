package com.ilchan.calendar_api.infrastructure.repository;

import com.ilchan.calendar_api.domain.entity.Event;
import com.ilchan.calendar_api.domain.repository.EventRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventJpaRepository extends JpaRepository<Event, Long>, EventRepository {

    @Override
    @Query("SELECT e FROM Event e WHERE e.calendarId = :calendarId " +
           "AND e.startAt < :to AND e.endAt > :from " +
           "ORDER BY e.startAt ASC, e.id ASC")
    List<Event> findByCalendarIdAndPeriod(
        @Param("calendarId") Long calendarId,
        @Param("from") LocalDateTime from,
        @Param("to") LocalDateTime to
    );
}
