<template>
  <div class="topic-card-section">
    <div class="best-topic-card">
      <div class="best-topic-content">
        <!-- 카테고리 -->
        <div class="topic-header">
          <span class="topic-category" :class="getCategoryClass(category)">{{ category }}</span>
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

      // 3. 기존 자동 분류 로직 (fallback)
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
      } else if (text.includes('연예') || text.includes('엔터테인먼트') || text.includes('아이돌') || text.includes('배우')) {
        return '연예';
      } else if (text.includes('국제') || text.includes('해외') || text.includes('외교')) {
        return '국제';
      } else {
        return '기타';
      }
    }
  },
  methods: {
    getCategoryClass(category) {
      const classMap = {
        '전체': 'category-all',
        '사회': 'category-society',
        '정치': 'category-politics',
        '문화': 'category-culture',
        '연예': 'category-entertainment',
        '경제': 'category-economy',
        '국제': 'category-international',
        '기술': 'category-tech',
        '환경': 'category-environment',
        '스포츠': 'category-sports',
        '기타': 'category-default'
      };
      return classMap[category] || 'category-default';
    },
    getCategoryTextColor(category) {
      const colorMap = {
        '전체': '#475569',    // 슬레이트 700
        '사회': '#166534',    // 그린 800
        '정치': '#b91c1c',    // 레드 700
        '문화': '#7c3aed',    // 바이올렛 600
        '연예': '#ea580c',    // 오렌지 600
        '경제': '#0f766e',    // 틸 700
        '국제': '#0369a1',    // 스카이 700
        '기술': '#4338ca',    // 인디고 700
        '환경': '#15803d',    // 그린 700
        '스포츠': '#be123c',  // 로즈 700
        '기타': '#475569'     // 슬레이트 700
      };
      return colorMap[category] || '#475569';
    },
    getCategoryProgressColor(category) {
      const colorMap = {
        '전체': '#64748b',    // 슬레이트 500
        '사회': '#22c55e',    // 그린 500
        '정치': '#ef4444',    // 레드 500
        '문화': '#a855f7',    // 바이올렛 500
        '연예': '#f97316',    // 오렌지 500
        '경제': '#14b8a6',    // 틸 500
        '국제': '#0ea5e9',    // 스카이 500
        '기술': '#6366f1',    // 인디고 500
        '환경': '#22c55e',    // 그린 500
        '스포츠': '#f43f5e',  // 로즈 500
        '기타': '#64748b'     // 슬레이트 500
      };
      return colorMap[category] || '#64748b';
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
  padding: 5px 14px;
  border-radius: 50px;
  font-size: 12px;
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

/* 카테고리별 색상 - 새로운 스타일 */
.category-all {
  background-color: #f1f5f9;
  color: #475569;
}

.category-society {
  background-color: #dcfce7;
  color: #166534;
}

.category-politics {
  background-color: #fee2e2;
  color: #b91c1c;
}

.category-culture {
  background-color: #ede9fe;
  color: #7c3aed;
}

.category-entertainment {
  background-color: #fed7aa;
  color: #ea580c;
}

.category-economy {
  background-color: #ccfbf1;
  color: #0f766e;
}

.category-international {
  background-color: #e0f2fe;
  color: #0369a1;
}

.category-tech {
  background-color: #e0e7ff;
  color: #4338ca;
}

.category-environment {
  background-color: #dcfce7;
  color: #15803d;
}

.category-sports {
  background-color: #ffe4e6;
  color: #be123c;
}

.category-default {
  background-color: #f1f5f9;
  color: #475569;
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