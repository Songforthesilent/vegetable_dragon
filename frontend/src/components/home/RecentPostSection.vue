<template>
  <section class="recent-posts-section">
    <div class="section-container">
      <section-header title="최근 게시글" subtitle="가장 최근 게시글을 확인해보세요" more-link="/board/list" />

      <!-- 카테고리 필터 -->
      <div class="category-filter-container">
        <category-filter
            :categories="['전체', ...categories]"
            :selected-category="selectedCategory"
            @category-selected="filterCategory"
        />
      </div>

      <!-- 로딩 상태 -->
      <div v-if="loading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>최근 게시글을 불러오는 중...</p>
      </div>

      <!-- 에러 상태 -->
      <div v-else-if="error" class="error-state">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="12" cy="12" r="10"></circle>
          <line x1="15" y1="9" x2="9" y2="15"></line>
          <line x1="9" y1="9" x2="15" y2="15"></line>
        </svg>
        <h3 class="error-title">데이터를 불러올 수 없습니다</h3>
        <p class="error-message">서버에서 데이터를 가져오는 중 오류가 발생했습니다.</p>
        <button @click="fetchRecentPosts" class="retry-button">다시 시도</button>
      </div>

      <!-- 빈 상태 -->
      <div v-else-if="filteredArticles.length === 0" class="empty-state">
        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
          <polyline points="14 2 14 8 20 8"></polyline>
          <line x1="16" y1="13" x2="8" y2="13"></line>
          <line x1="16" y1="17" x2="8" y2="17"></line>
        </svg>
        <h3 class="empty-title">게시글이 없습니다</h3>
        <p class="empty-message">
          {{ selectedCategory === '전체' ? '아직 작성된 게시글이 없습니다.' : `${selectedCategory} 카테고리에 게시글이 없습니다.` }}
        </p>
        <router-link to="/board/write" class="write-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="12" y1="5" x2="12" y2="19"></line>
            <line x1="5" y1="12" x2="19" y2="12"></line>
          </svg>
          첫 게시글 작성하기
        </router-link>
      </div>

      <!-- 게시글 목록 -->
      <div v-else class="posts-container">
        <div class="recent-posts-list">
          <div
              v-for="article in filteredArticles"
              :key="article.id"
              class="post-item-card"
              @click="goToPost(article.id)"
          >
            <div class="post-header">
              <span
                  class="post-category"
                  :class="getCategoryClass(article)"
                  :style="{
                  backgroundColor: getCategoryBackgroundColor(getCategoryName(article)),
                  color: getCategoryTextColor(getCategoryName(article))
                }"
              >
                {{ getCategoryName(article) }}
              </span>
              <span class="post-date">{{ formatDate(article.createdAt) }}</span>
            </div>

            <h3 class="post-title">{{ article.title }}</h3>

            <p class="post-excerpt">{{ getExcerpt(article.content) }}</p>

            <div class="post-footer">
              <div class="post-author">
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
                {{ article.authorUsername }}
              </div>
            </div>
          </div>
        </div>

        <!-- 더보기 버튼 -->
        <div class="load-more-container">
          <router-link to="/board/list" class="load-more-button">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"></polyline>
            </svg>
            모든 게시글 보기
          </router-link>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import axios from 'axios';
import SectionHeader from '@/components/home/SectionHeader.vue';
import CategoryFilter from '@/components/home/CategoryFilter.vue';
import { getCategoryByContent, getCategoryClass, getCategoryBackgroundColor, getCategoryTextColor} from '@/utils/CategoryUtils';

