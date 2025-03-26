<template>
  <div class="write-container">
    <form @submit.prevent="fnSubmit">
      <div class="form-group row">
        <label for="title">제목</label>
        <input type="text" id="title" v-model="post.title" placeholder="제목을 입력해주세요" class="title-input" required />
      </div>

      <div class="form-group row">
        <label for="category">카테고리</label>
        <select id="category" v-model="post.category" class="category-select" required>
          <option value="">카테고리를 선택하세요</option>
          <option value="전체">전체</option>
          <option value="사회">사회</option>
          <option value="정치">정치</option>
          <option value="문화">문화</option>
          <option value="연예">연예</option>
          <option value="국제">국제</option>
        </select>
      </div>

      <!--- 내용 & 익명 체크박스 -->
      <div class="form-group content-row">
        <label for="content">내용</label>
        <div class="anonymous-box">
          <input type="checkbox" id="anonymous" v-model="post.isAnonymous" />
          <label for="anonymous">익명</label>
        </div>
      </div>

      <!-- 내용 입력칸 -->
      <div class="form-group full-width">
        <textarea id="content" v-model="post.content" placeholder="내용을 입력해주세요" class="content-input" required></textarea>
      </div>

      <!-- 버튼 영역 -->
      <div class="form-actions">
        <input type="password" id="password" v-model="post.password" placeholder="비밀번호 입력" class="password-input" required />

        <div class="buttons">
          <button type="button" class="cancel-button" @click="fnCancel">취소</button>
          <button type="submit" class="submit-button">글 등록하기</button>
        </div>

      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      post: {
        title: '',
        category: '',
        author: '',
        isAnonymous: false,
        content: '',
        password: "",
      }
    };
  },
  methods: {
    fnSubmit() {
      // 익명 작성 시 작성자 필드를 '익명'으로 ㅓㄹ정
      if (this.post.isAnonymous) {
        this.post.author ='익명';
      }

      // 비밀번호가 입력되지 않은 경우 경고
      if (!this.post.password.trim()) {
        alert("비밀번호를 입력해주세요!");
        return;
      }

      // 백엔드에 게시글 저장 요청을 보내는 코드 (추후 API 연동 필요)
      console.log("게시글 데이터:", this.post);

      // 게시글 작성 완료 후 목록 페이지로 이동
      alert('게시글이 등록되었습니다!');
      this.$router.push('/board/list');
    },
    fnCancel() {
      // 취소 버튼 클릭 시 게시글 목록 페이지로 이동
      this.$router.push('/board/list');
    }
  }
};
</script>

<style scoped>
.write-container {
  max-width: 1050px;
  margin: 100px auto;
  padding: 50px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background: white;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 30px;
  display: flex;
  justify-content: space-between;
  text-align: left;
  font-size: 15px;
}

.row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.title-input,
.category-select {
  width: 90%;
  font-size: 14px;
}

label {
  display: block;
  font-weight: bold;
}

input,
textarea,
select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
}

textarea {
  height: 350px;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}

/* 비밀번호 입력란 스타일 */
.password-input {
  width: 200px;
  font-size: 14px
}

.buttons {
  display: flex;
  gap: 10px;
}


/* 익명 체크바그 */
.anonymous-box {
  display: flex;
  align-items: center;
  gap: 10px;
}

.anonymous-box input {
  width: auto;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 20px;
}

.submit-button {
  width: 170px;
  background-color: #3A4CA4;
  color: white;
  font-weight: bold;
  padding: 10px 20px;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
}

.cancel-button {
  width: 100px;
  background-color: white;
  color: black;
  font-weight: bold;
  padding: 10px 20px;
  border: 1px solid #ddd;
  border-radius: 30px;
  cursor: pointer;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
}
</style>
