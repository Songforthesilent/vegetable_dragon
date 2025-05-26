<template>
  <div class="main-container">
    <!-- 페이지 제목 헤더 -->
    <div class="page-title-header">
      <h1 class="page-title">인기 게시판</h1>
      <p class="page-subtitle">가장 활발한 토론에 참여해보세요</p>
    </div>

    <!-- 페이지 헤더 -->
    <div class="page-header">
      <!-- 필터 옵션-->
      <div class="filter-options">
        <button
            class="filter-button active"
            title="박빙인 주제"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
            <circle cx="12" cy="12" r="3"></circle>
          </svg>
          박빙
        </button>
        <button
            class="filter-button"
            title="논쟁이 많은 주제"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
          논쟁 많은 주제
        </button>
      </div>
    </div>

    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 데이터가 없는 상태 -->
      <div v-if="bestTopics.length === 0" class="empty-container">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="8" y1="12" x2="16" y2="12"></line>
        </svg>
        <h3 class="empty-title">표시할 게시글이 없습니다</h3>
        <p class="empty-message">아직 인기 게시글이 없습니다. 나중에 다시 확인해주세요.</p>
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
              <span class="topic-tag">토론</span>
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
                  <!-- 찬성 부분 (파란색) -->
                  <path class="vote-circle-true"
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
                <span class="percentage-value" :class="{ 'positive': (topic.ratio?.trueNewsRatio || 0.5) >= 0.5 }">
                  {{ (topic.ratio?.trueNewsRatio || 0.5) >= 0.5 ? '>' : '<' }} {{ Math.round((topic.ratio?.trueNewsRatio || 0.5) * 100) }}%
                </span>
              </div>
              <div class="percentage-item">
                <span class="percentage-label">가짜뉴스</span>
                <span class="percentage-value" :class="{ 'positive': (1 - (topic.ratio?.trueNewsRatio || 0.5)) >= 0.5 }">
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

export default {
  data() {
    return {
      bestTopics: [],
      loading: true,
      error: false,
      errorMessage: "",
      currentPage: 0,
      totalPages: 1,
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

        this.bestTopics = filtered.slice(0,10); // 상위 10개
      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
        this.error = true;
        this.errorMessage = "서버에서 데이터를 불러오는 중 오류가 발생했습니다.";
        this.loading = false;
      }
    },
    formatDate(dateString) {
      if (!dateString) return "";

      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');

      return `${year}-${month}-${day}`;
    },
    getCategoryName(category) {
      return this.categories[category] || "기타";
    }
  }
};
</script>

<style scoped>
/* 전체 레이아웃 */
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  background-color: ;
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
  margin: 40px 0 30px;
  text-align: center;
}

/* 필터 옵션 */
.filter-options {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 25px;
  flex-wrap: wrap;
}

.filter-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  background-color: #f8fafc;
  border: 1px solid #e2e8f0;
  border-radius: 10px;
  color: #475569;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.filter-button:hover {
  background-color: #f1f5f9;
  border-color: #cbd5e1;
}

.filter-button.active {
  background-color: #3662E3;
  color: white;
  border-color: #3662E3;
}

.filter-button.active svg {
  stroke: white;
}

/* 콘텐츠 영역 */
.content {
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

/* 토픽 카드 (사진과 동일한 디자인) */
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
  background: #3662E3;
  color: white;
  padding: 4px 12px;
  border-radius: 16px;
  font-size: 12px;
  font-weight: 600;
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
  stroke: #3662E3;
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
  color: #94a3b8;
  transition: color 0.2s ease;
}

.percentage-value.positive {
  color: #3662E3;
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

.empty-title {
  margin: 16px 0 8px 0;
  color: #1e293b;
}

.empty-message {
  margin: 0;
  color: #64748b;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
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
}
</style>
