<template>
  <div class="login-container">
    <h2>Login</h2>
    <p>마자용에 오신 것을 환영합니다!</p>
    <hr class="divider" />
    <form @submit.prevent="login" class="login-form">
      <div class="form-wrapper">
        <div class="form-left">
          <input
              type="email"
              id="email"
              v-model="email"
              placeholder="이메일을 입력하세요"
              required
          />
          <input
              type="password"
              id="password"
              v-model="password"
              placeholder="비밀번호를 입력하세요"
              required
          />
        </div>
        <div class="form-right">
          <button type="submit" class="submit-btn">로그인</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import {loginUser} from '@/api/auth';

export default {
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async login() {
      try {
        const result = await loginUser(this.email, this.password);

        // 서버 응답이 성공 형태인지 확인 (서버 응답 구조에 따라 조정)
        if (result.success !== false) {
          alert('로그인 성공!');
          this.$router.push('/');
        } else {
          alert('로그인 실패! 이메일 또는 비밀번호를 확인하세요.');
        }
      } catch (error) {
        console.error('로그인 실패:', error);
        alert('로그인 중 오류가 발생했습니다.');
      }
    }
  }
};
</script>

<style scoped>
.login-container {
  max-width: 1050px;
  margin: 100px auto;
  padding: 40px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

h2 {
  text-align: left;
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 30px;
}

p {
  text-align: left;
  font-size: 14px;
  font-weight: bold;
  color: #666666;
}

.divider {
  border: 1px solid black;
  margin-top: 20px;
  margin-bottom: 50px;
}


.login-form {
  width: 60%;
  margin: 0 auto;
  display: flex;
  justify-content: center;
}

.form-wrapper{
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 40px;
  width: 100%;
}

.form-left {
  display: flex;
  width: 70%;
  height: 100px;
  flex-direction: column;
  gap: 20px;
}

.form-right {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 30%;
  height: 100px;
}

label {
  display: block;
  margin-bottom: 10px;
  font-weight: 600;
}

.form-left input {
  padding: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-btn {
  width: 100%;
  height: 100%;
  padding: 10px 16px;
  font-size: 16px;
  font-weight: bold;
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  white-space: nowrap;
  transition: background-color 0.3s;
}

.submit-btn:hover {
  background-color: #2A3C8C;
}
</style>