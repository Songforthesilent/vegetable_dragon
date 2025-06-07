<template>
  <div class="write-container">
    <div class="write-header">
      <h1 class="write-title">새 게시글 작성</h1>
      <p class="write-subtitle">다양한 주제로 의견을 나누어보세요</p>
    </div>

    <form @submit.prevent="fnSubmit" class="write-form">
      <!-- 제목 입력 -->
      <div class="form-group">
        <label for="title" class="form-label">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z"></path>
            <line x1="4" y1="22" x2="4" y2="15"></line>
          </svg>
          제목
          <span class="required">*</span>
        </label>
        <input
            type="text"
            id="title"
            v-model="post.title"
            placeholder="제목을 입력해주세요"
            class="form-input"
            maxlength="100"
            required
        />
        <div class="input-info">
          <span class="char-count">{{ post.title.length }}/100</span>
        </div>
      </div>

      <!-- 카테고리 선택 -->
      <div class="form-group">
        <label for="category" class="form-label">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
            <path d="M22 19a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h5l2 3h9a2 2 0 0 1 2 2z"></path>
          </svg>
          카테고리
          <span class="required">*</span>
        </label>
        <div class="select-wrapper">
          <select id="category" v-model="post.category" class="form-select" required>
            <option value="">카테고리를 선택하세요</option>
            <option value="카테고리는 자동생성 됩니다 !" class="category_btn">카테고리는 자동생성 됩니다 !</option>
            <!-- <option value="전체">전체</option>
            <option value="사회">사회</option>
            <option value="정치">정치</option>
            <option value="문화">문화</option>
            <option value="연예">연예</option>
            <option value="경제">경제</option>
            <option value="국제">국제</option>-->
          </select>
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" class="select-icon">
            <polyline points="6 9 12 15 18 9"></polyline>
          </svg>
        </div>

        <div v-if="post.category" class="selected-category">
          <span class="category-badge" :class="getCategoryClass(post.category)">
            <span class="category-icon">{{ getCategoryIcon(post.category) }}</span>
            {{ post.category }}
          </span>
        </div>
      </div>

      <!-- 내용 및 익명 옵션 -->
      <div class="form-group">
        <div class="content-header">
          <label for="content" class="form-label">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
              <polyline points="14 2 14 8 20 8"></polyline>
              <line x1="16" y1="13" x2="8" y2="13"></line>
              <line x1="16" y1="17" x2="8" y2="17"></line>
            </svg>
            내용
            <span class="required">*</span>
          </label>
        </div>
        <textarea
            id="content"
            v-model="post.content"
            placeholder="내용을 입력해주세요"
            class="form-textarea"
            rows="8"
            maxlength="2000"
            required
        ></textarea>
        <div class="input-info">
          <span class="char-count">{{ post.content.length }}/2000</span>
        </div>
      </div>

      <!-- 비밀번호 및 버튼 영역 -->
      <div class="form-actions">
        <div class="password-section">
          <label for="password" class="password-label">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <circle cx="12" cy="16" r="1"></circle>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
            비밀번호
          </label>
          <input
              type="password"
              id="password"
              v-model="post.password"
              placeholder="비밀번호 입력"
              class="password-input"
              required
          />
        </div>

        <div class="buttons">
          <button type="button" class="cancel-button" @click="fnCancel">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <polyline points="15 18 9 12 15 6"></polyline>
            </svg>
            취소
          </button>
          <button type="submit" class="submit-button" :disabled="!isFormValid || isSubmitting">
            <svg v-if="isSubmitting" class="loading-icon" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <line x1="12" y1="2" x2="12" y2="6"></line>
              <line x1="12" y1="18" x2="12" y2="22"></line>
              <line x1="4.93" y1="4.93" x2="7.76" y2="7.76"></line>
              <line x1="16.24" y1="16.24" x2="19.07" y2="19.07"></line>
              <line x1="2" y1="12" x2="6" y2="12"></line>
              <line x1="18" y1="12" x2="22" y2="12"></line>
              <line x1="4.93" y1="19.07" x2="7.76" y2="16.24"></line>
              <line x1="16.24" y1="7.76" x2="19.07" y2="4.93"></line>
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
            </svg>
            {{ isSubmitting ? '등록 중...' : '글 등록하기' }}
          </button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

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
      },
      isSubmitting: false
    };
  },
  computed: {
    isFormValid() {
      return this.post.title.trim() &&
          this.post.content.trim() &&
          this.post.category &&
          this.post.password.trim();
    }
  },
  methods: {
    async fnSubmit() {
      // 제목과 내용이 비어있는지 체크
      if (!this.post.title.trim() || !this.post.content.trim()) {
        alert("제목과 내용을 입력해주세요!");
        return;
      }

      // 카테고리 선택 확인
      if (!this.post.category) {
        alert("카테고리를 선택해주세요!");
        return;
      }

      // 익명 작성 시 작성자 필드를 '익명'으로 설정
      if (this.post.isAnonymous) {
        this.post.author = '익명';
      }

      // 비밀번호가 입력되지 않은 경우 경고
      if (!this.post.password.trim()) {
        alert("비밀번호를 입력해주세요!");
        return;
      }

      this.isSubmitting = true;

      try {
        // 게시글 등록 요청을 보낼 때 category를 포함
        const response = await axios.post(
            "http://localhost:8081/posts",  // 백엔드 URL
            {
              title: this.post.title,
              content: this.post.content,
              category: this.post.category,  // 카테고리 추가
              author: this.post.author,      // 작성자 (익명/로그인 사용자)
              password: this.post.password  // 비밀번호
            },
            {
              withCredentials: true // 세션 쿠키
            }
        );

        // 백엔드에서 게시글 저장 요청을 보내고, 응답 받은 데이터로 화면 처리
        const createdPost = response.data;
        alert('게시글이 등록되었습니다!');

        // 게시글 작성 완료 후 해당 게시글 페이지로 이동
        this.$router.push(`/board/view/${createdPost.id}`);
      } catch (error) {
        if (error.response?.status === 401) {
          alert("로그인이 필요합니다.");
        } else {
          console.error("게시글 작성 실패 : ", error);
          alert("게시글 등록에 실패했습니다.");
        }
      } finally {
        this.isSubmitting = false;
      }
    },

    fnCancel() {
      if (this.post.title || this.post.content) {
        if (confirm('작성 중인 내용이 있습니다. 정말 나가시겠습니까?')) {
          this.$router.push('/board/list');
        }
      } else {
        this.$router.push('/board/list');
      }
    },

    getCategoryClass(category) {
      const classMap = {
        '전체': 'category-all',
        '사회': 'category-society',
        '정치': 'category-politics',
        '문화': 'category-culture',
        '연예': 'category-entertainment',
        '경제': 'category-economy',
        '국제': 'category-international'
      };
      return classMap[category] || 'category-default';
    },

    getCategoryIcon(category) {
      const iconMap = {
        '전체': '📋',
        '사회': '👥',
        '정치': '🏛️',
        '문화': '🎨',
        '연예': '🎭',
        '경제': '💰',
        '국제': '🌍'

      };
      return iconMap[category] || '📝';
    }
  }
};
</script>

