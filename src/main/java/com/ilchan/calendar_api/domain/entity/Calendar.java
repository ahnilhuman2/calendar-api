package com.ilchan.calendar_api.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "calendars")
public class Calendar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    protected Calendar() {
    }

    public Calendar(String name, String description) {
        validateName(name);
        this.name = name;
        this.description = description;
    }

    public void updateCalendar(String name, String description) {
        if (name != null) {
            validateName(name);
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("캘린더 이름은 필수입니다.");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("캘린더 이름은 100자를 초과할 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
