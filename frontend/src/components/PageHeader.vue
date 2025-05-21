<template>
  <header class="page-header">
    <div class="header-content">
      <!-- 로고 및 네비게이션 영역 -->
      <div class="header-top">
        <router-link to="/" class="logo-container">
          <div class="logo-icon">
            <svg width="40" height="40" viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
              <!-- Brain + Network icon representing collective intelligence -->
              <path d="M20 4C11.16 4 4 11.16 4 20C4 28.84 11.16 36 20 36C28.84 36 36 28.84 36 20C36 11.16 28.84 4 20 4Z" fill="#f0f4ff" stroke="#3A4CA4" stroke-width="2"/>
              <!-- Brain hemispheres -->
              <path d="M20 10C14.48 10 10 14.48 10 20C10 25.52 14.48 30 20 30C25.52 30 30 25.52 30 20C30 14.48 25.52 10 20 10Z" fill="#FFD700" stroke="#3A4CA4" stroke-width="1.5"/>
              <!-- Neural network connections -->
              <circle cx="16" cy="16" r="2" fill="#3A4CA4"/>
              <circle cx="24" cy="16" r="2" fill="#3A4CA4"/>
              <circle cx="20" cy="24" r="2" fill="#3A4CA4"/>
              <circle cx="14" cy="22" r="1.5" fill="#3A4CA4"/>
              <circle cx="26" cy="22" r="1.5" fill="#3A4CA4"/>
              <line x1="16" y1="16" x2="24" y2="16" stroke="#3A4CA4" stroke-width="1"/>
              <line x1="16" y1="16" x2="14" y2="22" stroke="#3A4CA4" stroke-width="1"/>
              <line x1="24" y1="16" x2="26" y2="22" stroke="#3A4CA4" stroke-width="1"/>
              <line x1="14" y1="22" x2="20" y2="24" stroke="#3A4CA4" stroke-width="1"/>
              <line x1="26" y1="22" x2="20" y2="24" stroke="#3A4CA4" stroke-width="1"/>
            </svg>
          </div>
          <div class="logo-text">
            <span class="logo-main">VEGETABLE DRAGON</span>
            <span class="logo-tagline">네가 틀렸고 내가 맞아!</span>
          </div>
        </router-link>

        <nav class="nav-links">
          <router-link to="/" class="nav-link">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
              <polyline points="9 22 9 12 15 12 15 22"></polyline>
            </svg>
            홈
          </router-link>
          <router-link to="/board/list" class="nav-link">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
              <polyline points="14 2 14 8 20 8"></polyline>
              <line x1="16" y1="13" x2="8" y2="13"></line>
              <line x1="16" y1="17" x2="8" y2="17"></line>
              <polyline points="10 9 9 9 8 9"></polyline>
            </svg>
            게시판
          </router-link>
          <router-link to="/about" class="nav-link">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="12" cy="12" r="10"></circle>
              <line x1="12" y1="16" x2="12" y2="12"></line>
              <line x1="12" y1="8" x2="12.01" y2="8"></line>
            </svg>
            소개
          </router-link>
        </nav>
      </div>

      <!-- 페이지 제목 영역 -->
      <div class="title-container">
        <div class="title-wrapper">
          <h1 class="page-title">{{ pageTitle }}</h1>
          <div class="title-decoration">
            <div class="title-underline"></div>
          </div>
        </div>
        <p class="subtitle">{{ pageContent }}</p>

        <!-- 현재 페이지 위치 표시 (브레드크럼) -->
        <div class="breadcrumb" v-if="showBreadcrumb && !isHomePage">
          <router-link to="/" class="breadcrumb-item">홈</router-link>
          <span class="breadcrumb-separator">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="9 18 15 12 9 6"></polyline>
            </svg>
          </span>
          <span class="breadcrumb-item current">{{ pageTitle }}</span>
        </div>
      </div>
    </div>

    <!--<div class="header-divider"></div>-->
  </header>
</template>

<script>
export default {
  props: {
    showBreadcrumb: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    // 현재 라우터의 meta.title 가져와서 페이지 제목으로 설정
    pageTitle() {
      return this.$route.meta.title || " "; // 제목 없으면 기본 제목
    },
    pageContent() {
      return this.$route.meta.content || " ";
    },
    // 현재 경로가 홈인지 확인
    isHomePage() {
      return this.$route.path === '/';
    }
  }
};
</script>

<style scoped>
.page-header {
  width: 100%;
  padding: 20px 0 15px;
  background-color: #ffffff;
  position: relative;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 5px;
}

/* 로고 및 네비게이션 영역 */
.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
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
  margin-right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-text {
  display: flex;
  flex-direction: column;
}

.logo-main {
  font-weight: 800;
  font-size: 1.2rem;
  color: #3A4CA4;
  letter-spacing: 0.5px;
}

.logo-tagline {
  font-size: 0.65rem;
  color: #666;
  letter-spacing: 0.3px;
}

.nav-links {
  display: flex;
  gap: 15px;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 5px;
  text-decoration: none;
  color: #555;
  font-weight: 500;
  font-size: 0.9rem;
  padding: 6px 10px;
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
  background-color: rgba(58, 76, 164, 0.08);
}

/* 페이지 제목 영역 */
.title-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
  position: relative;
}

.title-wrapper {
  position: relative;
  display: inline-block;
  margin-bottom: 8px;
}

.page-title {
  font-size: 2rem;
  font-weight: 700;
  margin: 0;
  color: #333;
  letter-spacing: 0.5px;
  position: relative;
  z-index: 2;
}

.title-decoration {
  position: relative;
  height: 8px;
}

.title-underline {
  width: 100%;
  height: 6px;
  background-color: #FFD700;
  position: absolute;
  bottom: 6px;
  left: 0;
  z-index: 1;
  border-radius: 3px;
}

.subtitle {
  font-size: 0.95rem;
  color: #666;
  margin: 0;
  font-weight: 500;
  font-style: italic;
}

/* 브레드크럼 네비게이션 */
.breadcrumb {
  display: flex;
  align-items: center;
  margin-top: 10px;
  font-size: 0.8rem;
}

.breadcrumb-item {
  color: #777;
  text-decoration: none;
}

.breadcrumb-item:hover {
  color: #3A4CA4;
  text-decoration: underline;
}

.breadcrumb-item.current {
  color: #3A4CA4;
  font-weight: 500;
}

.breadcrumb-separator {
  display: flex;
  align-items: center;
  margin: 0 6px;
  color: #ccc;
}

/* 구분선 */
.header-divider {
  height: 1px;
  background: linear-gradient(to right, transparent, #e0e0e0, transparent);
  margin-top: 15px;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .header-top {
    flex-direction: column;
    gap: 15px;
    align-items: center;
  }

  .nav-links {
    width: 100%;
    justify-content: center;
  }

  .page-title {
    font-size: 1.8rem;
    text-align: center;
  }

  .title-underline {
    max-width: 120px;
    height: 5px;
  }

  .subtitle {
    font-size: 0.85rem;
    text-align: center;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 1.5rem;
  }

  .nav-link {
    font-size: 0.8rem;
    padding: 5px 8px;
  }

  .logo-main {
    font-size: 1rem;
  }

  .logo-tagline {
    font-size: 0.55rem;
  }
}
</style>