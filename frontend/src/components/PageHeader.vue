<template>
  <header class="page-header">
    <!-- 메인 네비게이션 바 -->
    <nav class="main-nav">
      <div class="nav-container">
        <!-- 브랜드 로고 및 타이틀 -->
        <div class="brand-section">
          <div class="brand-logo">
            <div class="logo-icon">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 8C8 8 5 11 5 15C5 19 8 22 12 22C16 22 19 19 19 15C19 11 16 8 12 8Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <!-- 잎사귀들 -->
                <path d="M8 8C6 6 6 4 8 3C10 4 10 6 8 8Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M12 7C12 5 13 3 15 2C16 3 15 5 12 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M16 8C18 6 18 4 16 3C14 4 14 6 16 8Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <!-- 뿌리 무늬 -->
                <path d="M10 12L14 12" stroke="currentColor" stroke-width="1" stroke-linecap="round"/>
                <path d="M9 15L15 15" stroke="currentColor" stroke-width="1" stroke-linecap="round"/>
                <path d="M10 18L14 18" stroke="currentColor" stroke-width="1" stroke-linecap="round"/>
              </svg>
            </div>
          </div>
          <div class="brand-text">
            <h1 class="brand-title">VTDG</h1>
            <p class="brand-subtitle">Vegetable Dragon</p>
          </div>
        </div>

        <!-- 네비게이션 메뉴 -->
        <div class="nav-menu">
          <router-link to="/" class="nav-item" :class="{ active: $route.path === '/' }">
            홈
          </router-link>
          <router-link to="/board/list" class="nav-item" :class="{ active: $route.path.includes('/board') }">
            전체 게시판
          </router-link>
          <router-link to="/best-topics" class="nav-item" :class="{ active: $route.path === '/best-topics' }">
            인기 주제
          </router-link>
        </div>

        <!-- 로그인 배너 섹션 -->
        <div class="login-banner-section">
          <!-- 로그인 전 화면 -->
          <div v-if="!isLoggedIn" class="auth-buttons">
            <button class="login-button" @click="handleLogin">
              로그인
            </button>
            <button class="start-button" @click="handleStart">
              시작하기
            </button>
          </div>

          <!-- 로그인 후 화면 -->
          <div v-else class="user-section">
            <!-- 새 토론 버튼 -->
            <button class="new-discussion-button" @click="handleNewDiscussion">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <line x1="12" y1="5" x2="12" y2="19"></line>
                <line x1="5" y1="12" x2="19" y2="12"></line>
              </svg>
              새 토론
            </button>

            <!-- 사용자 프로필 -->
            <div class="user-profile" @click="toggleUserMenu">
              <div class="profile-avatar">
                {{ userInitial }}
              </div>

              <!-- 사용자 드롭다운 메뉴 -->
              <div v-if="showUserMenu" class="user-dropdown">
                <div class="user-info">
                  <div class="user-name">{{ userName }}</div>
                  <div class="user-email">{{ userEmail }}</div>
                </div>
                <div class="dropdown-divider"></div>
                <router-link to="/mypage" class="dropdown-item" @click="closeUserMenu">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                  </svg>
                  마이페이지
                </router-link>
                <a href="#" class="dropdown-item" @click="handleLogout">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                    <polyline points="16 17 21 12 16 7"></polyline>
                    <line x1="21" y1="12" x2="9" y2="12"></line>
                  </svg>
                  로그아웃
                </a>
              </div>
            </div>
          </div>
        </div>

        <!-- 모바일 햄버거 메뉴 -->
        <button class="mobile-menu-button" @click="toggleMobileMenu">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="3" y1="6" x2="21" y2="6"></line>
            <line x1="3" y1="12" x2="21" y2="12"></line>
            <line x1="3" y1="18" x2="21" y2="18"></line>
          </svg>
        </button>
      </div>
    </nav>

    <!-- 모바일 메뉴 -->
    <div v-if="showMobileMenu" class="mobile-menu">
      <div class="mobile-menu-content">
        <router-link to="/" class="mobile-nav-item" @click="closeMobileMenu">홈</router-link>
        <router-link to="/board/list" class="mobile-nav-item" @click="closeMobileMenu">토론 게시판</router-link>
        <router-link to="/best-topics" class="mobile-nav-item" @click="closeMobileMenu">커뮤니티</router-link>
        <a href="#" class="mobile-nav-item" @click="closeMobileMenu">가이드</a>

        <div class="mobile-search">
          <input
              type="text"
              placeholder="토론 주제를 검색해보세요..."
              class="mobile-search-input"
              v-model="searchQuery"
              @keyup.enter="handleSearch"
          />
        </div>

        <!-- 로그인 섹션 -->
        <div v-if="!isLoggedIn" class="mobile-auth-section">
          <button class="mobile-login-button" @click="handleLogin">로그인</button>
          <button class="mobile-start-button" @click="handleStart">시작하기</button>
        </div>

        <div v-else class="mobile-user-section">
          <button class="mobile-new-discussion" @click="handleNewDiscussion">
            새 토론 시작하기
          </button>
          <div class="mobile-user-info">
            <span>{{ userName }}님</span>
            <button @click="handleLogout" class="mobile-logout">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                <polyline points="16 17 21 12 16 7"></polyline>
                <line x1="21" y1="12" x2="9" y2="12"></line>
              </svg>로그아웃
            </button>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import axios from 'axios';

