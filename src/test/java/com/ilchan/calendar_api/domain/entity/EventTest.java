package com.ilchan.calendar_api.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Event 도메인 테스트")
class EventTest {

    @Test
    @DisplayName("이벤트 생성 시 startAt이 endAt보다 이전이어야 한다")
    void createEvent_startAtBeforeEndAt_success() {
        // given
        Long calendarId = 1L;
        String title = "회의";
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 10, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 11, 0);

        // when
        Event event = new Event(calendarId, title, null, startAt, endAt, false);

        // then
        assertThat(event.getTitle()).isEqualTo("회의");
        assertThat(event.getStartAt()).isEqualTo(startAt);
        assertThat(event.getEndAt()).isEqualTo(endAt);
    }

    @Test
    @DisplayName("이벤트 생성 시 startAt이 endAt 이후이면 예외 발생")
    void createEvent_startAtAfterEndAt_throwException() {
        // given
        Long calendarId = 1L;
        String title = "회의";
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 11, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 10, 0);

        // when & then
        assertThatThrownBy(() -> new Event(calendarId, title, null, startAt, endAt, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작 시간은 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("이벤트 생성 시 startAt과 endAt이 같으면 예외 발생")
    void createEvent_startAtEqualsEndAt_throwException() {
        // given
        Long calendarId = 1L;
        String title = "회의";
        LocalDateTime sameTime = LocalDateTime.of(2026, 2, 10, 10, 0);

        // when & then
        assertThatThrownBy(() -> new Event(calendarId, title, null, sameTime, sameTime, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작 시간은 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("이벤트 수정 시 startAt만 변경해도 시간 검증이 수행된다")
    void updateEvent_onlyStartAt_validated() {
        // given
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 10, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 11, 0);
        Event event = new Event(1L, "회의", null, startAt, endAt, false);

        // when
        LocalDateTime newStartAt = LocalDateTime.of(2026, 2, 10, 12, 0); // endAt(11:00)보다 늦음

        // then
        assertThatThrownBy(() -> event.updateEvent(null, null, newStartAt, null, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작 시간은 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("이벤트 수정 시 endAt만 변경해도 시간 검증이 수행된다")
    void updateEvent_onlyEndAt_validated() {
        // given
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 10, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 11, 0);
        Event event = new Event(1L, "회의", null, startAt, endAt, false);

        // when
        LocalDateTime newEndAt = LocalDateTime.of(2026, 2, 10, 9, 0); // startAt(10:00)보다 이른 시간

        // then
        assertThatThrownBy(() -> event.updateEvent(null, null, null, newEndAt, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("시작 시간은 종료 시간보다 이전이어야 합니다.");
    }

    @Test
    @DisplayName("제목이 null이면 예외 발생")
    void createEvent_titleNull_throwException() {
        // given
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 10, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 11, 0);

        // when & then
        assertThatThrownBy(() -> new Event(1L, null, null, startAt, endAt, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이벤트 제목은 필수입니다.");
    }

    @Test
    @DisplayName("제목이 빈 문자열이면 예외 발생")
    void createEvent_titleBlank_throwException() {
        // given
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 10, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 11, 0);

        // when & then
        assertThatThrownBy(() -> new Event(1L, "   ", null, startAt, endAt, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이벤트 제목은 필수입니다.");
    }

    @Test
    @DisplayName("제목이 200자를 초과하면 예외 발생")
    void createEvent_titleTooLong_throwException() {
        // given
        String longTitle = "a".repeat(201);
        LocalDateTime startAt = LocalDateTime.of(2026, 2, 10, 10, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 2, 10, 11, 0);

        // when & then
        assertThatThrownBy(() -> new Event(1L, longTitle, null, startAt, endAt, false))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이벤트 제목은 200자를 초과할 수 없습니다.");
    }
}
