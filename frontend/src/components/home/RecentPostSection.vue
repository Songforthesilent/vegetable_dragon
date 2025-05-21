<template>
  <section class="article-section">
    <section-header title="최근 게시글" more-link="/board/list" />

    <!-- 카테고리 필터 -->
    <category-filter
        :categories="['전체', ...categories]"
        :selected-category="selectedCategory"
        @category-selected="filterCategory"
    />

    <!-- 게시글 목록 -->
    <div v-if="loading" class="loading-state">
      데이터를 불러오는 중...
    </div>
    <div v-else-if="error" class="error-state">
      데이터를 불러오는데 실패했습니다.
    </div>
    <div v-else-if="filteredArticles.length === 0" class="empty-state">
      게시글이 없습니다.
    </div>
    <div v-else class="recent-posts-list">
      <post-item
          v-for="article in filteredArticles"
          :key="article.id"
          :article="article"
      />
    </div>
  </section>
</template>

<script>
import axios from 'axios';
import SectionHeader from '@/components/home/SectionHeader.vue';
import CategoryFilter from '@/components/home/CategoryFilter.vue';
import PostItem from '@/components/home/PostItem.vue';

export default {
  name: 'RecentPostsSection',
  components: {
    SectionHeader,
    CategoryFilter,
    PostItem
  },
  data() {
    return {
      categories: ['경제', '연예', '정치', '사회', '국제', '문화'],
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
      if (Array.isArray(this.recentArticles) && this.recentArticles.length > 0) {
        const categoryToCompare = this.selectedCategory.toLowerCase().trim();
        if (this.selectedCategory === '전체') {
          return this.recentArticles;
        } else {
          return this.recentArticles.filter(article =>
              article.category && article.category.name.toLowerCase().trim() === categoryToCompare
          );
        }
      }
      return [];
    }
  },
  created() {
    this.fetchRecentPosts();
  },
  methods: {
    filterCategory(category) {
      this.selectedCategory = category;
      this.fetchRecentPosts();
    },

    async fetchRecentPosts() {
      this.loading = true;
      this.error = null;

      try {
        if (this.selectedCategory === "전체") {
          const response = await axios.get("http://localhost:8081/posts", {
            params: {
              page: this.page,
              size: this.size,
            },
          });
          this.recentArticles = response.data.content;
        } else {
          const url = `http://localhost:8081/posts/category/${this.selectedCategory}`;
          const response = await axios.get(url, {
            params: {
              page: this.page,
              size: this.size,
            },
          });
          this.recentArticles = response.data;
        }
        this.loading = false;
      } catch (error) {
        console.error("게시글을 불러오는 데 실패했습니다.", error);
        this.error = error;
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.article-section {
  width: 100%;
  padding: 0px;
  margin: 20px 0 30px 0;
  background-color: #ffffff;
}

.recent-posts-list {
  display: flex;
  flex-direction: column;
}

.loading-state, .error-state, .empty-state {
  padding: 20px;
  text-align: center;
  color: #64748b;
  font-size: 14px;
}

.error-state {
  color: #ef4444;
}
</style>
