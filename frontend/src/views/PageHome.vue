<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 검색창 영역 -->
      <section class="search-section">
        <div class="search-container">
          <input type="text" v-model="searchQuery" placeholder="Search" class="search-input" />
          <button @click="search" class="search-button">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
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
            <span class="post-category">{{ article.category }}</span>
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

export default {
  components: {
    LoginBanner
  },
  data() {
    return {
      searchQuery: '',
      categories: ['경제', '연예', '정치', '사회', '국제', '문화'],
      selectedCategory: '전체',
      bestTopics: [ //예시
        {
          id: 1,
          title: 'Vue.js 소개',
          content: 'Vue.js는 최신 JavaScript 프레임워크입니다.',
          image: 'https://via.placeholder.com/150'
        },
        {
          id: 2,
          title: 'Vue Router 이해하기',
          content: 'Vue Router는 SPA 구현을 위한 라우터입니다.',
          image: 'https://via.placeholder.com/150'
        },
        {
          id: 3,
          title: 'Vuex 활용법',
          content: 'Vuex는 Vue의 상태 관리를 위한 라이브러리입니다.',
          image: 'https://via.placeholder.com/150'
        }
      ],
      recentArticles: [ // 예시
        { id: 1, category: '경제', title: 'Vuex는 Vue의 상태 관리를 위한 라이브러리입니다.' },
        { id: 2, category: '문화', title: 'Vue 3에서 추가된 새로운 기능들을 살펴봅니다.' },
        { id: 3, category: '문화', title: 'Composition API는 Vue 3에서 새로 도입된 기능입니다.' },
        { id: 4, category: '연예', title: '인기 드라마 배우들의 인터뷰' },
        { id: 5, category: '정치', title: '대선 후보들의 공약 분석' }
      ]
    };
  },
  computed: {
    filteredArticles() {
      if (this.selectedCategory === '전체') {
        return this.recentArticles;
      }
      return this.recentArticles.filter(article => article.category === this.selectedCategory);
    }
  },
  methods: {
    filterCategory(category) {
      this.selectedCategory = category;
    },
    search() {
      alert(`검색어: ${this.searchQuery}`);
    }
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
  padding: 0 150px;
  margin: 0 auto;
  gap: 30px;
}

.content {
  width: 75%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* 공통 섹션 스타일 */
.section-card {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 24px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.section-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.08);
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

.more-link {
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: #3A4CA4;
  text-decoration: none;
  transition: color 0.2s ease;
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

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.section-title {
  font-weight: 700;
  color: #1e293b;
  font-size: 18px;
  margin-bottom: 20px;
  position: relative;;
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
  padding: 8px 18px;
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
  font-size: 14px;
  color: #1e293b;
  text-decoration: none;
  text-align: left;
  font-weight: 450;
  transition: color 0.2s ease;
  white-space: nowrap;
  margin-left: 15px;
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

h3 {
  font-weight: bold;
  margin-bottom: 20px;
  font-size: 14px;
}

</style>