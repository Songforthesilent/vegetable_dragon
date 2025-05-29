<template>
  <div class="topic-card-section">
    <div class="best-topic-card">
      <div class="best-topic-content">
        <!-- 카테고리 -->
        <div class="topic-header">
          <span class="topic-category">{{ category }}</span>
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
          <vote-circle :true-ratio="article.ratio?.trueNewsRatio" />
          <vote-labels :true-ratio="article.ratio?.trueNewsRatio" />
        </div>
      </router-link>
    </div>
  </div>

</template>

<script>
import VoteCircle from '@/components/vote/VoteCircle.vue';
import VoteLabels from '@/components/vote/VoteLabels.vue';

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
      // 카테고리 자동 분류 로직
      if (!this.article.title && !this.article.content) return '토론';

      const text = (this.article.title + ' ' + (this.article.content || '')).toLowerCase();

      if (text.includes('정치') || text.includes('선거') || text.includes('정부') || text.includes('국회')) {
        return '정치';
      } else if (text.includes('경제') || text.includes('금융') || text.includes('투자') || text.includes('주식')) {
        return '경제';
      } else if (text.includes('사회') || text.includes('복지') || text.includes('교육') || text.includes('의료')) {
        return '사회';
      } else if (text.includes('문화') || text.includes('예술') || text.includes('영화') || text.includes('음악')) {
        return '문화';
      } else if (text.includes('기술') || text.includes('IT') || text.includes('AI') || text.includes('과학')) {
        return '기술';
      } else if (text.includes('환경') || text.includes('기후') || text.includes('에너지')) {
        return '환경';
      } else if (text.includes('스포츠') || text.includes('축구') || text.includes('야구') || text.includes('올림픽')) {
        return '스포츠';
      } else {
        return '토론';
      }
    }
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
  background: #3662E3;
  color: white;
  padding: 4px 12px;
  border-radius: 16px;
  font-size: 12px;
  font-weight: 600;
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
    font-size: 11px;
    padding: 3px 10px;
  }

  .topic-date {
    font-size: 11px;
  }
}
</style>