<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 검색창 영역 -->
      <section class="search-section">
        <div class="search-container">
          <input type="text" v-model="searchQuery" placeholder="Search" class="search-input" />
          <button @click="search" class="search-button">
            <i class="fas fa-search search-icon"></i> <!-- 돋보기 아이콘 -->
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
            <h3>{{ article.title }}</h3>
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
          </router-link> <!-- 전체보기 아이콘 -->
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
                <!-- 제목 클릭 시 상세 페이지로 이동 -->
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
          <router-link to="/register">회원가입하기</router-link>>
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
      searchQuery: '',
      categories: ['경제', '연예', '정치', '사회', '국제', '문화'],
      selectedCategory: '전체', // 초기값 '전체'
      bestTopics: [
        {
          id: 1,
          content: 'Vue.js는 최신 JavaScript 프레임워크입니다.',
          image: 'https://via.placeholder.com/150'
        },
        {
          id: 2,
          content: 'Vue Router는 SPA 구현을 위한 라우터입니다.',
          image: 'https://via.placeholder.com/150'
        },
        {
          id: 3,
          content: 'Vuex는 Vue의 상태 관리를 위한 라이브러리입니다.',
          image: 'https://via.placeholder.com/150'
        }
      ],
      recentArticles: [
        {id: 1, category: '경제', title: 'Vuex는 Vue의 상태 관리를 위한 라이브러리입니다.'},
        {id: 2, category: '문화', title: 'Vue 3에서 추가된 새로운 기능들을 살펴봅니다.'},
        {id: 3, category: '문화', title: 'Composition API는 Vue 3에서 새로 도입된 기능입니다.'},
        {id: 4, category: '연예', title: '인기 드라마 배우들의 인터뷰' },
        {id: 5, category: '정치', title: '대선 후보들의 공약 분석' }
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
      alert('검색어: ${this.searchQuery}');
    },
    login() {
      alert("로그인 버튼이 클릭되었습니다.");
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

/* 최근 게시글 */
.recent-posts-title {
  text-align: left;
  font-weight: bold;
  font-size: 18px;
}

.category-button {
  background-color: #8994c8;
  border: none;
  padding: 5px 25px;
  border-radius: 20px;
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
  gap: 18px;
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

/*로그인*/
.login-panel {
  width: 20%;
  height: 980px;
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
  left: 10px;  /* 아이콘을 input의 왼쪽에 배치 */
  top: 50%;
  transform: translateY(-85%); /* 수직 가운데 정렬 */
  color: #939393;  /* 아이콘 색상 */
}

.input-field {
  width: 100%;
  padding: 12px 12px 12px 30px;
  margin-bottom: 15px;
  border: 1px solid white;
  border-radius: 8px;
}

.username {
  color: #939393;
}

.password {
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

/* ✅ 제목 스타일 수정 */
.title {
  padding: 5px;
}

.title-link {
  text-decoration: none; /* ✅ 밑줄 제거 */
  color: #333; /* ✅ 제목 색상 변경 */
  font-weight: bold;
}

.title-link:hover {
  color: #3A4CA4; /* ✅ 마우스 오버 시 색상 변경 */
}
</style>