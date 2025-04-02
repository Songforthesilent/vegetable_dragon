<template>
  <div class="register-container">
    <h2>회원가입</h2>
    <p>회원가입 후 다양한 서비스를 경험해보세요.</p>
    <hr class="divider" />
    <form @submit.prevent="submitForm">
      <div class="input-group">
        <label for="username">아이디</label>
        <input type="text" id="username" v-model="form.username" required />
      </div>

      <div class="input-group">
        <label for="email">이메일</label>
        <input type="email" id="email" v-model="form.email" required />
      </div>

      <div class="input-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="form.password" required />
      </div>

      <div class="input-group">
        <label for="realName">이름</label>
        <input type="text" id="realName" v-model="form.realName" required />
      </div>

      <div class="input-group">
        <label for="birthday">생년월일</label>
        <input type="date" id="birthday" v-model="form.birthday" required />
      </div>

      <div class="input-group">
        <label for="anonymousName">사용자 닉네임</label>
        <input type="text" id="anonymousName" v-model="form.anonymousName" required />
      </div>

      <button type="submit">회원가입</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

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
      }
    };
  },
  methods: {
    async submitForm() {
      try {
        const API_URL = 'http://localhost:8081/api/register'; // API 주소 수정

        // API 요청 보내기
        const response = await axios.post(API_URL, this.form, {
          headers: { 'Content-Type': 'application/json' } // 헤더 설정
        });

        alert('회원가입이 완료되었습니다!');
        console.log('회원가입 성공: ', response.data);

        // 회원가입 성공 시 홈화면으로
        this.$router.push('/');
      } catch (error) {
        console.error('회원가입 실패: ', error.response?.data || error.message);
        alert(error.response?.data?.message || '회원가입에 실패했습니다.');
      }
      // 예시: 회원가입 API 호출 (추후 연동 필요)
      // this.$axios.post('/api/register', this.form).then(response => {
      //   console.log('회원가입 성공:', response);
      // }).catch(error => {
      //   console.error('회원가입 실패:', error);
      // });
    }
  }
};
</script>

<style scoped>
.register-container {
  width: 1050px;
  margin: 100px auto;
  padding: 50px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background: white;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
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
  margin-bottom: 20px;
}

.input-group {
  text-align: left;
  margin-top: 30px;
  margin-bottom: 15px;
}

.input-group label {
  font-weight: bold;
}

input {
  width: 100%;
  padding: 8px;
  margin-top: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  width: 120px;
  padding: 10px;
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #2A3C8C;
}
</style>
