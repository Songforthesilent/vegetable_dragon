<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 검색창 영역 -->
      <section class="search-section">
        <div class="search-container">
          <input type="text" v-model="searchQuery" placeholder="Search" class="search-input" />
          <button @click="search" class="search-button">
            <i class="fas fa-search search-icon"></i>
          </button>
        </div>
      </section>

      <!-- Best Topics 영역 -->
      <section class="article-section">
        <div class="section-header">
          <h2 class="best-topics-title">Best Topics</h2>
          <router-link to="/best-topics" class="more-link">
            <i class="fas fa-chevron-right"></i>
          </router-link>
        </div>
        <div class="best-topics-container">
          <div v-for="article in bestTopics" :key="article.id" class="best-topic">
            <img :src="article.image" alt="Best Topic Image" class="best-topic-img" />
            <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">
              <h3>{{ article.title }}</h3>
            </router-link>
            <p>{{ article.content }}</p>
          </div>
        </div>
      </section>

      <!-- 최근 게시글 영역 -->
      <section class="article-section">
        <div class="section-header">
          <h2 class="recent-posts-title">최근 게시글</h2>
          <router-link to="/board/list" class="more-link">
            <i class="fas fa-chevron-right"></i>
          </router-link>
        </div>

        <!-- 카테고리 버튼 -->
        <div class="recent-posts-container">
          <button v-for="category in ['전체', ...categories]"
                  :key="category"
                  :class="['category-button', { active: selectedCategory === category }]"
                  @click="filterCategory(category)">
            {{ category }}
          </button>
        </div>

        <div class="recent-table">
          <table>
            <tr v-for="article in filteredArticles" :key="article.id" class="table-row">
              <td class="category">{{ article.category ? article.category.name : '없음' }}</td> <!-- 카테고리 이름 출력 -->
              <td class="title">
                <router-link :to="'/board/view/' + article.id" class="title-link">
                  {{ article.title }}
                </router-link>
              </td>
            </tr>
          </table>
        </div>
      </section>
    </article>

    <!-- 로그인 배너 영역 -->
    <LoginBanner />
  </div>
</template>

