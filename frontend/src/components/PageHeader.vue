<!-- PageHeader.vue -->
<template>
  <header>
    <div id="nav">
      <router-link to="/">Vegetable_dragon</router-link>
    </div>
    <h1>{{ pageTitle }}</h1> <!-- 편재 페이지 제목 -->
  </header>
  <hr/>
</template>

<script>
export default {
  computed: {
    // 현재 라우터의 meta.title 가져와서 페이지 제목으로 설정
    pageTitle() {
      return this.$route.meta.title || ""; // 제목 없으면 기본 제목
    }
  }
};
</script>

<style scoped>
header {
  width: 100%; /* 전체 너비로 설정 */
  height: 270px;
  padding: 50px 0; /* 상하 여백을 추가하여 높이 늘리기 */
  background-color: #f7f7f7; /* 배경색 추가 (선택사항) */
  text-align: center;
}

h1 {
  font-size: 2rem;
  margin-top: 20px;
  color: #333;
  font-weight: bold;
}
</style>