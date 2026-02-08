package com.ilchan.calendar_api.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "events",
    indexes = {
        @Index(name = "idx_events_calendar_start", columnList = "calendar_id, start_at"),
        @Index(name = "idx_events_calendar_end", columnList = "calendar_id, end_at")
    }
)
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calendar_id", nullable = false)
    private Long calendarId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "all_day", nullable = false)
    private boolean allDay = false;

    protected Event() {
    }

    public Event(Long calendarId, String title, String description, LocalDateTime startAt, LocalDateTime endAt, boolean allDay) {
        validateTitle(title);
        validateTimeRange(startAt, endAt);
        this.calendarId = calendarId;
        this.title = title;
        this.description = description;
        this.startAt = startAt;
        this.endAt = endAt;
        this.allDay = allDay;
    }

    public void updateEvent(String title, String description, LocalDateTime startAt, LocalDateTime endAt, Boolean allDay) {
        if (title != null) {
            validateTitle(title);
            this.title = title;
        }
        if (description != null) {
            this.description = description;
        }

        LocalDateTime newStartAt = startAt != null ? startAt : this.startAt;
        LocalDateTime newEndAt = endAt != null ? endAt : this.endAt;
        validateTimeRange(newStartAt, newEndAt);

        if (startAt != null) {
            this.startAt = startAt;
        }
        if (endAt != null) {
            this.endAt = endAt;
        }
        if (allDay != null) {
            this.allDay = allDay;
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("이벤트 제목은 필수입니다.");
        }
        if (title.length() > 200) {
            throw new IllegalArgumentException("이벤트 제목은 200자를 초과할 수 없습니다.");
        }
    }

    private void validateTimeRange(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt == null || endAt == null) {
            throw new IllegalArgumentException("시작 시간과 종료 시간은 필수입니다.");
        }
        if (!startAt.isBefore(endAt)) {
            throw new IllegalArgumentException("시작 시간은 종료 시간보다 이전이어야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public boolean isAllDay() {
        return allDay;
    }
}