export default {
  name: 'PageHeader',
  data() {
    return {
      searchQuery: '',
      showMobileMenu: false,
      showUserMenu: false,
      isLoggedIn: false,
      userName: '',
      userEmail: ''
    }
  },
  computed: {
    userInitial() {
      return this.userName ? this.userName.charAt(0).toUpperCase() : 'U';
    }
  },
  watch: {
    // 라우트 변경 감지하여 로그인 상태 확인
    '$route'(to, from) {
      // 로그인/회원가입 페이지에서 다른 페이지로 이동할 때 로그인 상태 재확인
      if (from.path === '/login' || from.path === '/register') {
        this.checkLoginStatus();
      }
    }
  },
  async mounted() {
    await this.checkLoginStatus();

    // 모바일 메뉴 외부 클릭 시 닫기
    document.addEventListener('click', (event) => {
      const header = this.$el;
      if (!header.contains(event.target)) {
        this.showMobileMenu = false;
        this.showUserMenu = false;
      }
    });

    // 윈도우 포커스 이벤트 리스너 추가 (탭 전환 시 로그인 상태 확인)
    window.addEventListener('focus', this.handleWindowFocus);

    // 커스텀 이벤트 리스너 추가 (로그인 성공 시)
    window.addEventListener('loginSuccess', this.handleLoginSuccess);
  },
  beforeUnmount() {
    // 이벤트 리스너 정리
    window.removeEventListener('focus', this.handleWindowFocus);
    window.removeEventListener('loginSuccess', this.handleLoginSuccess);
  },
  methods: {
    async checkLoginStatus() {
      try {
        const response = await axios.get('http://localhost:8081/join/session', {
          withCredentials: true
        });

        if (response.data) {
          this.isLoggedIn = true;
          this.userName = response.data.username || response.data;
          this.userEmail = response.data.email || '';
        } else {
          this.isLoggedIn = false;
          this.userName = '';
          this.userEmail = '';
        }
      } catch (error) {
        console.log('로그인되지 않음');
        this.isLoggedIn = false;
        this.userName = '';
        this.userEmail = '';
      }
    },

    // 윈도우 포커스 시 로그인 상태 확인
    handleWindowFocus() {
      this.checkLoginStatus();
    },

    // 로그인 성공 이벤트 핸들러
    handleLoginSuccess() {
      this.checkLoginStatus();
    },

    handleSearch() {
      if (this.searchQuery.trim()) {
        this.$router.push({
          path: '/search',
          query: {q: this.searchQuery}
        });
        this.searchQuery = '';
        this.closeMobileMenu();
      }
    },

    handleLogin() {
      this.$router.push('/login');
      this.closeMobileMenu();
    },

    handleStart() {
      this.$router.push('/register');
      this.closeMobileMenu();
    },

    handleNewDiscussion() {
      this.$router.push('/board/write');
      this.closeMobileMenu();
    },

    async handleLogout() {
      try {
        await axios.post('http://localhost:8081/join/logout', {}, {
          withCredentials: true
        });

        // 로그인 상태 초기화
        this.isLoggedIn = false;
        this.userName = '';
        this.userEmail = '';

        // 메뉴 닫기
        this.closeUserMenu();
        this.closeMobileMenu();

        // localStorage 정리
        localStorage.removeItem('username');

        // 홈으로 리다이렉트
        this.$router.push('/');

        // 페이지 새로고침으로 완전한 상태 초기화
        setTimeout(() => {
          window.location.reload();
        }, 100);
      } catch (error) {
        console.error('로그아웃 실패:', error);
        // 에러가 발생해도 클라이언트 상태는 초기화
        this.isLoggedIn = false;
        this.userName = '';
        this.userEmail = '';
        localStorage.removeItem('username');
        this.$router.push('/');
      }
    },

    toggleMobileMenu() {
      this.showMobileMenu = !this.showMobileMenu;
      this.showUserMenu = false;
    },

    toggleUserMenu() {
      this.showUserMenu = !this.showUserMenu;
    },

    closeMobileMenu() {
      this.showMobileMenu = false;
    },

    closeUserMenu() {
      this.showUserMenu = false;
    }
  }
}
</script>

