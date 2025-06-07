<template>
  <div class="main-container">
    <!-- 페이지 제목 헤더 -->
    <div class="page-title-header">
      <h1 class="page-title">인기 게시판</h1>
      <p class="page-subtitle">가장 활발한 토론에 참여해보세요</p>
    </div>

    <!-- 페이지 헤더 -->
    <div class="page-header">
      <div class="post-count">
        <span class="count-text">총 {{ totalElements }}개의 게시글</span>
        <span v-if="searchQuery" class="search-result-text">
          '{{ searchQuery }}' 검색 결과: {{ bestTopics.length }}개
        </span>
      </div>

      <div class="search-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="search-icon">
          <circle cx="11" cy="11" r="8"></circle>
          <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
        </svg>
        <input
            v-model="searchQuery"
            type="text"
            placeholder="토론 주제를 검색해보세요..."
            class="search-input"
            @keyup.enter="search"
        />
        <button v-if="searchQuery" @click="clearSearch" class="clear-search-btn" title="검색어 지우기">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>
    </div>

    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 로딩 상태 -->
      <div v-if="loading" class="loading-container">
        <div class="loading-spinner"></div>
        <p class="loading-text">게시글을 불러오는 중...</p>
      </div>

      <!-- 데이터가 없는 상태 -->
      <div v-else-if="bestTopics.length === 0" class="empty-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="8" y1="12" x2="16" y2="12"></line>
        </svg>
        <h3 class="empty-title">{{ searchQuery ? '검색 결과가 없습니다' : '표시할 게시글이 없습니다' }}</h3>
        <p class="empty-message">
          {{ searchQuery ? `'${searchQuery}'에 대한 검색 결과가 없습니다.` : '아직 인기 게시글이 없습니다. 나중에 다시 확인해주세요.' }}
        </p>
        <button v-if="searchQuery" @click="clearSearch" class="reset-search-btn">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M2.5 2v6h6M21.5 22v-6h-6"></path>
            <path d="M22 11.5A10 10 0 0 0 3.2 7.2M2 12.5a10 10 0 0 0 18.8 4.2"></path>
          </svg>
          검색 초기화
        </button>
      </div>

      <!-- 게시글 그리드 -->
      <div v-else class="best-topics-container">
        <div class="topics-grid">
          <router-link
              v-for="topic in bestTopics"
              :key="topic.id"
              :to="'/board/view/' + topic.id"
              class="topic-card"
          >
            <!-- 상단 헤더 -->
            <div class="card-header">
              <span
                  class="topic-tag"
                  :class="getCategoryClass(getCategoryName(topic))"
                  :style="{
                  backgroundColor: getCategoryBackgroundColor(getCategoryName(topic)),
                  color: getCategoryTextColor(getCategoryName(topic))
                }"
              >
                {{ getCategoryName(topic) }}
              </span>
            </div>

            <!-- 제목 -->
            <h3 class="topic-title">{{ topic.title }}</h3>

            <!-- VS 차트 -->
            <div class="vs-chart-container">
              <div class="vote-circle">
                <svg viewBox="0 0 36 36" class="vote-chart">
                  <!-- 배경 원 -->
                  <path class="vote-circle-bg"
                        d="M18 2.0845
                      a 15.9155 15.9155 0 0 1 0 31.831
                      a 15.9155 15.9155 0 0 1 0 -31.831"
                  />
                  <!-- 찬성 부분 -->
                  <path class="vote-circle-true"
                        :stroke="getCategoryProgressColor(getCategoryName(topic))"
                        :stroke-dasharray="`${(topic.ratio?.trueNewsRatio || 0.5) * 100}, 100`"
                        d="M18 2.0845
                      a 15.9155 15.9155 0 0 1 0 31.831
                      a 15.9155 15.9155 0 0 1 0 -31.831"
                  />
                </svg>
                <div class="vs-overlay">VS</div>
              </div>
            </div>

            <!-- 하단 퍼센트 표시 -->
            <div class="percentage-container">
              <div class="percentage-item">
                <span class="percentage-label">진짜뉴스</span>
                <span
                    class="percentage-value"
                    :class="{ 'positive': (topic.ratio?.trueNewsRatio || 0.5) >= 0.5 }"
                    :style="{ color: (topic.ratio?.trueNewsRatio || 0.5) >= 0.5 ? getCategoryTextColor(getCategoryName(topic)) : '#94a3b8' }"
                >
                  {{ (topic.ratio?.trueNewsRatio || 0.5) >= 0.5 ? '>' : '<' }} {{ Math.round((topic.ratio?.trueNewsRatio || 0.5) * 100) }}%
                </span>
              </div>
              <div class="percentage-item">
                <span class="percentage-label">가짜뉴스</span>
                <span
                    class="percentage-value"
                    :class="{ 'positive': (1 - (topic.ratio?.trueNewsRatio || 0.5)) >= 0.5 }"
                    :style="{ color: (1 - (topic.ratio?.trueNewsRatio || 0.5)) >= 0.5 ? getCategoryTextColor(getCategoryName(topic)) : '#94a3b8' }"
                >
                  {{ (1 - (topic.ratio?.trueNewsRatio || 0.5)) >= 0.5 ? '>' : '<' }} {{ Math.round((1 - (topic.ratio?.trueNewsRatio || 0.5)) * 100) }}%
                </span>
              </div>
            </div>

            <!-- 하단 메타 정보 -->
            <div class="card-footer">
              <span class="topic-date">{{ formatDate(topic.createdAt) }}</span>
              <span class="topic-author">
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
                {{ topic.authorUsername }}
              </span>
            </div>
          </router-link>
        </div>
      </div>
    </article>
  </div>
