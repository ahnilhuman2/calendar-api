# API 계약(Contract)

이 문서는 `calendar-api`의 REST API 규칙, 요청/응답 포맷, 에러 포맷을 정의한다.
모든 Controller/DTO는 이 규칙을 따른다.

---

## 1. 공통 규칙

### 1.1 Base Path
- 모든 API는 `/api/v1`로 시작한다.

### 1.2 Content-Type
- Request: application/json
- Response: application/json

### 1.3 날짜/시간 포맷
- ISO-8601 문자열 사용
- 예: 2026-02-08T15:30:00
- 쿼리 파라미터 `from`, `to`도 동일 포맷 사용

---

## 2. 응답 포맷 정책

MVP에서는 “래퍼 없이” 리소스 기반 응답을 사용한다.
- 단건 조회: 리소스 JSON
- 목록 조회: 배열 JSON 또는 페이징 객체(JSON)

(추후 필요 시 공통 래퍼 도입 가능)

---

## 3. HTTP Status 규칙

- 200 OK: 조회/수정 성공
- 201 Created: 생성 성공
- 204 No Content: 삭제 성공
- 400 Bad Request: 유효성 검증 실패 / 파라미터 오류
- 404 Not Found: 리소스 없음
- 409 Conflict: 상태 충돌
- 500 Internal Server Error: 예기치 못한 서버 오류

---

## 4. 에러 응답 포맷 (표준)

에러는 아래 형식으로 응답한다.

예시 (필드 오류 포함):

{
"errorCode": "CALENDAR-400-001",
"message": "요청 값이 올바르지 않습니다.",
"fieldErrors": [
{ "field": "title", "reason": "must not be blank" }
]
}

예시 (필드 오류 없음):

{
"errorCode": "EVENT-404-001",
"message": "이벤트를 찾을 수 없습니다."
}

### 4.1 errorCode 규칙
- 형식: DOMAIN-HTTP-SEQ
- 예:
    - EVENT-400-001
    - CALENDAR-404-001
    - EVENT-409-001

구성 요소 설명:
- DOMAIN: 도메인 이름 (CALENDAR, EVENT 등)
- HTTP: HTTP 상태 코드 (400, 404, 409 등)
- SEQ: 해당 도메인 내 에러 순번 (001부터 시작)

### 4.2 fieldErrors 규칙
- 유효성 오류가 있을 때만 포함한다.
- 유효성 오류가 없으면 fieldErrors 필드는 생략 가능하다.

---

## 5. 엔드포인트 계약 (MVP)

### 5.1 캘린더

#### 5.1.1 캘린더 생성
POST /api/v1/calendars

Request:
{
"name": "내 캘린더",
"description": "개인 일정"
}

Response (201):
{
"id": 1,
"name": "내 캘린더",
"description": "개인 일정",
"createdAt": "2026-02-08T15:00:00",
"updatedAt": "2026-02-08T15:00:00"
}

Validation:
- name: 필수, 1~100자

---

#### 5.1.2 캘린더 목록 조회
GET /api/v1/calendars

Response (200):
[
{
"id": 1,
"name": "내 캘린더",
"description": "개인 일정",
"createdAt": "2026-02-08T15:00:00",
"updatedAt": "2026-02-08T15:00:00"
}
]

---

#### 5.1.3 캘린더 단건 조회
GET /api/v1/calendars/{calendarId}

---

#### 5.1.4 캘린더 수정
PATCH /api/v1/calendars/{calendarId}

Request:
{
"name": "수정된 이름",
"description": "수정된 설명"
}

---

#### 5.1.5 캘린더 삭제
DELETE /api/v1/calendars/{calendarId}

Response:
204 No Content

---

### 5.2 이벤트(Event)

#### 5.2.1 이벤트 생성
POST /api/v1/calendars/{calendarId}/events

Request:
{
"title": "회의",
"description": "주간 회의",
"startAt": "2026-02-09T10:00:00",
"endAt": "2026-02-09T11:00:00",
"allDay": false
}

Response (201):
{
"id": 100,
"calendarId": 1,
"title": "회의",
"description": "주간 회의",
"startAt": "2026-02-09T10:00:00",
"endAt": "2026-02-09T11:00:00",
"allDay": false,
"createdAt": "2026-02-08T15:10:00",
"updatedAt": "2026-02-08T15:10:00"
}

Validation:
- title: 필수, 1~200자
- startAt/endAt: 필수, startAt < endAt
- allDay: boolean

---

#### 5.2.2 캘린더 내 이벤트 기간 조회
GET /api/v1/calendars/{calendarId}/events?from=...&to=...

Query:
- from: ISO-8601 (inclusive)
- to: ISO-8601 (exclusive 권장)

Response (200):
[
{
"id": 100,
"calendarId": 1,
"title": "회의",
"description": "주간 회의",
"startAt": "2026-02-09T10:00:00",
"endAt": "2026-02-09T11:00:00",
"allDay": false,
"createdAt": "2026-02-08T15:10:00",
"updatedAt": "2026-02-08T15:10:00"
}
]

---

#### 5.2.3 이벤트 단건 조회
GET /api/v1/events/{eventId}

---

#### 5.2.4 이벤트 수정
PATCH /api/v1/events/{eventId}

Request:
{
"title": "회의(변경)",
"description": "회의 내용 변경",
"startAt": "2026-02-09T10:30:00",
"endAt": "2026-02-09T11:30:00",
"allDay": false
}

---

#### 5.2.5 이벤트 삭제
DELETE /api/v1/events/{eventId}

Response:
204 No Content
