# SPRING ADVANCED

## 1. 코드 개선

- Early Return을 적용하여  조건에 맞지 않는 경우 즉시 리턴하여, 불필요한 로직 실행을 방지하고 성능 향상
- 불필요한 if-else를 제거하여 불필요한 분기 처리로 인한 성능저하 방지

* Validation을 추가해 입력 데이터의 유효성을 사전에 검사하여 예상치 못한 예외 방지

---

## 2. @EntityGraph를 적용하여 N+1 문제 해결

- `@EntityGraph` 기반의 구현으로 수정하여 가독성 및 유지보수성 향상

---

## 3. API 로깅

### **AOP를 활용한 로깅 구현**

- 어드민 API 실행 전후 요청/응답 데이터를 로깅
- `@Aspect` 활용하여 API 호출 정보 기록

#### **로깅 항목**

- 요청한 사용자의 ID
- API 요청 시각
- API 요청 URL
- 요청 본문 (`RequestBody`)
- 응답 본문 (`ResponseBody`)

---

## 4. Enum Class 를 통한 에러 메시지 관리

- [https://kyn1013.tistory.com/229](https://kyn1013.tistory.com/229)

---

## 5. 테스트 커버리지

![Image](https://github.com/user-attachments/assets/a8e79af0-3c0c-41f5-a714-4334a3c73680)