<style scoped>
/* 전체 헤더 스타일 */
.page-header {
  width: 100%;
  background-color: #ffffff;
  border-bottom: 1px solid #e2e8f0;
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

/* 메인 네비게이션 */
.main-nav {
  width: 100%;
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

/* 브랜드 섹션 */
.brand-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-logo {
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-icon {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #3b82f6 0%, #7D3DE1 100%);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.brand-text {
  display: flex;
  flex-direction: column;
}

.brand-title {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
  line-height: 1.2;
}

.brand-subtitle {
  font-size: 12px;
  color: #64748b;
  margin: 0;
  line-height: 1;
}

/* 네비게이션 메뉴 */
.nav-menu {
  display: flex;
  align-items: center;
  gap: 32px;
}

.nav-item {
  position: relative;
  color: #475569;
  text-decoration: none;
  font-size: 15px;
  font-weight: 500;
  padding: 8px 0;
  transition: color 0.2s ease;
}

.nav-item:hover {
  color: #3662E3;
}

.nav-item.active {
  color: #3662E3;
}

.nav-item.active::after {
  content: '';
  position: absolute;
  bottom: -16px;
  left: 0;
  right: 0;
  height: 2px;
  background-color: #3662E3;
  border-radius: 1px;
}

/* 로그인 배너 섹션 */
.login-banner-section {
  display: flex;
  align-items: center;
}

/* 로그인 전 버튼들 */
.auth-buttons {
  display: flex;
  align-items: center;
  gap: 8px;
}

.login-button {
  padding: 8px 16px;
  background-color: transparent;
  color: #64748b;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.login-button:hover {
  background-color: #f8fafc;
  color: #3662E3;
  border-color: #3662E3;
}

.start-button {
  padding: 8px 16px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.start-button:hover {
  background-color: #2952cc;
  transform: translateY(-1px);
}

/* 로그인 후 사용자 섹션 */
.user-section {
  display: flex;
  align-items: center;
  gap: 8px;
}

.new-discussion-button {
  display: flex;
  align-items: center;
  gap: 6px;
  padding:7px 12px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.new-discussion-button:hover {
  background-color: #2952cc;
  transform: translateY(-1px);
}

/* 사용자 프로필 */
.user-profile {
  position: relative;
}

.profile-avatar {
  width: 32px;
  height: 34px;
  background-color: #e2e8ff;
  color: white;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
  margin-left: 4px;
}

.profile-avatar:hover {
  transform: scale(1.05);
}

/* 사용자 드롭다운 메뉴 */
.user-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  margin-top: 8px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  border: 1px solid #e2e8f0;
  min-width: 200px;
  z-index: 50;
  overflow: hidden;
}

.user-info {
  padding: 16px;
  background-color: #f8fafc;
}

.user-name {
  font-weight: 600;
  color: #1e293b;
  font-size: 14px;
}

.user-email {
  font-size: 12px;
  color: #64748b;
  margin-top: 2px;
}

.dropdown-divider {
  height: 1px;
  background-color: #e2e8f0;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  color: #475569;
  text-decoration: none;
  font-size: 14px;
  transition: background-color 0.2s ease;
}

.dropdown-item:hover {
  background-color: #f8fafc;
  color: #3662E3;
}

/* 모바일 햄버거 메뉴 */
.mobile-menu-button {
  display: none;
  background: none;
  border: none;
  color: #64748b;
  cursor: pointer;
  padding: 8px;
  border-radius: 6px;
  transition: background-color 0.2s ease;
}

.mobile-menu-button:hover {
  background-color: #f1f5f9;
}

/* 모바일 메뉴 */
.mobile-menu {
  display: none;
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  background-color: white;
  border-bottom: 1px solid #e2e8f0;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  z-index: 50;
}

.mobile-menu-content {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.mobile-nav-item {
  color: #475569;
  text-decoration: none;
  font-size: 16px;
  font-weight: 500;
  padding: 12px 0;
  border-bottom: 1px solid #f1f5f9;
  transition: color 0.2s ease;
}

.mobile-nav-item:hover {
  color: #3662E3;
}

.mobile-search {
  margin: 8px 0;
}

.mobile-search-input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 16px;
  background-color: #f8fafc;
}

.mobile-search-input:focus {
  outline: none;
  border-color: #3662E3;
  background-color: #ffffff;
}

/* 모바일 로그인 섹션 */
.mobile-auth-section {
  display: flex;
  gap: 12px;
  margin-top: 16px;
}

.mobile-login-button {
  flex: 1;
  padding: 12px 16px;
  background-color: transparent;
  color: #64748b;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.mobile-login-button:hover {
  background-color: #f8fafc;
  color: #3662E3;
  border-color: #3662E3;
}

.mobile-start-button {
  flex: 1;
  padding: 12px 16px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.mobile-start-button:hover {
  background-color: #2952cc;
}

.mobile-user-section {
  margin-top: 16px;
}

.mobile-new-discussion {
  width: 100%;
  padding: 12px 16px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s ease;
  margin-bottom: 16px;
}

.mobile-new-discussion:hover {
  background-color: #2952cc;
}

.mobile-user-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-top: 1px solid #e2e8f0;
}

.mobile-logout {
  padding: 6px 12px;
  background-color: #f8fafc;
  color: #64748b;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.mobile-logout:hover {
  background-color: #f1f5f9;
  color: #ef4444;
  border-color: #ef4444;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .search-section {
    max-width: 300px;
    margin: 0 20px;
  }

  .nav-menu {
    gap: 24px;
  }
}

@media (max-width: 768px) {
  .nav-menu,
  .search-section,
  .notification-button {
    display: none;
  }

  .mobile-menu-button {
    display: block;
  }

  .mobile-menu {
    display: block;
  }

  .brand-title {
    font-size: 18px;
  }

  .brand-subtitle {
    font-size: 11px;
  }
}

@media (max-width: 480px) {
  .nav-container {
    padding: 0 16px;
  }

  .brand-section {
    gap: 8px;
  }

  .logo-icon {
    width: 32px;
    height: 32px;
  }

  .brand-title {
    font-size: 16px;
  }

  .auth-buttons {
    gap: 6px;
  }

  .login-button,
  .start-button {
    padding: 6px 12px;
    font-size: 13px;
  }

  .new-discussion-button {
    padding: 6px 12px;
    font-size: 13px;
  }

  .profile-avatar {
    width: 32px;
    height: 32px;
    font-size: 12px;
  }
}
</style>