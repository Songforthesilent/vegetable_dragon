# 🐉 Vegetable Dragon

## 🎓 캡스톤디자인 (ETRI)

### 📌 주제: 인공지능 역기능 문제 해결을 위한 기술 및 서비스 개발  
### 🎯 목표: 집단 지성 기반 생성형 AI 판단 플랫폼  

---

## 🚀 지금까지 개발한 부분
1. **어그로성 기사 탐지 모델 초안 개발**
2. **Flask API 개발**  
   - 모델을 API 형태로 불러올 수 있도록 Flask 코드 작성  
3. **백엔드와 모델 연결**  
   - Spring Boot에서 Flask 서버를 호출하여 모델과 통신할 수 있도록 구현  

---

## ⚙️ 백엔드 흐름도

```mermaid
graph TD;
    A[사용자 글 작성 (Vue.js)] -->|전송| B[SpringBoot 서버]
    B -->|전달| C[Flask 서버 (ML 모델)]
    C -->|결과 반환| B
    B -->|결과 표시| A
```
## 🔄 시스템 흐름
**App.vue** ↔ **MLController** ↔ **MLService** ↔ **api-server.py**

---

## 📅 03.04 추가한 부분
### 🗄️ `news_ai_db` 생성 및 테이블 추가
- **생성된 테이블**:  
  - `UserFeedback`  
  - `NewsArticle`  

- ✨ **목적**:  
  - 사용자의 뉴스 반응 데이터를 AI 학습에 활용  

### 🛠️ 테스트 코드 작성
- Spring의 JPA 코드와 MySQL 간 연결이 **정상적으로 이루어지는지 확인 완료** ✅  

---

## 🔜 앞으로 추가해야 할 부분
- **`mlService`에서 프론트엔드로부터 사용자가 작성한 글과 반응 데이터를 받아 Repository에 저장하는 기능 구현** 🛠️  
