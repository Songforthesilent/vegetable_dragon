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
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="15" y1="9" x2="9" y2="15"></line>
          <line x1="9" y1="9" x2="15" y2="15"></line>
        </svg>
        <h3 class="error-title">토론 주제를 불러오는데 실패했습니다</h3>
        <p class="error-message">서버에서 데이터를 가져오는 중 오류가 발생했습니다.</p>
        <button @click="fetchBestTopics" class="retry-button">다시 시도</button>
      </div>

      <!-- 토론 카드 그리드 -->
      <div v-else class="topics-grid">
        <TopicCard
            v-for="article in bestTopics"
            :key="article.id"
            :article="article"
        />
        <!-- 데이터가 없을 때 -->
        <div v-if="bestTopics.length === 0" class="empty-state">
          <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
            <polyline points="14 2 14 8 20 8"></polyline>
            <line x1="16" y1="13" x2="8" y2="13"></line>
            <line x1="16" y1="17" x2="8" y2="17"></line>
          </svg>
          <h3 class="empty-title">토론이 없습니다</h3>
          <p class="empty-message">현재 표시할 토론이 없습니다.</p>
        </div>
      </div>

      <!-- 더보기 버튼 -->
      <div v-if="bestTopics.length > 0" class="load-more-container">
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
import { getCategoryByContent } from '@/utils/CategoryUtils';

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
      this.error = null;

      try {
        // 게시글을 카테고리 정보와 함께 가져오기
        const res = await axios.get("http://localhost:8081/posts", {
          params: {
            page: 0,
            size: 100
          }
        });

        console.log('API 응답 데이터:', res.data);

        const posts = res.data.content || res.data;

        // 각 게시글의 카테고리 정보 확인 및 로깅
        posts.forEach(post => {
          console.log(`게시글 ID ${post.id}:`, {
            title: post.title,
            category: post.category,
            categoryType: typeof post.category,
            autoCategory: this.getCategoryName(post)
          });

          // 카테고리 정보 처리 - 각 게시글에 categoryName 속성 추가
          post.categoryName = this.getCategoryName(post);
        });

        // 각 게시글의 비율 정보 가져오기
        const ratioPromises = posts.map(post =>
            axios.get(`http://localhost:8081/feedback/${post.id}/ratio`)
                .then(ratioRes => ({
                  ...post,
                  ratio: ratioRes.data
                }))
                .catch(error => {
                  console.warn(`게시글 ${post.id}의 비율 정보를 가져오는데 실패:`, error);
                  return null;
                })
        );

        const postsWithRatio = (await Promise.all(ratioPromises)).filter(Boolean);

        // 논란이 되는 게시글 필터링 (비율이 0.5에 가까운 것들)
        const controversialPosts = postsWithRatio.filter(post => {
          if (!post.ratio || typeof post.ratio.trueNewsRatio !== 'number') {
            return false;
          }
          return Math.abs(post.ratio.trueNewsRatio - 0.5) <= 0.05;
        });

        // 논란 정도에 따라 정렬 (0.5에 가까울수록 상위)
        controversialPosts.sort((a, b) =>
            Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
        );

        this.bestTopics = controversialPosts.slice(0, 3);

        console.log('필터링된 베스트 토픽:', this.bestTopics);
        console.log('카테고리 정보:', this.bestTopics.map(post => ({
          id: post.id,
          title: post.title,
          categoryName: post.categoryName,
          autoCategory: getCategoryByContent(post.title, post.content)
        })));

      } catch (error) {
        console.error("Best Topics 불러오기 실패:", error);
        this.error = error;
      } finally {
        this.loading = false;
      }
    },

    getCategoryName(article) {
      // 디버깅을 위한 로깅
      console.log('카테고리 정보 확인:', {
        articleId: article.id,
        category: article.category,
        categoryType: typeof article.category
      });

      // 1. API에서 가져온 카테고리 정보 우선 사용
      if (article.categoryName) {
        return article.categoryName;
      }

      // 2. 카테고리 객체가 있는 경우
      if (article.category) {
        // 카테고리가 객체이고 name 속성이 있는 경우
        if (typeof article.category === 'object' && article.category.name) {
          return article.category.name;
        }

        // 카테고리가 문자열인 경우
        if (typeof article.category === 'string') {
          return article.category;
        }
      }

      // 3. 자동분류 로직 사용
      const autoCategory = getCategoryByContent(article.title, article.content);
      console.log(`게시글 ID ${article.id} 자동분류 결과:`, autoCategory);
      return autoCategory;
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
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

/* 에러 상태 */
.error-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  text-align: center;
}

.error-state svg {
  color: #ef4444;
  margin-bottom: 16px;
}

.error-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
}

.error-message {
  font-size: 14px;
  color: #64748b;
  margin: 0 0 20px 0;
}

.retry-button {
  padding: 10px 20px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.retry-button:hover {
  background-color: #2952CC;
}

/* 빈 상태 */
.empty-state {
  grid-column: 1 / -1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  text-align: center;
}

.empty-state svg {
  color: #94a3b8;
  margin-bottom: 16px;
}

.empty-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
}

.empty-message {
  font-size: 14px;
  color: #64748b;
  margin: 0;
}

/* 더보기 버튼 */
.load-more-container {
  text-align: center;
  margin-top: 40px;
}

.load-more-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  background-color: #f8fafc;
  color: #64748b;
  text-decoration: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
  border: 1px solid #e2e8f0;
}

.load-more-button:hover {
  background-color: #f1f5f9;
  color: #3662E3;
  border-color: #3662E3;
  transform: translateY(-1px);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .topics-grid {
    grid-template-columns: 1fr;
    margin-top: 32px;
  }

  .best-topics-section {
    padding: 60px 0;
  }
}

@media (max-width: 480px) {
  .section-container {
    padding: 0 16px;
  }

  .best-topics-section {
    padding: 40px 0;
  }

  .topics-grid {
    gap: 16px;
  }
}
</style>