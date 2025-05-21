<template>
  <div class="post-item">
    <span class="post-category">{{ categoryName }}</span>
    <router-link :to="'/board/view/' + article.id" class="post-title-link">
      {{ article.title }}
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'PostItem',
  props: {
    article: {
      type: Object,
      required: true
    }
  },
  computed: {
    categoryName() {
      return this.article.category ? this.article.category.name : '없음';
    }
  }
}
</script>

<style scoped>
.post-item {
  display: flex;
  align-items: center;
  padding: 14px 0;
  border-bottom: 1px solid #f1f5f9;
}

.post-item:last-child {
  border-bottom: none;
}

.post-category {
  display: inline-block;
  padding: 4px 10px;
  background-color: #f1f5f9;
  color: #475569;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
  margin-right: 12px;
  min-width: 50px;
  text-align: center;
}

.post-title-link {
  font-size: 15px;
  color: #1e293b;
  text-decoration: none;
  text-align: left;
  margin-left: 15px;
  font-weight: 450;
  transition: color 0.2s ease;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.post-title-link:hover {
  color: #3A4CA4;
}
</style>
