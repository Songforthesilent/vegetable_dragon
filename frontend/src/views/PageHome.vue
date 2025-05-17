<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 검색창 영역 -->
<<<<<<< HEAD
      <SearchBar @search="search" />
=======
      <section class="search-section">
        <div class="search-container">
          <input type="text" v-model="searchQuery" placeholder="Search" class="search-input" />
          <button @click="search" class="search-button">
            <i class="fas fa-search search-icon"></i> <!-- 돋보기 아이콘 -->
          </button>
        </div>
      </section>
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

      <!-- Best Topics 영역 -->
      <section class="article-section">
        <div class="section-header">
<<<<<<< HEAD
          <h2 class="section-title">Best Topics</h2>
          <router-link to="/best-topics" class="more-link">
            더보기
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"></polyline>
            </svg>
          </router-link>
        </div>
        <div class="best-topics-container">
          <div v-for="article in bestTopics" :key="article.id" class="best-topic-card">
            <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">

            <div class="best-topic-vote-container">
              <div class="vote-circle-container">
                <div class="vote-circle">
                  <svg viewBox="0 0 36 36" class="vote-chart">
                    <!-- 배경 원 -->
                    <path class="vote-circle-bg"
                          d="M18 2.0845
                        a 15.9155 15.9155 0 0 1 0 31.831
                        a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                    <!-- 찬성 부분 (파란색) -->
                    <path class="vote-circle-true"
                          :stroke-dasharray="`${(article.ratio?.trueNewsRatio || 0.5) * 100}, 100`"
                          d="M18 2.0845
                        a 15.9155 15.9155 0 0 1 0 31.831
                        a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                    <!-- 반대 부분 (빨간색) -->
                    <path class="vote-circle-false"
                          :stroke-dasharray="`${(1 - (article.ratio?.trueNewsRatio || 0.5)) * 100}, 100`"
                          :stroke-dashoffset="-1 * (article.ratio?.trueNewsRatio || 0.5) * 100"
                          d="M18 2.0845
                        a 15.9155 15.9155 0 0 1 0 31.831
                        a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                  </svg>
                  <div class="vote-percentage">
                    <div class="vote-vs">VS</div>
                  </div>
                </div>
              </div>

              <div class="vote-labels">
                <div class="vote-label true">
                  <span class="vote-percent">{{ Math.round((article.ratio?.trueNewsRatio || 0.5) * 100) }}%</span>
                </div>
                <div class="vote-label false">
                  <span class="vote-percent">{{ Math.round((1 - (article.ratio?.trueNewsRatio || 0.5)) * 100) }}%</span>
                </div>
              </div>
            </div>
            </router-link>
            <div class="best-topic-content">
              <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">
                <h3 class="best-topic-title">{{ article.title }}</h3>
              </router-link>
              <p class="best-topic-excerpt">{{ article.content }}</p>
            </div>
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
          </div>
        </div>
      </section>

      <!-- 최근 게시글 영역 -->
      <section class="article-section">
        <div class="section-header">
<<<<<<< HEAD
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
=======
          <h2 class="recent-posts-title">최근 게시글</h2>
          <router-link to="/board/list" class="more-link">
            <i class="fas fa-chevron-right"></i>
          </router-link> <!-- 전체보기 아이콘 -->
        </div>

        <!-- 카테고리 버튼 -->
        <div class="recent-posts-container">
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
          <button v-for="category in ['전체', ...categories]"
                  :key="category"
                  :class="['category-button', { active: selectedCategory === category }]"
                  @click="filterCategory(category)">
            {{ category }}
          </button>
        </div>

<<<<<<< HEAD
        <!-- 게시글 목록 -->
        <div class="recent-posts-list">
          <div v-for="article in filteredArticles" :key="article.id" class="post-item">
            <span class="post-category">{{ article.category ? article.category.name : '없음' }}</span> <!-- 카테고리 이름 출력 -->
            <router-link :to="'/board/view/' + article.id" class="post-title-link">
              {{ article.title }}
            </router-link>
          </div>
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        </div>
      </section>
    </article>

    <!-- 로그인 배너 영역 -->
