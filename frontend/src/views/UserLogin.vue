<template>
  <div class="login-container">
    <h2>로그인</h2>
    <form @submit.prevent="login">
      <div>
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';  // Axios 가져오기

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
        const response = await axios.post(
            'http://localhost:8081/api/login',
            {
              email: this.email,
              password: this.password
            },
            { withCredentials: true }  // 세션 쿠키 저장을 위해 필요
        );

        console.log('로그인 성공:', response);
        alert('로그인 성공!');
        this.$router.push('/');  // 로그인 후 메인 페이지로 이동
      } catch (error) {
        console.error('로그인 실패:', error);
        alert('로그인 실패! 이메일 또는 비밀번호를 확인하세요.');
      }
    }
  }
};
</script>
