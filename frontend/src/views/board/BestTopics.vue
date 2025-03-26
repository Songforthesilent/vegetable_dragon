<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <div class="best-topics-container">
        <h1 class="page-title">Best Topics</h1>

        <section class="topics-grid">
          <div v-for="topic in bestTopics" :key="topic.id" class="topic-card">
            <img :src="topic.image" :alt="topic.title" class="topic-image" />
            <div class="topic-info">
              <p class="topic-author">{{ topic.author }}</p> <!-- 작성자 추가 -->
              <h3 class="topic-title">{{ topic.title }}</h3>
            </div>
          </div>
        </section>
      </div>
    </article>

    <!-- 로그인 배너 영역 -->
    <aside class="login-panel">
      <div class="login-box">
        <i class="fas fa-user-circle user-icon"></i>
        <h3>Sign In</h3>
        <div class="input-container">
          <i class="fas fa-user icon"></i>
          <input type="text" placeholder="username" class="input-field username" />
        </div>

        <div class="input-container">
          <i class="fas fa-lock icon"></i>
          <input type="password" placeholder="password" class="input-field password" />
        </div>
        <button class="login-button">로그인</button>
        <div class="links">
          <a href="#">회원가입하기</a>
          <a href="#">비밀번호찾기</a>
        </div>
      </div>
    </aside>
  </div>
</template>

<script>
export default {
  data() {
    return {
      bestTopics: [
        {
          id: 1,
          author: "김철수",
          title: "Vue.js는 무엇인가?",
          image: "https://via.placeholder.com/300"
        },
        {
          id: 2,
          author: "이영희",
          title: "Vue Router 사용법",
          image: "https://via.placeholder.com/300"
        },
        {
          id: 3,
          author: "박민수",
          title: "Vuex 상태관리",
          image: "https://via.placeholder.com/300"
        },
        {
          id: 4,
          author: "정다은",
          title: "Composition API 소개",
          image: "https://via.placeholder.com/300"
        },
        {
          id: 5,
          author: "손흥민",
          title: "Pinia 상태관리",
          image: "https://via.placeholder.com/300"
        },
        {
          id: 6,
          author: "유재석",
          title: "Vue와 TypeScript",
          image: "https://via.placeholder.com/300"
        }
      ]
    };
  }
};
</script>

<style scoped>
/* 전체 레이아웃 설정 */
.main-container {
  display: flex;
  justify-content: space-between;  /* 왼쪽 게시글 + 오른쪽 로그인 패널 */
  padding: 0 150px;
}

/* 게시글 영역 */
.content {
  width: 75%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.best-topics-container {
  width: 100%;
  text-align: center;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

/* 한 줄에 3개씩 배치 */
.topics-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3개씩 정렬 */
  gap: 20px;
  justify-content: center;
}

.topic-card {
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.topic-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.topic-info {
  padding: 15px;
}

.topic-author {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.topic-title {
  font-size: 18px;
  font-weight: bold;
}

.view-button {
  background-color: #3A4CA4;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
}

.view-button:hover {
  background-color: #2c3e50;
}

/* 로그인 패널 */
.login-panel {
  width: 20%;
  background-color: #f4f4f4;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  position: sticky;
  top: 20%;
}

.login-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
  background-color: #f9f9f9;
  padding: 20px 10px;
}

.user-icon {
  font-size: 80px;
  color:#3A4CA4;
  margin-top: 30px;
}

h3 {
  font-weight: bold;
  margin-bottom: 20px;
}

.input-container {
  position: relative;
  width: 100%;
}

.icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #939393;
}

.input-field {
  width: 100%;
  padding: 12px 12px 12px 30px;
  margin-bottom: 15px;
  border: 1px solid white;
  border-radius: 8px;
}

.username, .password {
  color: #939393;
}

.login-button {
  background-color: #3A4CA4;
  color: white;
  padding: 12px;
  border: 1px solid #3A4CA4;
  border-radius: 25px;
  cursor: pointer;
  width: 100%;
  margin-top: 10px;
}

.links {
  display: flex;
  justify-content: space-between;
  width: 100%;
  font-size: 10px;
  margin-top: 10px;
  color: #939393;
}
</style>
