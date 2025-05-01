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
              <td class="category">{{ article.category }}</td>
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