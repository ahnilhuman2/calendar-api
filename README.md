# Calendar API

웹뷰 기반 일정관리 앱을 위한 백엔드 REST API (MVP)

## 📋 프로젝트 개요

- **목표**: 개인용 캘린더/일정 관리 API 제공
- **아키텍처**: DDD (Domain-Driven Design)
- **기술 스택**:
  - Java 21
  - Spring Boot 4.0.2
  - Spring Data JPA
  - MySQL 8.x
  - Gradle

---

## 🚀 로컬 실행 방법

### 1. 사전 준비

#### MySQL 설치 및 실행
```bash
# macOS (Homebrew)
brew install mysql
brew services start mysql

# Ubuntu/Debian
sudo apt-get install mysql-server
sudo systemctl start mysql

# Windows
# MySQL Installer를 통해 설치 및 실행
```

#### 데이터베이스 및 사용자 생성
```bash
# MySQL 접속
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE calendar CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 사용자 생성 및 권한 부여
CREATE USER 'calendar'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON calendar.* TO 'calendar'@'localhost';
FLUSH PRIVILEGES;

# 확인
SHOW DATABASES;
SELECT user, host FROM mysql.user WHERE user = 'calendar';

# 종료
EXIT;
```

---

### 2. 프로젝트 클론 및 설정

```bash
# 프로젝트 클론 (또는 이미 있으면 생략)
git clone <repository-url>
cd calendar-api

# application.yml 확인
# src/main/resources/application.yml 파일이 다음 내용을 포함하는지 확인
```

#### `application.yml` 예시
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/calendar?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: calendar
    password: "1234"
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update  # 최초 실행 시 테이블 자동 생성
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        default_batch_fetch_size: 100

  jackson:
    time-zone: Asia/Seoul
    serialization:
      write-dates-as-timestamps: false

server:
  port: 8080

logging:
  level:
    com.ilchan.calendar_api: INFO
    org.hibernate.SQL: DEBUG
```

> ⚠️ **주의**: 운영 환경에서는 `ddl-auto: validate` 또는 `none`으로 변경하고, 비밀번호는 환경 변수로 관리하세요.

---

### 3. 빌드 및 실행

#### Gradle 명령어 (터미널)
```bash
# 빌드 (테스트 포함)
./gradlew clean build

# 빌드 (테스트 제외)
./gradlew clean build -x test

# 실행
./gradlew bootRun

# 또는 JAR 실행
java -jar build/libs/calendar-api-0.0.1-SNAPSHOT.jar
```

#### IntelliJ IDEA 실행
1. IntelliJ IDEA에서 프로젝트 열기
2. `src/main/java/com/ilchan/calendar_api/CalendarApiApplication.java` 우클릭
3. `Run 'CalendarApiApplication'` 클릭

---

### 4. 서버 실행 확인

```bash
# 서버 정상 실행 확인
curl http://localhost:8080/api/v1/calendars

# 응답 예시 (빈 배열)
[]
```

---

## 🧪 테스트 실행

### 전체 테스트 실행
```bash
./gradlew test
```

### 특정 테스트 클래스만 실행
```bash
# 도메인 규칙 테스트
./gradlew test --tests EventTest

# 기간 조회 overlap 테스트
./gradlew test --tests EventJpaRepositoryTest

