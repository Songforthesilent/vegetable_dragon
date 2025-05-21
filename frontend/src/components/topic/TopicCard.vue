<template>
  <div class="best-topic-card">
    <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">
      <div class="best-topic-vote-container">
        <vote-circle :true-ratio="article.ratio?.trueNewsRatio" />
        <vote-labels :true-ratio="article.ratio?.trueNewsRatio" />
      </div>
    </router-link>
    <div class="best-topic-content">
      <router-link :to="'/board/view/' + article.id" class="best-topic-title-link">
        <h3 class="best-topic-title">{{ article.title }}</h3>
      </router-link>
      <p class="best-topic-excerpt">{{ excerpt }}</p>
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
  background: linear-gradient(145deg, #f8fafc, #f1f5f9);
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.best-topic-content {
  padding: 16px;
}

.best-topic-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin: 0 0 8px 0;
  line-height: 1.4;
  transition: color 0.2s ease;
}

.best-topic-title-link {
  text-decoration: none;
}

.best-topic-card:hover .best-topic-title {
  color: #3A4CA4;
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
</style>
