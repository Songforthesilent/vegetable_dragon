<template>
  <header>
    <div class="header-content">
      <div id="nav">
        <router-link to="/" class="logo-link">
          <span class="logo-text"></span>
        </router-link>
      </div>
      <div class="title-container">
        <h1>{{ pageTitle }}</h1>
        <div class="title-underline"></div>
        <p class="subtitle">네가 틀렸고 내가 맞아 !</p>
      </div>
    </div>
  </header>
  <hr/>
</template>

<script>
export default {
  computed: {
    // 현재 라우터의 meta.title 가져와서 페이지 제목으로 설정
    pageTitle() {
      return this.$route.meta.title || "VEGETABLE DRAGON"; // 제목 없으면 기본 제목
    }
  }
};
</script>

<style scoped>
header {
  width: 100%;
  padding: 50px 0;
  background-color: #ffffff;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  text-align: center;
}

#nav {
  display: flex;
  justify-content: flex-start;
  padding: 10px 0;
  margin-bottom: 20px;
}

.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: #333;
}

.logo-text {
  font-weight: bold;
  font-size: 1.2rem;
}

.title-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 30px;
}

h1 {
  font-size: 3rem;
  font-weight: 800;
  margin: 0;
  color: #333;
  letter-spacing: 1px;
  text-transform: uppercase;
}

.title-underline {
  width: 100%;
  max-width: 180px;
  height: 10px;
  background-color: #FFD700;
  margin-top: -5px;
  z-index: -1;
}

.subtitle {
  font-size: 0.9rem;
  color: #666;
  margin-top: 15px;
  font-weight: 400;
}

hr {
  border: none;
  height: 1px;
  background-color: #e0e0e0;
  margin: 30px 0 0 0;
}

@media (max-width: 768px) {
  h1 {
    font-size: 2.2rem;
  }

  .title-underline {
    max-width: 140px;
    height: 8px;
  }

  .subtitle {
    font-size: 0.8rem;
  }
}
</style>