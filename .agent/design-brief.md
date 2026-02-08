# Design Brief (MVP)

이 문서는 `calendar-api`의 MVP 설계를 위해 Claude/개발자가 따라야 하는 설계 브리프이다.
이미 정의된 context/api-contract/domain-model 문서를 기반으로, 구현을 위한 구체 가이드를 제공한다.

---

## 1. 사용자 시나리오 (MVP)

### S1. 캘린더 만들기
- 사용자는 개인 캘린더를 만든다.
- 캘린더 이름은 필수이며, 기본 설명은 선택이다.

### S2. 일정 등록하기
- 사용자는 특정 캘린더에 일정을 등록한다.
- 일정은 시작시간과 종료시간이 있어야 하며, 시작 < 종료 조건을 만족해야 한다.
- 종일 일정(allDay)은 boolean으로만 처리하며, 상세 정책 고도화는 2nd phase로 미룬다.

### S3. 일정 조회하기 (기간)
- 사용자는 캘린더에서 특정 기간(from~to)의 일정을 조회한다.
- 기간과 “겹치는” 일정은 모두 반환한다(Overlap 규칙).

### S4. 일정 수정/삭제하기
- 사용자는 일정의 제목/시간/설명을 수정할 수 있다.
- 일정은 삭제할 수 있다(MVP: hard delete).

---

## 2. 핵심 정책 (MVP에서 반드시 고정)

### 2.1 시간/타임존
- 서버 timezone: Asia/Seoul
- API 시간 포맷: ISO-8601 문자열
- 내부 저장: LocalDateTime 기반(향후 UTC 전환 고려)

### 2.2 기간 조회 규칙 (Overlap)
- 기간 조회는 “겹치는 일정”을 반환한다.
- 겹침 조건:
    - event.startAt < to AND event.endAt > from

### 2.3 데이터 정렬
- 기본 정렬은 startAt 오름차순
- 동일 startAt일 경우 id 오름차순(안정 정렬)

---

## 3. 설계 결정을 미리 고정하는 것 (중요)

### 3.1 응답 래퍼
- MVP에서는 응답 래퍼를 사용하지 않는다(api-contract 기준)

### 3.2 삭제 정책
- MVP: hard delete
- 확장: soft delete 가능성은 문서/코드 구조로만 고려(필드/조건은 나중에 도입)

### 3.3 인증/인가
- MVP: 인증은 구현하지 않는다.
- 확장 대비: calendar에는 userId 필드를 둘 수 있다(선택).
- (선택) 개발 편의를 위해 고정 userId(예: 1L)로 처리 가능.

---

## 4. 구현 우선순위(추천)

1) Calendar CRUD (최소: create/list/get)
2) Event CRUD (create/get/update/delete)
3) Event 기간 조회
4) 전역 예외 처리 + 표준 에러 포맷
5) 테스트(도메인 검증/기간 조회)

---

## 5. 2nd Phase 후보(문서만 유지, 구현 금지)

- 반복 일정(RRULE)
- 공유/초대/참석자
- 알림/푸시
- 외부 캘린더 연동
