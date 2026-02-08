# 코드 스타일 가이드

이 문서는 `calendar-api` 프로젝트의 코딩 규칙과 개발 원칙을 정의한다.  
모든 개발자와 AI 에이전트는 이 가이드를 반드시 따른다.

---

## 1. 기술 스택

- 언어: Java 17+ (또는 Kotlin 선택 시 별도 명시)
- 프레임워크: Spring Boot
- 아키텍처: DDD (Domain-Driven Design)
- 빌드 도구: Gradle
- 데이터베이스: RDBMS (MySQL / PostgreSQL)

---

## 2. 기본 패키지 구조

모든 패키지는 다음 구조를 따른다.

com.<company>.calendar

예시:
com.example.calendar

---

## 3. 계층 구조 (DDD)

각 기능은 다음 계층 구조를 따른다.

domain
├─ entity
├─ repository
└─ service (도메인 서비스)

application
├─ service (유스케이스)
├─ dto
└─ facade (선택)

infrastructure
├─ repository (JPA 구현체)
├─ config
└─ external

api
├─ controller
├─ request
└─ response

---

## 4. 네이밍 규칙

### 4.1 클래스 네이밍

Entity: User, Calendar, Event  
Repository: UserRepository  
도메인 서비스: EventDomainService  
애플리케이션 서비스: CreateEventService  
컨트롤러: EventController  
Request DTO: CreateEventRequest  
Response DTO: EventResponse

---

### 4.2 메서드 네이밍

- 동사로 시작한다.
- 의도가 드러나는 이름을 사용한다.

좋은 예:
createEvent()
cancelEvent()
findEventsByUserId()

나쁜 예:
doEvent()
process()
handleData()

---

## 5. Controller 규칙

- Controller는 반드시 얇게 유지한다.
- 비즈니스 로직을 포함하지 않는다.
- 역할:
    - 요청 검증
    - 애플리케이션 서비스 호출
    - 응답 변환

---

## 6. Service 규칙

### 애플리케이션 서비스
- 유스케이스를 조합한다.
- 트랜잭션을 관리한다.
- 도메인 서비스를 호출한다.

### 도메인 서비스
- 순수 비즈니스 로직만 포함한다.
- Spring 의존성을 갖지 않는다.

---

## 7. Entity 규칙

- Entity는 비즈니스 로직을 포함해야 한다.
- 빈약한 도메인 모델(anemic model)을 지양한다.
- Controller/Service 어노테이션 사용 금지

---

## 8. DTO 규칙

- Entity를 API에 직접 노출하지 않는다.
- Request/Response 객체를 반드시 사용한다.
- 가능하면 불변 객체로 설계한다.

---

## 9. 예외 처리

- 커스텀 예외를 사용한다.
- 클라이언트에 Raw Exception을 노출하지 않는다.

예시:
EventNotFoundException
InvalidScheduleException

전역 처리:
GlobalExceptionHandler

---

## 10. 로깅 규칙

- 비즈니스 이벤트: INFO
- 오류 상황: ERROR
- 민감 정보 로그 금지

---

## 11. 트랜잭션 규칙

- @Transactional은 애플리케이션 서비스에서만 사용
- Domain 계층은 Spring에 의존하지 않는다.

---

## 12. 테스트 네이밍

패턴:

메서드명_조건_기대결과

예시:

createEvent_과거날짜_예외발생
cancelEvent_이미취소됨_실패

---

## 13. 일반 원칙

- 가독성을 최우선으로 한다.
- 메서드는 30줄 이하 권장
- 클래스는 하나의 책임만 가진다.
- static 상태 사용 금지
- 깊은 상속 구조 지양

---

## 14. AI 에이전트 규칙

AI가 생성하는 코드는 반드시:

- 이 스타일 가이드를 준수해야 한다.
- DDD 구조를 지켜야 한다.
- 임의의 패턴을 도입하지 않는다.
- 일관성을 창의성보다 우선한다.

