# 데이터 모델(초안) - MySQL

이 문서는 MVP 구현을 위한 테이블/컬럼/인덱스 초안을 정의한다.
(실제 구현은 JPA 엔티티 기준으로 진행하되, 조회 패턴을 고려해 인덱스를 맞춘다.)

---

## 1. calendars

테이블명: calendars

컬럼:
- id BIGINT PK AUTO_INCREMENT
- user_id BIGINT NULL (MVP 옵션)
- name VARCHAR(100) NOT NULL
- description VARCHAR(255) NULL
- created_at DATETIME NOT NULL
- updated_at DATETIME NOT NULL

인덱스:
- (선택) idx_calendars_user_id (user_id)

---

## 2. events

테이블명: events

컬럼:
- id BIGINT PK AUTO_INCREMENT
- calendar_id BIGINT NOT NULL (FK)
- title VARCHAR(200) NOT NULL
- description TEXT NULL
- start_at DATETIME NOT NULL
- end_at DATETIME NOT NULL
- all_day TINYINT(1) NOT NULL DEFAULT 0
- created_at DATETIME NOT NULL
- updated_at DATETIME NOT NULL

제약:
- FK: events.calendar_id -> calendars.id
- (권장) start_at < end_at 는 애플리케이션 레벨에서 검증

인덱스(기간 조회 성능용):
- idx_events_calendar_start (calendar_id, start_at)
- idx_events_calendar_end   (calendar_id, end_at)

기간 조회(overlap) 조건:
- start_at < :to AND end_at > :from
- calendar_id = :calendarId

---

## 3. 데이터 타입 정책
- 시간은 DATETIME 사용
- 서버 timezone: Asia/Seoul
- created_at/updated_at은 애플리케이션에서 생성/수정 시점에 세팅