</template>

<script>
import axios from "axios";
import { getCategoryByContent, getCategoryClass, getCategoryBackgroundColor, getCategoryTextColor, getCategoryProgressColor } from '@/utils/CategoryUtils';

export default {
  data() {
    return {
      bestTopics: [],
      allTopics: [], // 모든 게시글을 저장하는 배열 추가
      loading: true,
      error: false,
      errorMessage: "",
      currentPage: 0,
      totalPages: 1,
      totalElements: 0, // 총 게시글 수 추가
      searchQuery: "", // 검색어 추가
      categories: {
        "v1": "경제",
        "v2": "연예",
        "v3": "정치",
        "v4": "사회",
        "v5": "국제",
        "v6": "문화"
      }
    };
  },
  created() {
    this.fetchBestTopics();
  },
  methods: {
    async fetchBestTopics() {
      this.loading = true;
      this.error = false;

      try {
        const res = await axios.get(`${process.env.VUE_APP_API_URL}/posts`, {
          params: { page: 0, size: 100 }
        });

        // 총 게시글 수 저장
        this.totalElements = res.data.totalElements || res.data.content.length;

        const posts = res.data.content;

        const ratioPromises = posts.map(post =>
            axios.get(`${process.env.VUE_APP_API_URL}/feedback/${post.id}/ratio`)
                .then(ratioRes => ({
                  ...post,
                  ratio: ratioRes.data
                }))
                .catch(() => null)
        );

        const postsWithRatio = (await Promise.all(ratioPromises)).filter(Boolean);

        // 모든 게시글 저장
        this.allTopics = postsWithRatio;

        // 논란이 되는 게시글 필터링 (비율이 0.5에 가까운 것들)
        const filtered = postsWithRatio.filter(p =>
            Math.abs(p.ratio.trueNewsRatio - 0.5) <= 0.05
        );

        filtered.sort((a, b) =>
            Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
        );

        this.bestTopics = filtered.slice(0, 10); // 상위 10개
      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
        this.error = true;
        this.errorMessage = "서버에서 데이터를 불러오는 중 오류가 발생했습니다.";
      } finally {
        this.loading = false;
      }
    },

    // 검색 기능 추가
    search() {
      if (!this.searchQuery.trim()) {
        // 검색어가 없으면 모든 게시글 표시
        this.resetSearch();
        return;
      }

      this.loading = true;

      // 검색어를 소문자로 변환하여 대소문자 구분 없이 검색
      const query = this.searchQuery.toLowerCase();

      // 제목, 내용, 작성자 이름에서 검색
      const searchResults = this.allTopics.filter(topic => {
        const title = (topic.title || '').toLowerCase();
        const content = (topic.content || '').toLowerCase();
        const author = (topic.authorUsername || '').toLowerCase();
        const category = this.getCategoryName(topic).toLowerCase();

        return title.includes(query) ||
            content.includes(query) ||
            author.includes(query) ||
            category.includes(query);
      });

      // 검색 결과를 논란이 되는 순서로 정렬
      searchResults.sort((a, b) =>
          Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
      );

      this.bestTopics = searchResults;
      this.loading = false;
    },

    // 검색 초기화
    clearSearch() {
      this.searchQuery = "";
      this.resetSearch();
    },

    // 검색 초기화 후 원래 게시글 표시
    resetSearch() {
      // 논란이 되는 게시글 필터링 (비율이 0.5에 가까운 것들)
      const filtered = this.allTopics.filter(p =>
          Math.abs(p.ratio.trueNewsRatio - 0.5) <= 0.05
      );

      filtered.sort((a, b) =>
          Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
      );

      this.bestTopics = filtered.slice(0, 10); // 상위 10개
    },

    formatDate(dateString) {
      if (!dateString) return "";

      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');

      return `${year}-${month}-${day}`;
    },
    getCategoryName(topic) {
      // 1. API에서 가져온 카테고리 정보 우선 사용
      if (topic.categoryName) {
        return topic.categoryName;
      }

      // 2. 카테고리 객체가 있는 경우
      if (topic.category) {
        // 카테고리가 객체이고 name 속성이 있는 경우
        if (typeof topic.category === 'object' && topic.category.name) {
          return topic.category.name;
        }

        // 카테고리가 문자열인 경우
        if (typeof topic.category === 'string') {
          return topic.category;
        }
      }

      // 3. 자동분류 로직 사용
      return getCategoryByContent(topic.title, topic.content);
    },
    getCategoryClass,
    getCategoryBackgroundColor,
    getCategoryTextColor,
    getCategoryProgressColor
  }
};
</script>