<style scoped>
/* 전체 컨테이너 */
.write-container {
  max-width: 900px;
  margin: 40px auto;
  padding: 0 20px;
}

/* 헤더 */
.write-header {
  text-align: center;
  margin-bottom: 40px;
}

.write-title {
  font-size: 28px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 8px 0;
}

.write-subtitle {
  font-size: 15px;
  color: #64748b;
  margin: 0;
}

/* 폼 스타일 */
.write-form {
  background-color: #ffffff;
  border-radius: 16px;
  padding: 32px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  border: 1px solid #f1f5f9;
}

.form-group {
  margin-bottom: 24px;
}

.form-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 600;
  color: #374151;
  margin-bottom: 8px;
}

.required {
  color: #ef4444;
}

/* 입력 필드 */
.form-input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  transition: border-color 0.2s ease;
}

.form-input:focus {
  outline: none;
  border-color: #3A4CA4;
  box-shadow: 0 0 0 3px rgba(58, 76, 164, 0.1);
}

/* 셀렉트 박스 */
.select-wrapper {
  position: relative;
}

.form-select {
  width: 100%;
  padding: 12px 40px 12px 16px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  background-color: #ffffff;
  appearance: none;
  cursor: pointer;
  transition: border-color 0.2s ease;
}

.form-select:focus {
  outline: none;
  border-color: #3A4CA4;
  box-shadow: 0 0 0 3px rgba(58, 76, 164, 0.1);
}

option {
  background-color: white;
  color: black;
  font-size: 14px;
}

option.category_btn {
  background-color: #ffffff;
  padding: 12px 40px 12px 16px;
}


.category_btn:focus {
  background-color: #e2e8f0;
}

.select-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #64748b;
  pointer-events: none;
}

/* 선택된 카테고리 */
.selected-category {
  margin-top: 8px;
  text-align: left;
}

.category-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  color: white;
}

.category-icon {
  font-size: 14px;
}

/* 카테고리별 색상 */
.category-all { background-color: #64748b; }
.category-society { background-color: #3A4CA4; }
.category-politics { background-color: #ef4444; }
.category-culture { background-color: #8b5cf6; }
.category-entertainment { background-color: #f59e0b; }
.category-economy { background-color: #10b981; }
.category-international { background-color: #06b6d4; }
.category-default { background-color: #64748b; }

/* 내용 헤더 */
.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

/* 텍스트에어리어 */
.form-textarea {
  width: 100%;
  padding: 14px 16px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  font-family: inherit;
  resize: vertical;
  min-height: 260px;
  transition: border-color 0.2s ease;
}

.form-textarea:focus {
  outline: none;
  border-color: #3A4CA4;
  box-shadow: 0 0 0 3px rgba(58, 76, 164, 0.1);
}

/* 입력 정보 */
.input-info {
  display: flex;
  justify-content: flex-end;
  margin-top: 4px;
}

.char-count {
  font-size: 12px;
  color: #94a3b8;
}

/* 폼 액션 */
.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-top: 32px;
  gap: 20px;
}

.password-section {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.password-label {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #64748b;
  margin: 0;
}

.password-input {
  width: 200px;
  padding: 8px 12px;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 14px;
}

.password-input:focus {
  outline: none;
  border-color: #3662E3;
  box-shadow: 0 0 0 2px rgba(58, 76, 164, 0.1);
}

.buttons {
  display: flex;
  gap: 12px;
}

.cancel-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background-color: #f8fafc;
  color: #64748b;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.cancel-button:hover {
  background-color: #f1f5f9;
  color: #374151;
}

.submit-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.submit-button:hover:not(:disabled) {
  background-color: #2d3a7c;
  transform: translateY(-1px);
}

.submit-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.loading-icon {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .write-container {
    padding: 0 16px;
  }

  .write-form {
    padding: 24px;
  }

  .content-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .form-actions {
    flex-direction: column;
    align-items: stretch;
  }

  .password-section {
    align-self: flex-start;
  }

  .buttons {
    width: 100%;
  }

  .cancel-button,
  .submit-button {
    flex: 1;
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .write-title {
    font-size: 24px;
  }

  .write-subtitle {
    font-size: 14px;
  }

  .form-actions {
    gap: 16px;
  }

  .buttons {
    flex-direction: column;
  }

  .cancel-button,
  .submit-button {
    width: 100%;
  }
}
</style>