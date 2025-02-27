# vegetable_dragon

캡스톤디자인(ETRI)

주제: 인공지능 역기능 문제 해결을 위한 기술 및 서비스 개발

목표: 집단 지성 기반 생성형 AI 판단 플랫폼

**지금까지 개발한 부분**
1. 어그로성 기사 탐지 모델 초안 개발
2. 모델을 api로 불러올 수 있도록 flask 코드 작성
3. 해당 모델과 백엔드가 통신할 수 있도록 연결

**백엔드 흐름도**
1. 사용자가 글을 쓴다. (Vue.js)
2. 해당 글을 서버로 보낸다. (SpringBoot)
3. 서버에서 받아서 flask 서버를 불러서 모델에 글을 전달한다. (flask)
4. 글을 전달받은 후에 결과값을 서버에 다시 보낸다. (SpringBoot)
5. 결과값을 화면에 표시한다. (Vue.js)

App.vue <-> MLController <-> MLService <-> api-server.py 