<style scoped>
/* 전체 레이아웃 */
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 페이지 제목 헤더 */
.page-title-header {
  padding: 40px 0 20px 0;
  border-bottom: 1px solid #f1f5f9;
  margin-bottom: 30px;
  text-align: left;
}

.page-title {
  font-size: 26px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 15px 0;
  line-height: 1.2;
}

.page-subtitle {
  font-size: 15px;
  color: #64748b;
  margin: 0;
  line-height: 1.5;
}

/* 페이지 헤더 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
}

/* 게시글 수 표시 */
.post-count {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 5px;
}

.count-text {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.search-result-text {
  font-size: 13px;
  color: #3662E3;
  font-weight: 500;
}

/* 검색 컨테이너 */
.search-container {
  position: relative;
  align-items: center;
  width: 650px;
}

.search-icon {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #94a3b8;
  z-index: 1;
}

.search-input {
  width: 100%;
  padding: 14px 20px 14px 50px;
  border: none;
  border-radius: 25px;
  background-color: #f8fafc;
  color: #1e293b;
  font-size: 14px;
  transition: all 0.3s ease;
  outline: none;
}

.search-input::placeholder {
  color: #94a3b8;
  font-weight: 400;
}

.search-input:focus {
  background-color: #ffffff;
  box-shadow: 0 0 0 2px #3662E3;
  transform: translateY(-1px);
}

.search-input:focus ~ .search-icon {
  color: #3662E3;
}

/* 검색어 지우기 버튼 */
.clear-search-btn {
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #94a3b8;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: all 0.2s ease;
  z-index: 2;
}

.clear-search-btn:hover {
  color: #64748b;
  background-color: #f1f5f9;
}

/* 로딩 상태 */
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #64748b;
  text-align: center;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid #f1f5f9;
  border-top: 3px solid #3662E3;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.loading-text {
  font-size: 14px;
  color: #64748b;
  margin: 0;
}

