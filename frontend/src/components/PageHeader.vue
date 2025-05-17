<<<<<<< HEAD
<template>
  <header class="page-header">
    <div class="header-content">
      <!-- 로고 및 네비게이션 영역 -->
      <div class="header-top">
        <router-link to="/" class="logo-container">
          <div class="logo-icon">
            <svg width="36" height="36" viewBox="0 0 36 36" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M18 3C9.72 3 3 9.72 3 18C3 26.28 9.72 33 18 33C26.28 33 33 26.28 33 18C33 9.72 26.28 3 18 3ZM18 30C11.37 30 6 24.63 6 18C6 11.37 11.37 6 18 6C24.63 6 30 11.37 30 18C30 24.63 24.63 30 18 30Z" fill="#3A4CA4"/>
              <path d="M18 10.5C13.86 10.5 10.5 13.86 10.5 18C10.5 22.14 13.86 25.5 18 25.5C22.14 25.5 25.5 22.14 25.5 18C25.5 13.86 22.14 10.5 18 10.5Z" fill="#FFD700"/>
              <path d="M18 15C16.34 15 15 16.34 15 18C15 19.66 16.34 21 18 21C19.66 21 21 19.66 21 18C21 16.34 19.66 15 18 15Z" fill="#3A4CA4"/>
            </svg>
          </div>
          <div class="logo-text">VEGETABLE DRAGON</div>
        </router-link>

        <nav class="nav-links">
          <router-link to="/" class="nav-link">홈</router-link>
          <router-link to="/board/list" class="nav-link">게시판</router-link>
          <router-link to="/about" class="nav-link">소개</router-link>
        </nav>
      </div>

      <!-- 페이지 제목 영역 -->
      <div class="title-container">
        <div class="title-wrapper">
          <h1 class="page-title">{{ pageTitle }}</h1>
          <p class="subtitle">{{ pageContent }}</p>
          <div class="title-decoration">
            <div class="title-dots">
              <span class="dot"></span>
              <span class="dot"></span>
              <span class="dot"></span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="header-divider"></div>
  </header>
=======
<!-- PageHeader.vue -->
<template>
  <header>
    <div id="nav">
      <router-link to="/">Home</router-link>
    </div>
    <h1>{{ pageTitle }}</h1> <!-- 편재 페이지 제목 -->
  </header>
  <hr/>
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
</template>

<script>
export default {
<<<<<<< HEAD
  props: {
    showBreadcrumb: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    // 현재 라우터의 meta.title 가져와서 페이지 제목으로 설정
    pageTitle() {
      return this.$route.meta.title || "VEGETABLE DRAGON"; // 제목 없으면 기본 제목
    },
    pageContent(){
      return this.$route.meta.content || "네가 틀렸고 내가 맞아!";
    },
    // 현재 경로가 홈인지 확인
    isHomePage() {
      return this.$route.path === '/';
=======
  computed: {
    // 현재 라우터의 meta.title 가져와서 페이지 제목으로 설정
    pageTitle() {
      return this.$route.meta.title || "vege"; // 제목 없으면 기본 제목
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    }
  }
};
</script>

<style scoped>
<<<<<<< HEAD
.page-header {
  width: 100%;
  padding: 30px 0 20px;
  background-color: #ffffff;
  position: relative;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* 로고 및 네비게이션 영역 */
.header-top {
  display: flex;
  justify-content: space-between;
  margin-bottom: 30px;
}

.logo-container {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: #333;
  transition: transform 0.3s ease;
}

.logo-container:hover {
  transform: translateY(-2px);
}

.logo-icon {
  margin-right: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-text {
  font-weight: 800;
  font-size: 1.4rem;
  color: #3A4CA4;
  letter-spacing: 0.5px;
}

.nav-links {
  display: flex;
  gap: 20px;
}

.nav-link {
  text-decoration: none;
  color: #555;
  font-weight: 500;
  font-size: 1rem;
  padding: 5px 10px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.nav-link:hover {
  color: #3A4CA4;
  background-color: rgba(58, 76, 164, 0.05);
}

.nav-link.router-link-active {
  color: #3A4CA4;
  font-weight: 600;
}

/* 페이지 제목 영역 */
.title-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
  position: relative;
}

.title-wrapper {
  position: relative;
  display: inline-block;
  margin-bottom: 15px;
}

.page-title {
  font-size: 2.6rem;
  font-weight: 600;
  margin-top: 20px;
  color: #333;
  letter-spacing: 1px;
  text-transform: uppercase;
  position: relative;
  z-index: 2;
}

.title-decoration {
  position: relative;
  height: 20px;
}

.title-dots {
  display: flex;
  gap: 8px;
  right: -20px;
  position: absolute;
  bottom: 0;
}

.dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background-color: #3A4CA4;
  display: inline-block;
}

.subtitle {
  font-size: 15px;
  color: #666;
  margin: 20px 0 20px 0;
  font-weight: 500;
  font-style: italic;
}

/* 구분선 */
.header-divider {
  height: 1px;
  background: linear-gradient(to right, transparent, #e0e0e0, transparent);
  margin-top: 30px;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .header-top {
    flex-direction: column;
    gap: 20px;
    align-items: center;
  }

  .nav-links {
    width: 100%;
    justify-content: center;
  }

  .page-title {
    font-size: 2.2rem;
    text-align: center;
  }

  .title-underline {
    max-width: 140px;
    height: 8px;
  }

  .subtitle {
    font-size: 0.9rem;
    text-align: center;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 1.8rem;
  }

  .nav-link {
    font-size: 0.9rem;
    padding: 5px 8px;
  }

  .logo-text {
    font-size: 1.2rem;
  }
}
</style>
=======
header {
  width: 100%; /* 전체 너비로 설정 */
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
