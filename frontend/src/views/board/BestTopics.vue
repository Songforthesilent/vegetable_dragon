<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <div class="best-topics-container">
        <h1 class="page-title">Best Topics</h1>
        <section class="topics-grid">
          <router-link
              v-for="topic in bestTopics"
              :key="topic.id"
              :to="'/board/view/' + topic.id"
              class="topic-card"
          >
            <img :src="topic.image || 'https://via.placeholder.com/300'" :alt="topic.title" class="topic-image" />
            <div class="topic-info">
              <p class="topic-author">{{ topic.author }}</p>
              <h3 class="topic-title">{{ topic.title }}</h3>
            </div>
          </router-link>
        </section>
      </div>
    </article>

    <!-- 로그인 배너 영역 -->
    <LoginBanner />
  </div>
</template>
<script>
import axios from "axios";
import LoginBanner from "@/components/LoginBanner.vue";

export default {
  components: {
    LoginBanner
  },
  data() {
    return {
      bestTopics: []
    };
  },
  created() {
    this.fetchBestTopics();
  },
  methods: {
    async fetchBestTopics() {
      try {
        const postRes = await axios.get("http://localhost:8081/posts", {
          params: { page: 0, size: 100 }
        });
        const posts = postRes.data.content;

        const ratioPromises = posts.map(post =>
            axios.get(`http://localhost:8081/feedback/${post.id}/ratio`)
                .then(ratioRes => ({
                  ...post,
                  ratio: ratioRes.data
                }))
                .catch(() => null)
        );

        const postsWithRatio = (await Promise.all(ratioPromises)).filter(Boolean);

        const filtered = postsWithRatio.filter(p =>
            Math.abs(p.ratio.trueNewsRatio - 0.5) <= 0.05
        );

        filtered.sort((a, b) =>
            Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
        );

        this.bestTopics = filtered.slice(0, 10);
      } catch (err) {
        console.error("Best Topics 로딩 실패", err);
      }
    }
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
