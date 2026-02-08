# Git Commit Convention

> 모든 커밋 메시지는 이 규칙을 따릅니다.  
> 규칙 기반: **Conventional Commits 1.0.0** + 실무 확장(스코프/예시/정책).

---

## 📌 커밋 메시지 구조

```
<type>(<scope>): <subject>

<body>

<footer>
```

### 예시 (KR)
```
feat(payment): 카드 결제 승인 API 추가

JWT 기반 승인 흐름 구현 및 응답 VO 정리
- POST /api/v1/payments/approve
- 승인추적번호(traceNo) 생성 로직 추가
- 타임아웃 3s → 5s 변경

Resolves: #123
```

### 예시 (EN)
```
refactor(domain): extract barcode approval service into separate module

- Move business rules to application layer
- Add unit tests for cancellation path

Refs: #456
```

---

## 🔖 Type (유형)

| Type        | 설명 |
|-------------|------|
| **feat**    | 새로운 기능 추가 |
| **fix**     | 버그 수정 |
| **docs**    | 문서/주석 수정 |
| **style**   | 코드 포맷팅, 세미콜론 등 (동작 영향 없음) |
| **refactor**| 리팩터링 (기능 변화 없음) |
| **perf**    | 성능 개선 |
| **test**    | 테스트 코드 추가/수정 |
| **build**   | 빌드 관련 수정 (gradle, npm, deps 등) |
| **ci**      | CI/CD 설정 변경 |
| **chore**   | 잡다한 변경 (설정, 스크립트 등) |
| **revert**  | 이전 커밋 되돌리기 |

---

## 🎯 Scope (영역)

- **계층**: `domain`, `application`, `infra`, `api`, `db`, `config`, `properties`
- **기능**: `auth`, `payment`, `barcode`, `card`, `settlement`, `pickup`
- **프론트엔드**: `ui`, `admin`, `table`, `i18n`, `excel`, `assets`
- **빌드/운영**: `gradle`, `wrapper`, `deps`, `docker`, `logging`

예시:
```
feat(payment): 결제 승인 API 추가
fix(db): index 누락으로 인한 성능 이슈 해결
chore(properties): graceful shutdown 설정 추가
```

---

## 📝 Subject 규칙

- 명령형 사용 (추가, 수정, 제거, 개선 등)
- 72자 이내 권장
- 마침표(`.`) 사용 금지
- 불필요 접두어(`[update]`, `[WIP]`) 금지

✅ 좋은 예
```
fix(db): 결제 테이블 FK 누락으로 인한 오류 수정
```

❌ 나쁜 예
```
update: 결제 수정함
```

---

## 📄 Body (본문)

- 변경 이유 / 배경 설명
- 주요 변경점 bullet 형식
- 영향 범위 / 제한 사항
- 테스트 결과

---

## 🔗 Footer (하단)

- 이슈 연동: `Resolves: #123`, `Refs: #456`
- Breaking Changes: `BREAKING CHANGE: <내용>`
- 공동 작업자: `Co-authored-by: 이름 <email>`

예시:
```
BREAKING CHANGE: /api/v1/login 응답 필드 'token' → 'accessToken'
```

---

## 🛠 자주 쓰는 매핑 예시

- `chore(properties): graceful shutdown 설정 추가`
- `build(wrapper): use Nexus proxy for Gradle distributionUrl`
- `chore(assets): update SEED partner card images`
- `chore(logging): 로그 메시지 수정`
- `docs: 주석 보강`
- `chore: remove unused source`
- `perf(db): add index for settlement query`
- `refactor(domain): 서비스 레이어 분리`
- `feat(admin-excel): 엑셀 다운로드 컬럼 추가`
- `feat(i18n): 번역 키 추가`
- `build(deps): bump spring-boot to 3.3.2`

---
