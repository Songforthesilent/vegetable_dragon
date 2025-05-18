<template>
  <div class="main-container">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <!-- 필터 옵션 -->
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
            <div class="vote-visualization">
              <div class="topic-badge">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M12 20.94c1.5 0 2.75 1.06 4 1.06 3 0 4-3 4-6.5-2.5 0-4 2.5-8 2.5s-5.5-2.5-8-2.5c0 3.5 1 6.5 4 6.5 1.25 0 2.5-1.06 4-1.06z"></path>
                  <path d="M12 11.94c1.5 0 2.75 1.06 4 1.06 3 0 4-3 4-6.5-2.5 0-4 2.5-8 2.5s-5.5-2.5-8-2.5c0 3.5 1 6.5 4 6.5 1.25 0 2.5-1.06 4-1.06z"></path>
                </svg>
                인기
              </div>

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
                          :stroke-dasharray="`${(topic.ratio?.trueNewsRatio || 0.5) * 100}, 100`"
                          d="M18 2.0845
                        a 15.9155 15.9155 0 0 1 0 31.831
                        a 15.9155 15.9155 0 0 1 0 -31.831"
                    />
                    <!-- 반대 부분 (빨간색) -->
                    <path class="vote-circle-false"
                          :stroke-dasharray="`${(1 - (topic.ratio?.trueNewsRatio || 0.5)) * 100}, 100`"
                          :stroke-dashoffset="-1 * (topic.ratio?.trueNewsRatio || 0.5) * 100"
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
                  <span class="vote-text">진짜뉴스</span>
                  <span class="vote-percent">{{ Math.round((topic.ratio?.trueNewsRatio || 0.5) * 100) }}%</span>
                </div>
                <div class="vote-label false">
                  <span class="vote-text">가짜뉴스</span>
                  <span class="vote-percent">{{ Math.round((1 - (topic.ratio?.trueNewsRatio || 0.5)) * 100) }}%</span>
                </div>
              </div>
            </div>
            <div class="topic-info">
              <div class="topic-meta">
                <span class="topic-category">{{ getCategoryName(topic.category) }}</span>
                <span class="topic-date">{{ formatDate(topic.createdAt) }}</span>
              </div>
              <div class="topic-meta2">
                <h3 class="topic-title">{{ topic.title }}</h3>
                <p class="topic-author">
                  <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg>
                  {{ topic.authorUsername }}
                </p>
              </div>
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
        // 카테고리 디버깅
        console.log("카테고리 정보:", filtered.map(post => ({
          id: post.id,
          category: post.category,
          mappedCategory: this.getCategoryName(post.category)
        })));
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
  background-color: #3A4CA4;
  color: white;
  border-color: #3A4CA4;
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
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}

.topic-card {
  display: flex;
  flex-direction: column;
  background-color: #ffffff;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  text-decoration: none;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  height: 100%;
}

.topic-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
}

/* 투표 시각화 컴포넌트 */
.vote-visualization {
  position: relative;
  padding: 20px;
  background: linear-gradient(145deg, #f8fafc, #f1f5f9);
  border-bottom: 1px solid #e2e8f0;
  height: 200px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.vote-circle-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-grow: 1;
}

.vote-circle {
  position: relative;
  width: 120px;
  height: 120px;
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
  transition: stroke-dasharray 0.8s ease;
}

.vote-circle-false {
  fill: none;
  stroke: #ef4444;
  stroke-width: 3.8;
  stroke-linecap: round;
  transition: stroke-dasharray 0.8s ease, stroke-dashoffset 0.8s ease;
}

.vote-percentage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.vote-vs {
  font-size: 20px;
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
  margin-top: 15px;
}

.vote-label {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 45%;
}

.vote-label.true {
  color: #3A4CA4;
}

.vote-label.false {
  color: #ef4444;
}

.vote-icon {
  font-size: 18px;
  margin-bottom: 5px;
}

.vote-text {
  font-size: 12px;
  font-weight: 500;
  margin-bottom: 3px;
}

.vote-percent {
  font-size: 16px;
  font-weight: 700;
}

/* 기존 topic-badge 스타일 수정 */
.topic-badge {
  position: absolute;
  top: 15px;
  right: 15px;
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 6px 12px;
  background-color: rgba(255, 215, 0, 0.9);
  color: #1e293b;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  z-index: 2;
}

.topic-info {
  padding: 20px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.topic-meta {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.topic-category {
  display: inline-block;
  padding: 4px 10px;
  background-color: #f1f5f9;
  color: #475569;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.topic-date {
  font-size: 12px;
  color: #94a3b8;
}

.topic-meta2 {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}

.topic-title {
  font-size: 16px;
  font-weight: 600;
  text-align: left;
  color: #1e293b;
  margin: 0 0 0 5px;
  line-height: 1.4;
  transition: color 0.2s ease;
}

.topic-card:hover .topic-title {
  color: #3A4CA4;
}

.topic-author {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
  color: #64748b;
  margin: 0 0 10px 0;
}

.topic-author svg {
  stroke: #64748b;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .topics-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .page-title {
    font-size: 28px;
  }

  .filter-options {
    flex-direction: column;
    align-items: center;
  }

  .filter-button {
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 640px) {
  .topics-grid {
    grid-template-columns: 1fr;
  }

  .vote-visualization {
    height: 180px;
    padding: 15px;
  }

  .vote-circle {
    width: 100px;
    height: 100px;
  }

  .vote-vs {
    font-size: 18px;
  }

  .vote-text {
    font-size: 11px;
  }

  .vote-percent {
    font-size: 14px;
  }
}
</style>