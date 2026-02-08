# 시스템 아키텍처

이 문서는 `calendar-api` 프로젝트의 전체 아키텍처와 설계 원칙을 정의한다.

---

## 1. 아키텍처 개요

본 시스템은 다음 구조를 따른다.

- 계층형 아키텍처 (Layered Architecture)
- DDD (Domain-Driven Design)
- 필요 시 Hexagonal 구조로 확장 가능

전체 흐름:

클라이언트 (WebView / Mobile)
↓
API 계층
↓
Application 계층
↓
Domain 계층
↓
Infrastructure 계층
↓
Database

---

## 2. 계층별 역할

### 2.1 API 계층

위치:
api/

역할:
- REST 엔드포인트 제공
- 요청 검증
- 응답 포맷 변환
- 비즈니스 로직 금지

---

### 2.2 Application 계층

위치:
application/

역할:
- 유스케이스 조합
- 트랜잭션 관리
- 도메인 서비스 호출
- DTO ↔ 도메인 객체 변환

예시:
CreateEventService
UpdateScheduleService

---

### 2.3 Domain 계층

위치:
domain/

역할:
- 핵심 비즈니스 로직
- 엔티티, 값 객체
- 도메인 서비스
- Repository 인터페이스

규칙:
- Spring 의존 금지
- Infrastructure 의존 금지

---

### 2.4 Infrastructure 계층

위치:
infrastructure/

역할:
- JPA Repository 구현
- 외부 API 연동
- DB 설정
- 캐시, 메시징 등 기술 요소

---

## 3. 핵심 도메인 개념

### 주요 Aggregate

User
- 시스템 사용자

Calendar
- 사용자 소유
- 여러 개의 Event 포함

Event
- 하나의 Calendar에 속함
- 속성:
    - 제목
    - 시작 시간
    - 종료 시간
    - 상태

---

## 4. 이벤트 생성 흐름 예시

1. 클라이언트 → POST /events
2. Controller가 요청 수신
3. Controller가 CreateEventService 호출
4. Application Service:
    - Calendar 조회
    - Event 생성
    - Repository 저장
5. 응답 반환

---

## 5. 트랜잭션 경계

- Application Service 단위로 설정
- 하나의 유스케이스 = 하나의 트랜잭션

예시:
CreateEventService → @Transactional

---

## 6. 모듈 전략 (향후 확장)

초기:
calendar-api (단일 모듈)

확장 시:

calendar-platform
├─ calendar-api
├─ calendar-domain
├─ calendar-batch
└─ calendar-admin

---

## 7. 데이터베이스 설계 방향

- RDBMS (MySQL / PostgreSQL)
- JPA/Hibernate 사용
- Aggregate 중심 테이블 설계

예시:

calendar
id
user_id
name
created_at

event
id
calendar_id
title
start_at
end_at
status
created_at

---

## 8. API 버저닝

기본 경로:

/api/v1

예시:

POST   /api/v1/events
GET    /api/v1/events/{id}
DELETE /api/v1/events/{id}

---

## 9. 보안 (향후 단계)

예정:

- JWT 기반 인증
- 사용자별 캘린더 접근 제어
- 역할 기반 권한 (선택)

---

## 10. 비기능 원칙

- Stateless API
- 수평 확장 가능 구조
- 명확한 계층 분리
- 테스트 가능한 도메인 로직

---

## 11. AI 에이전트 규칙

AI는 다음을 반드시 지켜야 한다.

- 정의된 아키텍처를 준수한다.
- 계층을 무시하지 않는다.
- Controller에 비즈니스 로직을 넣지 않는다.
- Controller에서 Repository 직접 호출 금지
- 반드시 Application Service를 통해 처리한다.
