<template>
  <aside class="login-panel">
    <div class="login-box">
      <i class="fas fa-user-circle user-icon"></i>
      <template v-if="!isLoggedIn">
        <h3>Sign In</h3>
        <div class="input-container">
          <i class="fas fa-user icon"></i>
          <input v-model="loginForm.email" type="text" placeholder="username" class="input-field username" />
        </div>
        <div class="input-container">
          <i class="fas fa-lock icon"></i>
          <input v-model="loginForm.password" type="password" placeholder="password" class="input-field password" />
        </div>
        <button class="login-button" @click="login">로그인</button>
        <div class="links">
          <router-link to="/register">회원가입하기</router-link>
          <a href="#">비밀번호찾기</a>
        </div>
      </template>

      <template v-else>
        <h3>환영합니다, {{ userName }} 님!</h3>
        <button class="login-button" @click="logout">로그아웃</button>
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
      userName: localStorage.getItem("username") || ''
    };
  },
  computed: {
    isLoggedIn() {
      return this.userName !== '';
    }
  },
  methods: {
    login() {
      fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
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
            localStorage.setItem("username", username);
            alert(`환영합니다, ${username}님!`);
          })
          .catch((err) => {
            alert("로그인 실패: " + err.message);
          });
    },
    logout() {
      localStorage.removeItem("username");
      this.userName = '';
    }
  }
};
</script>

<style scoped>
/*로그인*/
.login-panel {
  width: 20%;
  height: 980px;
  background-color: #f4f4f4;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
  position: sticky;
  top: 20%;
}

.login-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 15px;
  background-color: #f9f9f9;
  padding: 20px 10px;
}

.user-icon {
  font-size: 80px;
  color:#3A4CA4;
  margin-top: 30px;
}

h3 {
  font-weight: bold;
  margin-bottom: 20px;
}

.input-container {
  position: relative;
  width: 100%;
}

.icon {
  position: absolute;
  left: 10px;  /* 아이콘을 input의 왼쪽에 배치 */
  top: 50%;
  transform: translateY(-85%); /* 수직 가운데 정렬 */
  color: #939393;  /* 아이콘 색상 */
}

.input-field {
  width: 100%;
  padding: 12px 12px 12px 30px;
  margin-bottom: 15px;
  border: 1px solid white;
  border-radius: 8px;
}

.username {
  color: #939393;
}

.password {
  color: #939393;
}
.login-button {
  background-color: #3A4CA4;
  color: white;
  padding: 12px;
  border: 1px solid #3A4CA4;
  border-radius: 25px;
  cursor: pointer;
  width: 100%;
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
</style>
