<template>
  <div class="article-content">
    <h2>{{ title }}</h2>
    <p class="content-text">{{ content }}</p>
    <a :href="link" target="_blank" class="article-link" v-if="link">기사 원문 보기</a>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true
    },
    content: {
      type: String,
      required: true
    },
    link: {
      type: String,
      default: ''
    }
  }
};
</script>

<style scoped>
.content-text{
  white-space: pre-line;
}
.article-content {
  text-align: left;
}

.article-content h2 {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 30px;
  line-height: 1.4;
}

.article-content p {
  line-height: 1.6;
  color: #333;
}

.article-link {
  display: block;
  margin-top: 20px;
  color: #3A4CA4;
  text-align: right;
  text-decoration: underline;
}
</style>
