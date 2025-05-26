<template>
  <div class="register-page">
    <div class="register-header">
      <!-- 배경 장식 -->
      <div class="background-decoration">
        <div class="decoration-circle circle-1"></div>
        <div class="decoration-circle circle-2"></div>
        <div class="decoration-circle circle-3"></div>
        <div class="decoration-circle circle-4"></div>
      </div>

      <!-- 메인 컨테이너 -->
      <div class="register-container">
        <div class="register-card">
          <!-- 로딩 오버레이 -->
          <div v-if="isLoading" class="loading-overlay">
            <div class="loading-spinner"></div>
            <p>계정을 생성하는 중입니다...</p>
          </div>
          <!-- 카드 헤더 -->
          <div class="card-header">
            <div class="icon-wrapper">
              <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                <circle cx="9" cy="7" r="4"/>
                <path d="M22 21v-2a4 4 0 0 0-3-3.87"/>
                <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
              </svg>
            </div>
            <h2>회원가입</h2>
            <p>모든 필드를 정확히 입력해주세요</p>
          </div>

          <form @submit.prevent="submitForm" class="register-form">
            <!-- 기본 정보 섹션 -->
            <div class="form-section">
              <h3 class="section-title">
                <svg xmlns="http://www.w3.org/2000/svg" width="20"
                     height="20" viewBox="0 0 24 24" fill="none"
                     stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                  <circle cx="12" cy="7" r="4"/>
                </svg>기본 정보</h3>
              <div class="form-row">
                <div class="input-group">
                  <label for="username">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                      <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                      <circle cx="12" cy="7" r="4"/>
                    </svg>아이디</label>
                  <input type="text" id="username" v-model="form.username" placeholder="아이디 4-20자" required />
                </div>

                <div class="input-group">
                  <label for="email">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                      <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/>
                      <polyline points="22,6 12,13 2,6"/>
                    </svg>이메일</label>
                  <input type="email" id="email" v-model="form.email" placeholder="example@email.com" required />
                </div>
              </div>

              <div class="input-group">
                <label for="password">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                    <circle cx="12" cy="16" r="1"/>
                    <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                  </svg>비밀번호</label>
                <div class="password-input-wrapper">
                  <input type="password" id="password" v-model="form.password"
                         :class="{ 'error': errors.password }"
                         placeholder="영문, 숫자, 특수문자 조합 8자 이상" required />
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
              </div>

              <div class="form-section">
                <h3 class="section-title">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                    <polyline points="14 2 14 8 20 8"/>
                    <line x1="16" y1="13" x2="8" y2="13"/>
                    <line x1="16" y1="17" x2="8" y2="17"/>
                    <polyline points="10 9 9 9 8 9"/>
                  </svg>
                  개인 정보
                </h3>
                <div class="form-row">
                  <div class="input-group">
                    <label for="realName">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                        <circle cx="12" cy="7" r="4"/>
                      </svg>이름</label>
                    <input type="text" id="realName" v-model="form.realName"
                           placeholder="실제 이름을 입력하세요" required />
                  </div>

                  <div class="input-group">
                    <label for="birthday">
                      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                        <line x1="16" y1="2" x2="16" y2="6"/>
                        <line x1="8" y1="2" x2="8" y2="6"/>
                        <line x1="3" y1="10" x2="21" y2="10"/>
                      </svg>생년월일</label>
                    <input type="date" id="birthday" v-model="form.birthday" placeholder="영문, 숫자, 특수문자 조합 8자 이상" required />
                  </div>
                </div>
                <div class="input-group">
                  <label for="anonymousName">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                      <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
                    </svg>사용자 닉네임</label>
                  <input type="text" id="anonymousName" v-model="form.anonymousName"
                         placeholder="닉네임을 적어주세요" required />
                </div>
              </div>
            </div>
            <div class="form-actions">
              <button type="submit"
                      class="submit-button">
                <svg v-if="!isLoading" xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2"/>
                  <circle cx="9" cy="7" r="4"/>
                  <path d="M22 21v-2a4 4 0 0 0-3-3.87"/>
                  <path d="M16 3.13a4 4 0 0 1 0 7.75"/>
                </svg>
                <div v-else class="button-spinner"></div>
                {{ isLoading ? '계정 생성 중...' : '회원가입 완료' }}
              </button>
              <p class="login-link">
                이미 계정이 있으신가요?
                <a href="/login" class="link">로그인하기</a>
              </p>
            </div>
          </form>
        </div>
      </div>

      <!-- 성공 모달 -->
      <div v-if="showSuccessModal" class="modal-overlay">
        <div class="success-modal">
          <div class="success-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
              <polyline points="22 4 12 14.01 9 11.01"/>
            </svg>
          </div>
          <h3>회원가입 완료!</h3>
          <p>{{ form.anonymousName }}님, 환영합니다!<br>로그인 페이지로 이동합니다.</p>
          <button @click="goToLogin" class="modal-button">로그인하러 가기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {registerUser} from '@/api/user';

