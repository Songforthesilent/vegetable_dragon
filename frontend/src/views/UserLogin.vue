<template>
  <div class="login-page">
    <!-- 배경 장식 -->
    <div class="background-decoration">
      <div class="decoration-circle circle-1"></div>
      <div class="decoration-circle circle-2"></div>
      <div class="decoration-circle circle-3"></div>
    </div>

    <div class="login-container">
      <!-- 헤더 섹션 -->
      <div class="login-header">
        <div class="welcome-section">
          <h2>로그인</h2>
          <p class="welcome-text">마자용에 오신 것을 환영합니다!</p>
        </div>
      </div>

      <!-- 로딩 오버레이 -->
      <div v-if="isLoading" class="loading-overlay">
        <div class="loading-spinner"></div>
        <p>로그인 중입니다...</p>
      </div>

      <!-- 로그인 폼 -->
      <form @submit.prevent="login" class="login-form">
        <div class="input-section">
          <div class="input-group">
            <label for="email">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                <polyline points="22,6 12,13 2,6"/>
              </svg>
              이메일
            </label>
            <input
                type="email"
                id="email"
                v-model="email"
                :class="{ 'error': errors.email }"
                placeholder="example@email.com"
                required
            />
            <span v-if="errors.email" class="error-message">{{ errors.email }}</span>
          </div>

          <div class="input-group">
            <label for="password">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                <circle cx="12" cy="16" r="1"/>
                <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
              </svg>
              비밀번호
            </label>
            <div class="password-input-wrapper">
              <input
                  :type="showPassword ? 'text' : 'password'"
                  id="password"
                  v-model="password"
                  :class="{ 'error': errors.password }"
                  placeholder="비밀번호를 입력하세요"
                  required
              />
              <button
                  type="button"
                  class="password-toggle"
                  @click="showPassword = !showPassword"
              >
                <svg v-if="showPassword" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/>
                  <line x1="1" y1="1" x2="23" y2="23"/>
                </svg>
                <svg v-else xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
                  <circle cx="12" cy="12" r="3"/>
                </svg>
              </button>
            </div>
            <span v-if="errors.password" class="error-message">{{ errors.password }}</span>
          </div>
        </div>

        <!-- 로그인 버튼 -->
        <button
            type="submit"
            class="submit-btn"
            :disabled="isLoading || !isFormValid"
        >
          <svg v-if="!isLoading" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/>
            <polyline points="10 17 15 12 10 7"/>
            <line x1="15" y1="12" x2="3" y2="12"/>
          </svg>
          <div v-else class="button-spinner"></div>
          {{ isLoading ? '로그인 중...' : '로그인' }}
        </button>

        <!-- 회원가입 링크 -->
        <div class="signup-section">
          <p>아직 계정이 없으신가요?</p>
          <a href="/register" class="signup-link">회원가입하기</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginForm',
  data() {
    return {
      email: '',
      password: '',
      rememberMe: false,
      showPassword: false,
      isLoading: false,
      errors: {}
    };
  },
  computed: {
    isFormValid() {
      return this.email && this.password && Object.keys(this.errors).length === 0;
    }
  },
  watch: {
    email() {
      this.validateEmail();
    },
    password() {
      this.validatePassword();
    }
  },
  methods: {
    validateEmail() {
      if (!this.email) {
        if (this.errors.email) {
          const newErrors = { ...this.errors };
          delete newErrors.email;
          this.errors = newErrors;
        }
        return;
      }

      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      const newErrors = { ...this.errors };

      if (!emailRegex.test(this.email)) {
        newErrors.email = '올바른 이메일 형식이 아닙니다';
      } else {
        delete newErrors.email;
      }

      this.errors = newErrors;
    },

    validatePassword() {
      if (!this.password) {
        if (this.errors.password) {
          const newErrors = { ...this.errors };
          delete newErrors.password;
          this.errors = newErrors;
        }
        return;
      }

      const newErrors = { ...this.errors };

      if (this.password.length < 6) {
        newErrors.password = '비밀번호는 6자 이상이어야 합니다';
      } else {
        delete newErrors.password;
      }

      this.errors = newErrors;
    },

    async login() {
      // 폼 검증
      this.validateEmail();
      this.validatePassword();

      if (Object.keys(this.errors).length > 0) {
        return;
      }

      this.isLoading = true;

      try {
        const response = await fetch("http://localhost:8081/join/login", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          credentials: "include",
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        });

        if (!response.ok) {
          const error = await response.text();
          throw new Error(error);
        }

        const username = await response.text();

        // 로그인 상태 유지 옵션 처리
        if (this.rememberMe) {
          localStorage.setItem("username", username);
          localStorage.setItem("rememberMe", "true");
        } else {
          sessionStorage.setItem("username", username);
        }

        // 성공 메시지와 리다이렉트
        this.showSuccessMessage(username);

        setTimeout(() => {
          this.$router.push('/');
        }, 1500);

      } catch (error) {
        console.error('로그인 실패:', error);
        this.showErrorMessage(error.message);
      } finally {
        this.isLoading = false;
      }
    },

    showSuccessMessage(username) {
      // 간단한 성공 알림 (실제로는 toast 라이브러리 사용 권장)
      alert(`환영합니다, ${username}님!`);
    },

    showErrorMessage(message) {
      // 간단한 에러 알림 (실제로는 toast 라이브러리 사용 권장)
      alert("로그인 실패: " + message);
    },

    socialLogin(provider) {
      console.log(`${provider} 로그인 시도`);
      // 소셜 로그인 구현
    }
  }
};
</script>

