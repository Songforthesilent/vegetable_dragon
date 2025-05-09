<template>
  <aside class="login-panel">
    <div class="login-box">
      <div class="profile-avatar">
        <div v-if="isLoggedIn" class="avatar-circle with-user">
          {{ userInitial }}
        </div>
        <div v-else class="avatar-circle">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
            <circle cx="12" cy="7" r="4"></circle>
          </svg>
        </div>
      </div>

      <template v-if="!isLoggedIn">
        <h3 class="form-title">로그인</h3>
        <div class="input-container">
          <div class="input-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
              <circle cx="12" cy="7" r="4"></circle>
            </svg>
          </div>
          <input v-model="loginForm.email" type="text" placeholder="username" class="input-field username" />
        </div>
        <div class="input-container">
          <div class="input-icon">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
          </div>
          <input v-model="loginForm.password" type="password" placeholder="password" class="input-field password" />
        </div>
        <button class="login-button" @click="login">로그인</button>
        <div class="links">
          <router-link to="/register">회원가입하기</router-link>
          <a href="#">비밀번호찾기</a>
        </div>
      </template>

      <template v-else>
        <h3 class="welcome-text">{{ userName }} 님</h3>
        <button class="action-button" @click="fnWrite">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
          </svg>
          <span>글 등록하기</span>
        </button>
        <button class="logout-button" @click="logout">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
            <polyline points="16 17 21 12 16 7"></polyline>
            <line x1="21" y1="12" x2="9" y2="12"></line>
          </svg>
          <span>로그아웃</span>
        </button>
      </template>
    </div>
  </aside>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        email: '',
        password: ''
      },
      userName: ''
    };
  },
  computed: {
    isLoggedIn() {
      return this.userName !== '';
    }
  },
  methods: {
    login() { // 8080 -> 8081로 변경
      fetch("http://localhost:8081/join/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(this.loginForm)
      })
          .then(async (res) => {
            if (!res.ok) {
              const error = await res.text();
              throw new Error(error);
            }
            return res.text(); // username 이라고 가정
          })
          .then((username) => {
            this.userName = username;
            localStorage.setItem("username", username); // localstorage를 써서, UserLogin 에서도 username을 가져올 수 있도록 함.
            alert(`${username}님`);
          })
          .catch((err) => {
            alert("로그인 실패: " + err.message);
          });
    },
    logout() { // 로그아웃 연결
      fetch("http://localhost:8081/join/logout", {
        method: "POST",
        credentials: "include"
      })
          .then(() => {
            localStorage.removeItem("username");
            this.userName = '';

            window.location.reload();  // 페이지 새로 고침
          })
    },
    syncUserName() {
      this.userName = localStorage.getItem("username") || '';
    },
    fnWrite() {
      this.$router.push('/board/write');
    },
  },
  mounted() {
    this.syncUserName();
    window.addEventListener("storage", this.syncUserName);

  },
  beforeMount() {
    window.removeEventListener("storage", this.syncUserName);
  }
};
</script>

<style scoped>
/*로그인*/
.login-panel {
  width: 23%;
  height: 980px;
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  text-align: center;
  position: sticky;
}

.login-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 8px;
  background-color: #ffffff;
  padding: 20px 10px;
}

/* 프로필 아바타 */
.profile-avatar {
  margin-bottom: 20px;
}

.avatar-circle {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background-color: #f1f5f9;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3A4CA4;
  font-size: 28px;
  font-weight: 700;
  box-shadow: 0 4px 10px rgba(58, 76, 164, 0.15);
}

.avatar-circle.with-user {
  background: linear-gradient(135deg, #3A4CA4, #6366f1);
  color: white;
}

.avatar-circle svg {
  width: 32px;
  height: 32px;
  stroke: #3A4CA4;
}

/* 로그인 폼 */
.form-title {
  font-size: 18px;
  font-weight: bold;
  margin-top: 10px;
  margin-bottom: 30px;
  color: #1e293b;
}

.input-container {
  position: relative;
  width: 100%;
  margin-bottom: 15px;
}

.input-icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #939393;
}

.input-field {
  width: 100%;
  padding: 12px 12px 12px 35px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  background-color: #f8fafc;
  color: #1e293b;
  transition: all 0.3s ease;
}

.input-field::placeholder {
  color: #939393;
}

.input-field:focus {
  outline: none;
  border-color: #3A4CA4;
  box-shadow: 0 0 0 2px rgba(58, 76, 164, 0.1);
  background-color: #ffffff;
}

.login-button {
  background-color: #3A4CA4;
  color: white;
  padding: 12px;
  border: 1px solid #3A4CA4;
  border-radius: 10px;
  cursor: pointer;
  width: 95%;
  margin-top: 10px;
}

.links {
  display: flex;
  justify-content: space-between;
  width: 100%;
  font-size: 10px;
  margin-top: 10px;
  color: #939393;
}

/* 로그인 후 사용자 프로필 */
.welcome-text {
  font-size: 18px;
  font-weight: bold;
  margin: 10px 0 30px 0;
  color: #1e293b;
}

.action-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  width: 95%;
  padding: 12px;
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 15px;
}

.action-button:hover {
  background-color: #2d3a7c;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(58, 76, 164, 0.2);
}

.action-button:active {
  transform: translateY(0);
}

.action-button svg {
  transition: transform 0.3s ease;
}

.action-button:hover svg {
  transform: translateX(2px);
}

.logout-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  width: 95%;
  padding: 12px;
  background-color: transparent;
  color: #64748b;
  border: 1px solid #e2e8f0;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-button:hover {
  background-color: #fee2e2;
  color: #ef4444;
  border-color: #fecaca;
}

.logout-button svg {
  transition: transform 0.3s ease;
}

.logout-button:hover svg {
  transform: translateX(2px);
  stroke: #ef4444;
}
</style>