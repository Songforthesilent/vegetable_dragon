<template>
  <div class="login-box">
    <h3>Sign In</h3>
    <div class="input-container">
      <i class="fas fa-user icon"></i>
      <input type="text" v-model="username" placeholder="Username" class="input-field" />
    </div>

    <div class="input-container">
      <i class="fas fa-lock icon"></i>
      <input type="password" v-model="password" placeholder="Password" class="input-field" />
    </div>

    <button @click="login" class="login-button">Login</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: ''
    };
  },
  methods: {
    async login() {
      if (this.username && this.password) {
        try {
          // 서버로 로그인 요청을 보내는 코드 작성
          const response = await this.$axios.post('/api/login', {
            username: this.username,
            password: this.password
          });

          // 로그인 성공시 처리
          if (response.data.success) {
            // 로그인 성공시, 사용자 정보를 vuex나 로컬스토리지에 저장
            localStorage.setItem('user', JSON.stringify(response.data.user));
            this.$router.push('/dashboard'); // 로그인 후 대시보드로 이동
          } else {
            alert('로그인 실패');
          }
        } catch (error) {
          console.error('로그인 요청 중 오류:', error);
          alert('로그인에 실패했습니다. 다시 시도해주세요.');
        }
      } else {
        alert('아이디와 비밀번호를 입력해주세요.');
      }
    }
  }
};
</script>

<style scoped>
/* 로그인 UI 스타일링 */
.login-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  padding: 30px;
  width: 100%;
  max-width: 400px;
  background-color: #f4f4f4;
}

.input-container {
  position: relative;
  width: 100%;
  margin-bottom: 15px;
}

.icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
}

.input-field {
  width: 100%;
  padding: 12px 12px 12px 30px;
  border-radius: 5px;
  border: 1px solid #ccc;
  font-size: 16px;
}

.login-button {
  background-color: #3A4CA4;
  color: white;
  padding: 12px 20px;
  border-radius: 25px;
  border: none;
  cursor: pointer;
  font-size: 16px;
}

.login-button:hover {
  background-color: #2c3e50;
}
</style>