<<<<<<< HEAD
    <LoginBanner />
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
  </div>
</template>

<script>
<<<<<<< HEAD
import LoginBanner from '@/components/LoginBanner.vue';
import SearchBar from '@/components/SearchBar.vue';
import axios from 'axios';

export default {
  components: {
    LoginBanner,
    SearchBar
  },
  data() {
    return {
      categories: ['경제', '연예', '정치', '사회', '국제', '문화'],
      selectedCategory: '전체',  // 기본 카테고리
      bestTopics: [], // 예시로 남겨놓은 Best Topics
      recentArticles: [],  // 백엔드에서 받아올 최신 게시글
      page: 0, // 현재 페이지 (기본은 첫 번째 페이지)
      size: 5, // 한 페이지에 표시할 게시글 수
      categoryMap: {
        "vue": "사회",
        "react": "정치",
        "angular": "문화",
        "economy": "경제",
        "entertainment": "연예",
        "international": "국제",
        "": "기타"
      }
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    };
  },
  computed: {
    filteredArticles() {
<<<<<<< HEAD
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
    search(query) {
      alert(`검색어: ${query}`);
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
<<<<<<< HEAD
  padding: 0 250px;
  margin-top: 30px;
}

.content {
  width: 74%;
=======
  padding: 0 150px;
}

.content {
  width: 75%;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
  display: flex;
  flex-direction: column;
  gap: 10px;
}

<<<<<<< HEAD
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

.more-link:hover {
  color: #2d3a7c;
}

.more-link svg {
  margin-left: 4px;
  transition: transform 0.2s ease;
}

.more-link:hover svg {
  transform: translateX(3px);
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}

/* Best Topics */
.article-section {
  width: 100%;
  padding: 0px;
<<<<<<< HEAD
  margin: 20px 0 30px 0;
  background-color: #ffffff;
}

=======
  margin-top: 20px;
  background-color: #ffffff;
}

.article {
  border-bottom: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 10px;
}

>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

<<<<<<< HEAD
/* 투표 시각화 스타일 */
.best-topic-vote-container {
  padding: 15px;
  background: linear-gradient(145deg, #f8fafc, #f1f5f9);
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.vote-circle-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.vote-circle {
  position: relative;
  width: 80px;
  height: 80px;
}

.vote-chart {
  width: 100%;
  height: 100%;
  transform: rotate(-90deg);
}

.vote-circle-bg {
  fill: none;
  stroke: #e2e8f0;
  stroke-width: 3.8;
}

.vote-circle-true {
  fill: none;
  stroke: #3A4CA4;
  stroke-width: 3.8;
  stroke-linecap: round;
}

.vote-circle-false {
  fill: none;
  stroke: #ef4444;
  stroke-width: 3.8;
  stroke-linecap: round;
}

.vote-percentage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.vote-vs {
  font-size: 16px;
  font-weight: 800;
  color: #1e293b;
  background: linear-gradient(135deg, #3A4CA4, #ef4444);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  letter-spacing: -1px;
}

.vote-labels {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.vote-label {
  display: flex;
  justify-content: center;
  width: 45%;
}

.vote-label.true {
  color: #3A4CA4;
}

.vote-label.false {
  color: #ef4444;
}

.vote-percent {
  font-size: 14px;
  font-weight: 700;
}

/* Best Topics 섹션 */
.best-topics-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

/* 기존 best-topic-image-container 스타일 제거 및 best-topic-card 수정 */
.best-topic-card {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  height: 100%;
}

.best-topic-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.best-topic-card:hover {
  transform: scale(1.05);
}

.best-topic-content {
  padding: 16px;
}

.best-topic-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
  line-height: 1.4;
  transition: color 0.2s ease;
}

.best-topic-title-link {
  text-decoration: none;
}

.best-topic-card:hover .best-topic-title {
  color: #3A4CA4;
}

.best-topic-excerpt {
  font-size: 14px;
  color: #64748b;
  margin: 0;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}

.category-button.active {
  background-color: #3A4CA4;
<<<<<<< HEAD
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
</style>