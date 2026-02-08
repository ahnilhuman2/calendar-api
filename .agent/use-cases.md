# Use Cases (MVP)

이 문서는 MVP 구현 범위를 “유스케이스 단위”로 나눈다.
각 유스케이스는 application service 1개에 대응하는 것을 권장한다.

---

## UC-01 캘린더 생성
Endpoint:
- POST /api/v1/calendars

Input:
- name (필수, 1~100)
- description (옵션)

Rules:
- name은 공백 불가

Output:
- 생성된 캘린더 리소스(JSON)

Errors:
- 400: name validation 실패

---

## UC-02 캘린더 목록 조회
Endpoint:
- GET /api/v1/calendars

Rules:
- (MVP) 전체 목록 반환 (user 필터는 2nd phase)

Output:
- 캘린더 배열(JSON)

---

## UC-03 캘린더 단건 조회
Endpoint:
- GET /api/v1/calendars/{calendarId}

Errors:
- 404: 캘린더 없음

---

## UC-04 이벤트 생성
Endpoint:
- POST /api/v1/calendars/{calendarId}/events

Input:
- title (필수, 1~200)
- description (옵션)
- startAt (필수)
- endAt (필수)
- allDay (옵션, default false)

Rules:
- startAt < endAt
- calendarId는 존재해야 함

Output:
- 생성된 이벤트 리소스(JSON)

Errors:
- 400: validation/time range
- 404: calendar 없음

---

## UC-05 캘린더 내 이벤트 기간 조회
Endpoint:
- GET /api/v1/calendars/{calendarId}/events?from=...&to=...

Rules:
- from, to 필수
- overlap 조건: event.startAt < to AND event.endAt > from
- 기본 정렬: startAt ASC, id ASC

Output:
- 이벤트 배열(JSON)

Errors:
- 400: 파라미터 누락/형식 오류
- 404: calendar 없음

---

## UC-06 이벤트 단건 조회
Endpoint:
- GET /api/v1/events/{eventId}

Errors:
- 404: event 없음

---

## UC-07 이벤트 수정
Endpoint:
- PATCH /api/v1/events/{eventId}

Input:
- title/description/startAt/endAt/allDay (모두 옵션)
  Rules:
- startAt/endAt 둘 다 변경되는 경우 startAt < endAt 검증
- calendarId 변경은 MVP에서 금지(필요 시 2nd phase)

Errors:
- 400: validation/time range
- 404: event 없음

---

## UC-08 이벤트 삭제
Endpoint:
- DELETE /api/v1/events/{eventId}
  Output:
- 204 No Content

Errors:
- 404: event 없음