export default {
  name: 'UserRegister',
  data() {
    return {
      form: {
        username: '',
        email: '',
        password: '',
        realName: '',
        birthday: '',
        anonymousName: '',
      },
      errors: {},
      isLoading: false,
      showPassword: false,
      showSuccessModal: false
    };
  },
  computed: {
    passwordStrength() {
      const password = this.form.password;
      if (!password) return { width: '0%', class: '', text: '' };

      let score = 0;
      if (password.length >= 8) score++;
      if (/[a-z]/.test(password)) score++;
      if (/[A-Z]/.test(password)) score++;
      if (/[0-9]/.test(password)) score++;
      if (/[^A-Za-z0-9]/.test(password)) score++;

      if (score <= 2) return { width: '33%', class: 'weak', text: '약함' };
      if (score <= 3) return { width: '66%', class: 'medium', text: '보통' };
      return { width: '100%', class: 'strong', text: '강함' };
    },
    isFormValid() {
      return this.form.username &&
          this.form.email &&
          this.form.password &&
          this.form.realName &&
          this.form.birthday &&
          this.form.anonymousName &&
          this.agreements.terms &&
          this.agreements.privacy &&
          Object.keys(this.errors).length === 0;
    }
  },
  watch: {
    'form.username': {
      handler() {
        this.validateField('username');
      },
      immediate: false
    },
    'form.email': {
      handler() {
        this.validateField('email');
      },
      immediate: false
    },
    'form.password': {
      handler() {
        this.validateField('password');
      },
      immediate: false
    },
    'form.realName': {
      handler() {
        this.validateField('realName');
      },
      immediate: false
    },
    'form.anonymousName': {
      handler() {
        this.validateField('anonymousName');
      },
      immediate: false
    }
  },
  methods: {
    validateField(fieldName) {
      // 필드가 비어있으면 에러를 제거하고 검증하지 않음
      if (!this.form[fieldName]) {
        if (this.errors[fieldName]) {
          const newErrors = { ...this.errors };
          delete newErrors[fieldName];
          this.errors = newErrors;
        }
        return;
      }

      const newErrors = { ...this.errors };
      switch (fieldName) {
        case 'username':
          if (this.form.username.length < 4 || this.form.username.length > 20) {
            newErrors.username = '아이디는 4-20자 사이여야 합니다';
          } else if (!/^[a-zA-Z0-9]+$/.test(this.form.username)) {
            newErrors.username = '영문과 숫자만 사용 가능합니다';
          } else {
            delete newErrors.username;
          }
          break;

        case 'password':
          if (this.form.password.length < 8) {
            newErrors.password = '비밀번호는 8자 이상이어야 합니다';
          } else if (!/(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])/.test(this.form.password)) {
            newErrors.password = '영문, 숫자, 특수문자를 모두 포함해야 합니다';
          } else {
            delete newErrors.password;
          }
          break;

        case 'realName':
          if (this.form.realName.length < 2) {
            newErrors.realName = '이름은 2자 이상이어야 합니다';
          } else {
            delete newErrors.realName;
          }
          break;

        case 'anonymousName':
          if (this.form.anonymousName.length < 2 || this.form.anonymousName.length > 20) {
            newErrors.anonymousName = '닉네임은 2-20자 사이여야 합니다';
          } else {
            delete newErrors.anonymousName;
          }
          break;
      }

      this.errors = newErrors;
    },
    async submitForm() {
      try {
        const response = await registerUser(this.form);

        alert('회원가입이 완료되었습니다!');
        console.log('회원가입 성공: ', response.data);
        this.$router.push('/login');

      } catch (error) {
        console.error('회원가입 실패: ', error.response?.data || error.message);
        alert(error.response?.data?.message || '회원가입에 실패했습니다.');
      }
    },
    goToLogin() {
      this.showSuccessModal = false;
      // 실제 라우터 사용 시: this.$router.push('/login');
      console.log('로그인 페이지로 이동');
    }
  }
};
</script>

