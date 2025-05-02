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
          <h2 class="section-title">Best Topics</h2>
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
          <h2 class="section-title">최근 게시글</h2>
          <router-link to="/board/list" class="more-link">
            더보기
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"></polyline>
            </svg>
          </router-link>
        </div>

        <!-- 카테고리 버튼 -->
        <div class="category-filter">
          <button v-for="category in ['전체', ...categories]"
                  :key="category"
                  :class="['category-button', { active: selectedCategory === category }]"
                  @click="filterCategory(category)">
            {{ category }}
          </button>
        </div>

        <!-- 게시글 목록 -->
        <div class="recent-posts-list">
          <div v-for="article in filteredArticles" :key="article.id" class="post-item">
              <span class="post-category">{{ article.category ? article.category.name : '없음' }}</span> <!-- 카테고리 이름 출력 -->
              <router-link :to="'/board/view/' + article.id" class="post-title-link">
                  {{ article.title }}
              </router-link>
          </div>
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

    async fetchBestTopics() {
      try {
        const res = await axios.get("http://localhost:8081/posts", {
          params: { page: 0, size: 100 }
        });

        const posts = res.data.content;

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

        this.bestTopics = filtered.slice(0, 3); // 또는 10개 원하면 .slice(0, 10)
      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
      }
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

    }
  },
  created() {
    this.fetchRecentPosts();  // 페이지가 로드되면 최근 게시글을 불러옴
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

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
  position: relative;
  padding-left: 14px;
}

.section-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 20px;
  background-color: #3A4CA4;
  border-radius: 2px;
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

.more-link {
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: #3A4CA4;
  text-decoration: none;
  transition: color 0.2s ease;
}

.more-link:hover {
  color: #2d3a7c;
}

.more-link svg {
  margin-left: 4px;
  transition: transform 0.2s ease;
}

.more-link:hover svg {
  transform: translateX(3px);
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

/* 최근 게시글 섹션 */
.category-filter {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.category-button {
  padding: 8px 16px;
  background-color: #f1f5f9;
  color: #475569;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.category-button:hover {
  background-color: #e2e8f0;
  color: #1e293b;
}

.category-button.active {
  background-color: #3A4CA4;
  color: white;
}

.recent-posts-list {
  display: flex;
  flex-direction: column;
}

.post-item {
  display: flex;
  align-items: center;
  padding: 14px 0;
  border-bottom: 1px solid #f1f5f9;
}

.post-item:last-child {
  border-bottom: none;
}

.post-category {
  display: inline-block;
  padding: 4px 10px;
  background-color: #f1f5f9;
  color: #475569;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
  margin-right: 12px;
  min-width: 50px;
  text-align: center;
}

.post-title-link {
  font-size: 15px;
  color: #1e293b;
  text-decoration: none;
  text-align: left;
  margin-left: 15px;
  font-weight: 450;
  transition: color 0.2s ease;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.post-title-link:hover {
  color: #3A4CA4;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .main-container {
    flex-direction: column;
    padding: 0 16px;
  }

  .sidebar {
    width: 100%;
    margin-top: 30px;
  }

  .best-topics-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .best-topics-container {
    grid-template-columns: 1fr;
  }

  .category-filter {
    overflow-x: auto;
    padding-bottom: 10px;
    flex-wrap: nowrap;
  }

  .category-button {
    white-space: nowrap;
  }

  .section-card {
    padding: 20px;
  }
}
</style>