export default {
  name: 'RecentPostsSection',
  components: {
    SectionHeader,
    CategoryFilter
  },
  data() {
    return {
      categories: ['경제', '연예', '정치', '사회', '국제', '문화', '기술', '기타'],
      selectedCategory: '전체',
      recentArticles: [],
      loading: false,
      error: null,
      page: 0,
      size: 5
    };
  },
  computed: {
    filteredArticles() {
      if (!Array.isArray(this.recentArticles) || this.recentArticles.length === 0) {
        return [];
      }

      if (this.selectedCategory === '전체') {
        return this.recentArticles;
      }

      return this.recentArticles.filter(article => {
        const categoryName = this.getCategoryName(article);
        return categoryName === this.selectedCategory;
      });
    }
  },
  created() {
    this.fetchRecentPosts();
  },
  methods: {
    filterCategory(category) {
      this.selectedCategory = category;
      // 자동분류 로직을 사용하므로 모든 게시글을 가져와서 프론트엔드에서 필터링
      this.fetchRecentPosts();
    },

    async fetchRecentPosts() {
      this.loading = true;
      this.error = null;

      try {
        const response = await axios.get("http://localhost:8081/posts", {
          params: {
            page: this.page,
            size: 20, // 필터링을 위해 더 많은 게시글을 가져옴
          },
        });
        this.recentArticles = response.data.content;

        // 디버깅 정보
        console.log("최근 게시글 데이터:", this.recentArticles);
        console.log("카테고리 정보:", this.recentArticles.map(post => ({
          id: post.id,
          title: post.title,
          autoCategory: this.getCategoryName(post)
        })));
      } catch (error) {
        console.error("게시글을 불러오는 데 실패했습니다.", error);
        this.error = error;
      } finally {
        this.loading = false;
      }
    },

    getCategoryName(article) {
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
      return getCategoryByContent(article.title, article.content);
    },

    getCategoryClass(article) {
      const categoryName = this.getCategoryName(article);
      return getCategoryClass(categoryName);
    },

    getCategoryBackgroundColor(categoryName) {
      return getCategoryBackgroundColor(categoryName);
    },

    getCategoryTextColor(categoryName) {
      return getCategoryTextColor(categoryName);
    },

    formatDate(dateString) {
      if (!dateString) return '';

      const date = new Date(dateString);
      const now = new Date();
      const diffMs = now - date;
      const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
      const diffDays = Math.floor(diffHours / 24);

      if (diffDays > 0) {
        return `${diffDays}일 전`;
      } else if (diffHours > 0) {
        return `${diffHours}시간 전`;
      } else {
        return '방금 전';
      }
    },

    getExcerpt(content) {
      if (!content) return '';
      return content.length > 80 ? content.substring(0, 80) + '...' : content;
    },

    goToPost(id) {
      this.$router.push(`/board/view/${id}`);
    }
  }
}
</script>

<style scoped>
/* 전체 섹션 */
.recent-posts-section {
  padding: 60px 0;
  background-color: #f8fafc;
}

.section-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 카테고리 필터 컨테이너 */
.category-filter-container {
  margin: 20px 0;
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
  background-color: #2d3a7c;
}

/* 빈 상태 */
.empty-state {
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
  margin: 0 0 20px 0;
}

.write-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background-color: #3662E3;
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  transition: all 0.2s ease;
}

.write-button:hover {
  background-color: #2d3a7c;
  transform: translateY(-1px);
}

/* 게시글 컨테이너 */
.posts-container {
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.recent-posts-list {
  display: flex;
  flex-direction: column;
}

/* 게시글 아이템 카드 */
.post-item-card {
  padding: 20px;
  border-bottom: 1px solid #f1f5f9;
  cursor: pointer;
  transition: all 0.2s ease;
}

.post-item-card:last-child {
  border-bottom: none;
}

.post-item-card:hover {
  background-color: #f8fafc;
  transform: translateX(4px);
}

/* 게시글 헤더 */
.post-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.post-category {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 6px 16px;
  border-radius: 50px;
  font-size: 11px;
  font-weight: 700;
  text-align: center;
  min-width: 50px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.1);
  transition: all 0.2s ease;
}

.post-category:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

.post-date {
  font-size: 12px;
  color: #94a3b8;
  font-weight: 500;
}

/* 게시글 제목 */
.post-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
  line-height: 1.4;
  transition: color 0.2s ease;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-align: left;
}

.post-item-card:hover .post-title {
  color: #3662E3;
}

/* 게시글 요약 */
.post-excerpt {
  font-size: 14px;
  color: #64748b;
  line-height: 1.5;
  margin: 0 0 12px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-align: left;
}

/* 게시글 푸터 */
.post-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.post-author {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #64748b;
}

.post-author svg {
  stroke: #64748b;
}

.stat-item svg {
  stroke: #94a3b8;
}

/* 더보기 버튼 */
.load-more-container {
  padding: 20px;
  text-align: center;
  border-top: 1px solid #f1f5f9;
}

.load-more-button {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
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
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .recent-posts-section {
    padding: 40px 0;
  }

  .section-container {
    padding: 0 16px;
  }

  .post-item-card {
    padding: 16px;
  }

  .post-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .post-footer {
    flex-direction: column;
    align-items: flex-end;
    gap: 8px;
  }
}

@media (max-width: 480px) {
  .post-title {
    font-size: 15px;
  }

  .post-excerpt {
    font-size: 13px;
  }

  .category-filter-container {
    margin: 16px 0;
  }
}
</style>