<style scoped>
/* 전체 페이지 레이아웃 */
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  position: relative;
  overflow: hidden;
}

/* 배경 장식 */
.background-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
}

.decoration-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.circle-1 {
  width: 200px;
  height: 200px;
  top: 10%;
  left: 10%;
  animation-delay: 0s;
}

.circle-2 {
  width: 150px;
  height: 150px;
  top: 60%;
  right: 15%;
  animation-delay: 2s;
}

.circle-3 {
  width: 100px;
  height: 100px;
  bottom: 20%;
  left: 20%;
  animation-delay: 4s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
}

/* 메인 컨테이너 */
.login-container {
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  width: 100%;
  max-width: 480px;
  position: relative;
  animation: slideUp 0.6s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 헤더 섹션 */
.login-header {
  background: linear-gradient(135deg, #f8fafc 0%, #F9F6FE 100%);
  color: black;
  padding: 40px;
  text-align: center;
}

.logo-section {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  margin-bottom: 30px;
}

.logo-icon {
  width: 50px;
  height: 50px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-text h1 {
  font-size: 24px;
  font-weight: 700;
  margin: 0;
}

.logo-subtitle {
  font-size: 12px;
  opacity: 0.8;
  margin: 0;
}

.welcome-section h2 {
  font-size: 28px;
  font-weight: 700;
  margin: 0 0 10px 0;
}

.welcome-text {
  font-size: 16px;
  opacity: 0.9;
  margin: 0;
}

/* 로딩 오버레이 */
.loading-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.95);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f4f6;
  border-top: 4px solid #3b82f6;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 폼 스타일 */
.login-form {
  padding: 40px;
}

.input-section {
  margin-bottom: 30px;
}

.input-group {
  margin-bottom: 25px;
}

.input-group label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 600;
  color: #374151;
  margin-bottom: 8px;
  font-size: 14px;
}

.input-group input {
  width: 100%;
  padding: 14px 16px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  font-size: 16px;
  transition: all 0.3s ease;
  background-color: #fafafa;
  box-sizing: border-box;
}

.input-group input:focus {
  outline: none;
  border-color: #3b82f6;
  background-color: white;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.input-group input.error {
  border-color: #ef4444;
  background-color: #fef2f2;
}

.input-group input.error:focus {
  box-shadow: 0 0 0 3px rgba(239, 68, 68, 0.1);
}

.error-message {
  display: block;
  color: #ef4444;
  font-size: 12px;
  margin-top: 5px;
  font-weight: 500;
}

/* 비밀번호 입력 */
.password-input-wrapper {
  position: relative;
}

.password-toggle {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #6b7280;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: color 0.2s ease;
}

.password-toggle:hover {
  color: #3b82f6;
}

/* 폼 옵션 */
.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  font-size: 14px;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  color: #6b7280;
}

.remember-me input[type="checkbox"] {
  display: none;
}

.checkmark {
  width: 18px;
  height: 18px;
  border: 2px solid #d1d5db;
  border-radius: 4px;
  background-color: white;
  position: relative;
  transition: all 0.2s ease;
}

.remember-me input[type="checkbox"]:checked + .checkmark {
  background-color: #3b82f6;
  border-color: #3b82f6;
}

.remember-me input[type="checkbox"]:checked + .checkmark::after {
  content: '';
  position: absolute;
  left: 5px;
  top: 1px;
  width: 5px;
  height: 9px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.forgot-password {
  color: #3b82f6;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s ease;
}

.forgot-password:hover {
  color: #2563eb;
  text-decoration: underline;
}

/* 제출 버튼 */
.submit-btn {
  width: 100%;
  background: linear-gradient(135deg, #3b82f6, #7c3aed);
  color: white;
  border: none;
  border-radius: 12px;
  padding: 16px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.submit-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(59, 130, 246, 0.3);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.button-spinner {
  width: 20px;
  height: 20px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top: 2px solid white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* 회원가입 섹션 */
.signup-section {
  text-align: center;
  margin-bottom: 30px;
  color: #6b7280;
  font-size: 14px;
}

.signup-section p {
  margin: 0 0 10px 0;
}

.signup-link {
  color: #3b82f6;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s ease;
}

.signup-link:hover {
  color: #2563eb;
  text-decoration: underline;
}

/* 반응형 디자인 */
@media (max-width: 640px) {
  .login-page {
    padding: 10px;
  }

  .login-container {
    max-width: 100%;
  }

  .login-header {
    padding: 30px 20px;
  }

  .login-form {
    padding: 30px 20px;
  }

  .form-options {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }
}

/* 포커스 상태 */
.submit-btn:focus,
.social-btn:focus {
  outline: none;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}
</style>