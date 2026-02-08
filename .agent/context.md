# 프로젝트 컨텍스트 (MVP)

이 문서는 `calendar-api` 프로젝트의 목적, MVP 범위, 제외 범위, 기본 정책을 정의한다.
모든 개발자/AI 에이전트는 이 문서를 기준으로 기능을 설계/구현한다.

---

## 1. 목표

- 웹뷰 기반 일정관리 앱(클라이언트)을 위한 백엔드 API 제공
- 일정(Event)을 생성/수정/삭제/조회할 수 있어야 한다.
- “일단 잘 동작하는 개인용 캘린더 MVP”를 빠르게 만든다.

---

## 2. MVP 범위 (포함)

### 2.1 캘린더/일정 기본 기능
- 캘린더 생성/수정/삭제/조회
- 일정(Event) 생성/수정/삭제/단건조회/목록조회
- 기간 조회(예: start~end 구간 내 일정 리스트)
- 제목/메모(설명) 등 기본 필드 지원

### 2.2 데이터 일관성
- 일정 시작시간 < 종료시간 검증
- 동일 캘린더 내 데이터는 일관되게 조회되어야 한다.

### 2.3 운영 편의성
- 표준 에러 응답 포맷 제공
- 서버 로그/에러 로그 기본 체계 마련

---

## 3. MVP 제외 범위 (이번 단계에서는 하지 않음)

- 반복 일정(RRULE), 주기 생성
- 공유 캘린더, 다중 사용자 공유/권한
- 알림/푸시(FCM/APNS), 이메일 알림
- 외부 캘린더 동기화(구글/애플 캘린더)
- 초대(Invite), 참석자(Attendees)
- 태그/카테고리/색상 고도화
- 첨부파일
- 복잡한 검색(전문 검색 등)

---

## 4. 기본 가정 및 정책

### 4.1 사용자(User)
- MVP에서는 “인증/인가”를 깊게 구현하지 않는다.
- 다만 확장을 위해 데이터 모델에는 userId를 포함할 수 있다.
- (선택) 개발 편의를 위해 고정 userId(예: 1L)로 동작하도록 할 수 있다.

### 4.2 시간/타임존 정책
- API 입력/출력은 ISO-8601 포맷을 사용한다.
- 서버 기본 timezone은 Asia/Seoul을 기준으로 한다.
- (MVP) 일정 시간은 LocalDateTime 기반으로 처리한다.
    - 운영 확장 시 UTC(Instant)로 전환 가능하도록 설계를 단순하게 유지한다.

### 4.3 삭제 정책
- MVP에서는 Hard delete를 기본으로 한다.
- 운영 단계에서 Soft delete를 도입할 수 있도록 확장 여지는 남긴다.

### 4.4 성능/스케일
- MVP는 단일 DB(MySQL) 기준으로 설계한다.
- 조회 API는 기간 조건으로 인덱스가 효율적으로 타도록 설계한다.

---

## 5. 1차 릴리즈 목표 엔드포인트 (요약)

- 캘린더
    - POST /api/v1/calendars
    - GET  /api/v1/calendars
    - GET  /api/v1/calendars/{calendarId}
    - PATCH /api/v1/calendars/{calendarId}
    - DELETE /api/v1/calendars/{calendarId}

- 이벤트
    - POST /api/v1/calendars/{calendarId}/events
    - GET  /api/v1/calendars/{calendarId}/events?from=...&to=...
    - GET  /api/v1/events/{eventId}
    - PATCH /api/v1/events/{eventId}
    - DELETE /api/v1/events/{eventId}

(상세 규격은 api-contract.md에서 정의한다.)