/* 콘텐츠 영역 */
.content {
  margin-top: 30px;
  margin-bottom: 50px;
}

/* 게시글 그리드 */
.best-topics-container {
  width: 100%;
}

.topics-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 24px;
}

/* 토픽 카드 */
.topic-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
  border: 1px solid #f1f5f9;
  height: 100%;
  display: flex;
  flex-direction: column;
  text-decoration: none;
  color: inherit;
}

.topic-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

/* 상단 헤더 */
.card-header {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-bottom: 16px;
}

.topic-tag {
  padding: 6px 16px;
  border-radius: 50px;
  font-size: 11px;
  font-weight: 700;
  text-align: center;
  min-width: 50px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
}

.topic-tag:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

/* 제목 */
.topic-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  line-height: 1.4;
  margin: 0 0 24px 0;
  transition: color 0.2s ease;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.topic-card:hover .topic-title {
  color: #3662E3;
}

/* VS 차트 컨테이너 */
.vs-chart-container {
  display: flex;
  justify-content: center;
  margin: 20px 0;
  flex: 1;
  align-items: center;
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
  stroke: #f1f5f9;
  stroke-width: 3;
}

.vote-circle-true {
  fill: none;
  stroke-width: 3;
  stroke-linecap: round;
  transition: stroke-dasharray 0.8s ease;
}

.vs-overlay {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 12px;
  font-weight: 700;
  color: #64748b;
  background: white;
  width: 28px;
  height: 28px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 2;
}

/* 하단 퍼센트 표시 */
.percentage-container {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  margin: 16px 0;
}

.percentage-item {
  flex: 1;
  text-align: center;
}

.percentage-label {
  display: block;
  font-size: 12px;
  color: #94a3b8;
  margin-bottom: 4px;
  font-weight: 500;
}

.percentage-value {
  display: block;
  font-size: 14px;
  font-weight: 600;
  transition: color 0.2s ease;
}

/* 하단 메타 정보 */
.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: auto;
  padding-top: 16px;
  border-top: 1px solid #f1f5f9;
}

.topic-date {
  color: #94a3b8;
  font-size: 12px;
  font-weight: 500;
}

.topic-author {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #64748b;
}

.topic-author svg {
  stroke: #64748b;
}

/* 빈 상태 */
.empty-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
  color: #64748b;
  text-align: center;
}

.empty-container svg {
  color: #94a3b8;
  margin-bottom: 16px;
}

.empty-title {
  margin: 16px 0 8px 0;
  color: #1e293b;
  font-size: 18px;
}

.empty-message {
  margin: 0 0 20px;
  color: #64748b;
  font-size: 14px;
}

.reset-search-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background-color: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  color: #3662E3;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.reset-search-btn:hover {
  background-color: #f1f5f9;
  border-color: #3662E3;
  transform: translateY(-1px);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
  }

  .search-container {
    width: 100%;
  }

  .topics-grid {
    grid-template-columns: 1fr;
  }

  .topic-card {
    padding: 16px;
  }

  .topic-title {
    font-size: 15px;
    margin-bottom: 20px;
  }

  .vs-overlay {
    width: 24px;
    height: 24px;
    font-size: 10px;
  }
}

@media (max-width: 480px) {
  .main-container {
    padding: 0 16px;
  }

  .page-title {
    font-size: 22px;
  }

  .page-subtitle {
    font-size: 14px;
  }
}
</style>