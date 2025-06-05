<template>
  <div class="topic-card-section">
    <div class="best-topic-card">
      <div class="best-topic-content">
        <!-- 카테고리 -->
        <div class="topic-header">
          <span
              class="topic-category"
              :class="getCategoryClass(category)"
              :style="{
              backgroundColor: getCategoryBackgroundColor(category),
              color: getCategoryTextColor(category)
            }"
          >
            {{ category }}
          </span>
          <span class="topic-date">{{ formattedDate }}</span>
        </div>

        <!-- 제목 -->
        <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">
          <h3 class="best-topic-title">{{ article.title }}</h3>
        </router-link>
      </div>

      <!-- 투표결과 -->
      <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">
        <div class="best-topic-vote-container">
          <vote-circle
              :true-ratio="article.ratio?.trueNewsRatio"
              :category-color="getCategoryProgressColor(category)"
          />
          <vote-labels
              :true-ratio="article.ratio?.trueNewsRatio"
              :category-color="getCategoryTextColor(category)"
          />
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import VoteCircle from '@/components/vote/VoteCircle.vue';
import VoteLabels from '@/components/vote/VoteLabels.vue';
import {
  getCategoryByContent,
  getCategoryClass,
  getCategoryBackgroundColor,
  getCategoryTextColor,
  getCategoryProgressColor
} from '@/utils/CategoryUtils';

export default {
  name: 'TopicCard',
  components: {
    VoteCircle,
    VoteLabels
  },
  props: {
    article: {
      type: Object,
      required: true
    }
  },
  computed: {
    excerpt() {
      if (!this.article.content) return '';
      return this.article.content.length > 100
          ? this.article.content.substring(0, 100) + '...'
          : this.article.content;
    },
    formattedDate() {
      if (!this.article.createdAt) return '';
      const date = new Date(this.article.createdAt);
      return date.toISOString().split('T')[0];
    },
    category() {
      // 1. API에서 가져온 카테고리 정보 우선 사용
      if (this.article.categoryName) {
        return this.article.categoryName;
      }

      // 2. 카테고리 객체가 있는 경우
      if (this.article.category) {
        // 카테고리가 객체이고 name 속성이 있는 경우
        if (typeof this.article.category === 'object' && this.article.category.name) {
          return this.article.category.name;
        }

        // 카테고리가 문자열인 경우
        if (typeof this.article.category === 'string') {
          return this.article.category;
        }
      }

      // 3. 자동분류 로직 사용
      return getCategoryByContent(this.article.title, this.article.content);
    }
  },
  methods: {
    getCategoryClass,
    getCategoryBackgroundColor,
    getCategoryTextColor,
    getCategoryProgressColor
  }
}
</script>

<style scoped>
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

.best-topic-vote-container {
  padding: 15px;
  background-color: #ffffff;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.best-topic-content {
  padding: 16px;
}

/* 카테고리 헤더 */
.topic-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.topic-category {
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

.topic-category:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
}

.topic-date {
  color: #94a3b8;
  font-size: 12px;
  font-weight: 500;
}

.best-topic-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
  line-height: 1.4;
  text-align: left;
  transition: color 0.2s ease;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.best-topic-title-link {
  text-decoration: none;
}

.best-topic-card:hover .best-topic-title {
  color: #3662E3;
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

/* 반응형 */
@media (max-width: 768px) {
  .best-topic-content {
    padding: 12px;
  }

  .best-topic-title {
    font-size: 15px;
  }

  .topic-category {
    font-size: 10px;
    padding: 5px 14px;
    min-width: 45px;
  }

  .topic-date {
    font-size: 11px;
  }
}
</style>