# 서비스 레이어 테스트
./gradlew test --tests CreateCalendarServiceTest
```

### 테스트 리포트 확인
```bash
# 테스트 실행 후 리포트 생성 위치
open build/reports/tests/test/index.html
```

---

## 📡 API 엔드포인트

### 캘린더 (Calendar)
| Method | Path | 설명 |
|--------|------|------|
| POST | /api/v1/calendars | 캘린더 생성 |
| GET | /api/v1/calendars | 캘린더 목록 조회 |
| GET | /api/v1/calendars/{calendarId} | 캘린더 단건 조회 |

### 이벤트 (Event)
| Method | Path | 설명 |
|--------|------|------|
| POST | /api/v1/calendars/{calendarId}/events | 이벤트 생성 |
| GET | /api/v1/calendars/{calendarId}/events?from=&to= | 이벤트 기간 조회 |
| GET | /api/v1/events/{eventId} | 이벤트 단건 조회 |
| PATCH | /api/v1/events/{eventId} | 이벤트 수정 |
| DELETE | /api/v1/events/{eventId} | 이벤트 삭제 |

---

## 📦 프로젝트 구조 (DDD)

```
src/main/java/com/ilchan/calendar_api/
├── api/                        # API 계층
│   ├── controller/             # REST 컨트롤러
│   ├── request/                # 요청 DTO
│   ├── response/               # 응답 DTO
│   └── exception/              # 예외 처리 및 에러 응답
├── application/                # Application 계층
│   └── service/                # 유스케이스 서비스 (트랜잭션 관리)
├── domain/                     # Domain 계층
│   ├── entity/                 # 도메인 엔티티 (비즈니스 로직 포함)
│   └── repository/             # Repository 인터페이스
└── infrastructure/             # Infrastructure 계층
    ├── repository/             # JPA Repository 구현체
    └── config/                 # 설정 클래스
```

---

## ✅ 로컬 검증 체크리스트

### 1단계: DB 준비
- [ ] MySQL 서버 실행 중
- [ ] `calendar` 데이터베이스 생성 완료
- [ ] `calendar` 사용자 생성 및 권한 부여 완료
- [ ] `application.yml` 설정 확인 (username/password)

### 2단계: 빌드 & 테스트
- [ ] `./gradlew clean build` 성공
- [ ] 테스트 전체 통과 (11개 이상)
- [ ] 빌드 결과물 확인: `build/libs/calendar-api-0.0.1-SNAPSHOT.jar`

### 3단계: 서버 실행
- [ ] `./gradlew bootRun` 또는 IntelliJ 실행
- [ ] 콘솔에 "Started CalendarApiApplication" 메시지 확인
- [ ] 포트 8080 리스닝 확인

### 4단계: API 테스트 (curl)

#### 캘린더 생성
```bash
curl -X POST http://localhost:8080/api/v1/calendars \
  -H "Content-Type: application/json" \
  -d '{
    "name": "내 캘린더",
    "description": "테스트용"
  }'

# 응답 확인 (201 Created, id 포함)
```

#### 캘린더 목록 조회
```bash
curl http://localhost:8080/api/v1/calendars

# 응답 확인 (200 OK, 방금 생성한 캘린더 포함)
```

#### 이벤트 생성 (calendarId는 위에서 받은 값으로 대체)
```bash
curl -X POST http://localhost:8080/api/v1/calendars/1/events \
  -H "Content-Type: application/json" \
  -d '{
    "title": "주간 회의",
    "description": "팀 미팅",
    "startAt": "2026-02-10T10:00:00",
    "endAt": "2026-02-10T11:00:00",
    "allDay": false
  }'

# 응답 확인 (201 Created)
```

#### 이벤트 기간 조회
```bash
curl "http://localhost:8080/api/v1/calendars/1/events?from=2026-02-10T00:00:00&to=2026-02-10T23:59:59"

# 응답 확인 (200 OK, startAt 오름차순 정렬)
```

---

## 🔍 트러블슈팅

### MySQL 연결 실패
```
Error: Communications link failure
```
**해결 방법**:
1. MySQL 서버 실행 확인: `mysql -u calendar -p`
2. `application.yml`의 username/password 확인
3. 포트 확인 (기본: 3306)

### 테이블 생성 안 됨
```
Table 'calendar.calendars' doesn't exist
```
**해결 방법**:
1. `ddl-auto: update` 설정 확인
2. 서버 재시작
3. 또는 수동 생성: `.agent/data-model.md` 참고

### 포트 이미 사용 중
```
Port 8080 is already in use
```
**해결 방법**:
1. 다른 프로세스 종료: `lsof -ti:8080 | xargs kill -9`
2. 또는 `application.yml`에서 `server.port` 변경

---

## 📚 참고 문서

- [API 계약 (api-contract.md)](.agent/api-contract.md)
- [Use Cases (use-cases.md)](.agent/use-cases.md)
- [아키텍처 (architecture.md)](.agent/architecture.md)
- [코드 스타일 (style.md)](.agent/style.md)

---

## 📝 라이선스

This project is for educational purposes.
