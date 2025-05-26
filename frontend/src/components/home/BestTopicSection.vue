<template>
  <section class="best-topics-section">
    <div class="section-container">
      <SectionHeader
          title="지금 인기 있는 토론"
          subtitle="가장 활발하게 논의되고 있는 주제를 확인해보세요"
          linkText="모든 토론 보기"
          linkTo="/best-topics"
      />

      <!-- 로딩 상태 -->
      <div v-if="loading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>토론 주제를 불러오는 중...</p>
      </div>

      <!-- 에러 상태 -->
      <div v-else-if="error" class="error-state">
        <p>토론 주제를 불러오는데 실패했습니다.</p>
        <button @click="fetchBestTopics" class="retry-button">다시 시도</button>
      </div>

      <!-- 토론 카드 그리드 -->
      <div v-else class="topics-grid">
      <topic-card
          v-for="article in bestTopics"
          :key="article.id"
          :article="article"
      />
        <!-- 데이터가 없을 때 -->
        <div v-if="bestTopics.length === 0" class="empty-state">
          <p>현재 표시할 토론이 없습니다.</p>
        </div>
      </div>
        <!-- 더보기 버튼 -->
      <div class="load-more-container">
        <router-link to="/best-topics" class="load-more-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="9 18 15 12 9 6"></polyline>
          </svg>
          모든 토론 보기
        </router-link>
      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios';
import SectionHeader from '@/components/home/SectionHeader.vue';
import TopicCard from '@/components/topic/TopicCard.vue';

export default {
  name: 'BestTopicsSection',
  components: {
    SectionHeader,
    TopicCard
  },
  data() {
    return {
      bestTopics: [],
      loading: false,
      error: null
    };
  },
  created() {
    this.fetchBestTopics();
  },
  methods: {
    async fetchBestTopics() {
      this.loading = true;
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

        this.bestTopics = filtered.slice(0, 3);
        this.loading = false;


      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
        this.error = error;
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
  /* 베스트 토론 섹션 */
.best-topics-section {
  padding: 80px 0;
  background: white;
}

.section-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 토론 카드 그리드 */
.topics-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 24px;
  margin-top: 48px;
}

/* 로딩 상태 */
.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #64748b;
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

/* 에러 상태 */
.error-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  color: #ef4444;
}

.retry-button {
  margin-top: 16px;
  padding: 8px 16px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.retry-button:hover {
  background-color: #2952CC;
}

/* 빈 상태 */
.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  padding: 60px 20px;
  color: #64748b;
}

  .load-more-button {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    padding: 10px 20px;
    background-color: #f8fafc;
    color: #64748b;
    text-decoration: none;
    border-radius: 6px;
    margin-top: 30px;
    font-size: 13px;
    font-weight: 500;
    transition: all 0.2s ease;
    border: 1px solid #e2e8f0;
  }

  .load-more-button:hover {
    background-color: #f1f5f9;
    color: #3662E3;
    border-color: #3662E3;
  }

/* 반응형 디자인 */
@media (max-width: 768px) {
  .topics-grid {
    grid-template-columns: 1fr;
  }

  .best-topics-section {
    padding: 60px 0;
  }
}

@media (max-width: 480px) {
  .section-container {
    padding: 0 16px;
  }
}
</style>