<style scoped>
/* 전체 페이지 레이아웃 */
.register-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 0;
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
  top: 15%;
  left: 20%;
  animation-delay: 0s;
}

.circle-2 {
  width: 150px;
  height: 150px;
  top: 55%;
  right: 10%;
  animation-delay: 2s;
}

.circle-3 {
  width: 100px;
  height: 100px;
  bottom: 40%;
  left: 10%;
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
/* 헤더 섹션 */
.register-header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  padding: 60px 0 40px;
  text-align: center;
  color: white;
}

/* 메인 컨테이너 */
.register-container {
  max-width: 800px;
  margin: -20px auto 0;
  padding: 0 20px 60px;
  position: relative;
  z-index: 1;
}

.register-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  position: relative;
}

/* 카드 헤더 */
.card-header {
  background: linear-gradient(135deg, #f8fafc 0%, #F9F6FE 100%);
  color: black;
  padding: 40px;
  text-align: center;
  position: relative;
}

.icon-wrapper {
  width: 80px;
  height: 80px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: #3662E3;
}

.card-header h2 {
  font-size: 25px;
  font-weight: 700;
  margin: 0 0 10px 0;
}

.card-header p {
  font-size: 14px;
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
  border-top: 4px solid #3662E3;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 폼 스타일 */
.register-form {
  padding: 40px;
}

.form-section {
  margin-bottom: 40px;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  margin: 0 0 25px 0;
  margin-top: 10px;
  padding-bottom: 10px;
  border-bottom: 2px solid #f3f4f6;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
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
  padding: 12px 16px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  font-size: 14px;
  transition: all 0.3s ease;
  background-color: #fafafa;
  box-sizing: border-box;
}

.input-group input:focus {
  outline: none;
  border-color: #3662E3;
  background-color: white;
  box-shadow: 0 0 0 3px rgba(58, 76, 164, 0.1);
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

.input-help {
  display: block;
  color: #6b7280;
  font-size: 12px;
  margin-top: 5px;
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
  color: #3662E3;
}

/* 제출 버튼 */
.form-actions {
  text-align: center;
  margin-top: 40px;
}

.submit-button {
  background: linear-gradient(135deg, #3b82f6, #7c3aed);
  color: white;
  border: none;
  border-radius: 12px;
  padding: 16px 40px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  display: inline-flex;
  align-items: center;
  gap: 10px;
  min-width: 200px;
  justify-content: center;
}

.submit-button:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(58, 76, 164, 0.3);
}

.submit-button:disabled {
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

.login-link {
  margin-top: 20px;
  color: #6b7280;
  font-size: 14px;
}

.link {
  color: #3A4CA4;
  text-decoration: none;
  font-weight: 500;
}

.link:hover {
  text-decoration: underline;
}

/* 모달 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(5px);
}

.success-modal {
  background: white;
  border-radius: 20px;
  padding: 40px;
  text-align: center;
  max-width: 400px;
  width: 90%;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  animation: modal-appear 0.3s ease;
}

@keyframes modal-appear {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.success-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #10b981, #34d399);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: white;
}

.success-modal h3 {
  font-size: 24px;
  font-weight: 700;
  color: #1f2937;
  margin: 0 0 15px 0;
}

.success-modal p {
  color: #6b7280;
  line-height: 1.6;
  margin: 0 0 30px 0;
}

.modal-button {
  background: linear-gradient(135deg, #3b82f6, #7c3aed);
  color: white;
  border: none;
  border-radius: 10px;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.modal-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(58, 76, 164, 0.3);
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .register-container {
    padding: 0 15px 40px;
  }

  .register-form {
    padding: 30px 20px;
  }

  .form-row {
    grid-template-columns: 1fr;
    gap: 0;
  }

  .card-header {
    padding: 30px 20px;
  }

  .card-header h2 {
    font-size: 24px;
  }

  .section-title {
    font-size: 18px;
  }
}

@media (max-width: 480px) {
  .register-form {
    padding: 20px 15px;
  }

  .submit-button {
    width: 100%;
    padding: 14px 20px;
  }
}
</style>