<script>
import LoginBanner from '@/components/LoginBanner.vue';
import axios from 'axios';
export default {
  components: {
    LoginBanner
  },
  data() {
    return {
      searchQuery: '',
      categories: ['경제', '연예', '정치', '사회', '국제', '문화'],
      selectedCategory: '전체',  // 기본 카테고리
      bestTopics: [], // 예시로 남겨놓은 Best Topics
      recentArticles: [],  // 백엔드에서 받아올 최신 게시글
      page: 0, // 현재 페이지 (기본은 첫 번째 페이지)
      size: 5, // 한 페이지에 표시할 게시글 수
    };
  },
  computed: {
    filteredArticles() {
      if (Array.isArray(this.recentArticles) && this.recentArticles.length > 0) {
        const categoryToCompare = this.selectedCategory.toLowerCase().trim();  // 소문자, 공백 제거
        if (this.selectedCategory === '전체') {
          return this.recentArticles;
        } else {
          return this.recentArticles.filter(article =>
              article.category && article.category.name.toLowerCase().trim() === categoryToCompare
          );
        }
      }
      return [];  // 최근 게시글이 없다면 빈 배열 반환
    }
  },
  methods: {
// 카테고리 클릭 시 호출
    filterCategory(category) {
      this.selectedCategory = category;
      this.fetchRecentPosts();  // 카테고리 변경 시 게시글을 다시 불러옴
    },

    // 검색 기능 (예시로 alert로 검색어 출력)
    search() {
      alert(`검색어: ${this.searchQuery}`);
    },
    // 최근 게시글을 백엔드에서 불러오는 메소드
    async fetchRecentPosts() {
      if (this.selectedCategory === "전체"){
        try {
          const response = await axios.get("http://localhost:8081/posts", {
            params: {
              page: this.page, // 현재 페이지
              size: this.size, // 한 페이지에 표시할 게시글 수
            },
          });
          this.recentArticles = response.data.content; // 페이지네이션이 포함된 응답의 내용
        } catch (error) {
          console.error("게시글을 불러오는 데 실패했습니다.", error);
        }
      } else {
        try {
          // const encodedCategory = encodeURIComponent(this.selectedCategory);
          const url = `http://localhost:8081/posts/category/${this.selectedCategory}`;
          console.log("Request URL:", url); // 요청 URL 확인용 로그
          const response = await axios.get(url, {
            params: {
              page: this.page,
              size: this.size,
            },
          });
          console.log(response.data);
          this.recentArticles = response.data;
          console.log("Fetched articles:", this.recentArticles); // 데이터 확인용 로그
        } catch (error) {
          console.error("카테고리별 게시글을 불러오는 데 실패했습니다.", error);
        }
      }
    },
    async fetchBestTopics() {
      try {
        const res = await axios.get("http://localhost:8081/posts", {
          params: { page: 0, size: 50 },
        });

        const posts = res.data.content;

        const ratioPromises = posts.map(post =>
            axios.get(`http://localhost:8081/feedback/${post.id}/ratio`)
                .then(r => ({ ...post, ratio: r.data }))
                .catch(() => null)
        );

        const postsWithRatio = (await Promise.all(ratioPromises)).filter(Boolean);

        const filtered = postsWithRatio.filter(p => {
          const r = p.ratio.trueNewsRatio;
          return Math.abs(r - 0.5) <= 0.05;
        });

        filtered.sort((a, b) =>
            Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
        );

        this.bestTopics = filtered.slice(0, 3);
      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
      }
    },
  },

  created() {
    this.fetchRecentPosts();
    this.fetchBestTopics();
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
  padding: 0 150px;
}

.content {
  width: 75%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.search-section {
  margin-top: 40px;
  text-align: left;
  background-color: #ffffff;
}

.search-container {
  position: relative;
  width: 100%;
}

.search-input {
  padding: 10px;
  width: 100%;
  border: 1px solid #999FBB;
  border-radius: 5px;
  color: #989595;
}

.search-button {
  position: absolute;
  right: 6px;
  top: 6px;
  padding: 6px 12px;
  background-color: white;
  color: #989595;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

/* Best Topics */
.article-section {
  width: 100%;
  padding: 0px;
  margin-top: 20px;
  background-color: #ffffff;
}

.article {
  border-bottom: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 10px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.best-topics-title {
  text-align: left;
  font-weight: bold;
  font-size: 18px;
}

.more-link {
  font-size: 14px;
  color: #3A4CA4;
  text-decoration: none;
}

.more-link:hover {
  text-decoration: underline;
}

.best-topics-container {
  display: flex;
  gap: 15px;
  margin-top: 10px;
  padding: 0px;
}

.best-topic {
  width: 50%;
  background-color: #f9f9f9;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
}

.best-topic-img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.best-topic-title-link {
  text-decoration: none;
  color: #333;
  font-weight: bold;
}

.best-topic-title-link:hover {
  color: #3A4CA4;
}

/* 최근 게시글 */
.recent-posts-title {
  text-align: left;
  font-weight: bold;
  font-size: 18px;
}

.category-button {
  background-color: #8994c8;
  border: none;
  padding: 6px 23px;
  border-radius: 7px;
  font-size: 13px;
  font-weight: bold;
  color: white;
}

.category-button.active {
  background-color: #3A4CA4;
}
.category-button:hover {
  background-color: #3A4CA4;
  cursor: pointer;
}

.recent-posts-container {
  display: flex;
  gap: 15px;
  margin-top: 10px;
  text-align: left; /* 왼쪽 정렬 */
}

.recent-table {
  width: 100%;
  background: white;
  border-radius: 10px;
  padding: 10px;
}

.recent-table table {
  width: 100%;
  border-collapse: collapse;
}

.category {
  text-align: left;
  font-weight: bold;
  padding: 5px;
}

/*최근게시글리스트*/
.table-row {
  border-bottom: 1px solid #ddd;
  color: #939393;
  font-size: 14px;
  text-align: left;
}

h3 {
  font-weight: bold;
  margin-bottom: 20px;
  font-size: 14px;
}

/* 제목 스타일 수정 */
.title {
  padding: 5px;
}

.title-link {
  text-decoration: none; /* 밑줄 제거 */
  color: #333; /* 제목 색상 변경 */
  font-weight: bold;
}

.title-link:hover {
  color: #3A4CA4; /* 마우스 오버 시 색상 변경 */
}
</style>