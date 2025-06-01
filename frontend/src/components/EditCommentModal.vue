<template>
  <div v-if="show" class="modal-overlay" @click.self="handleCancel">
    <div class="modal-container">
      <div class="modal-header">
        <h2 class="modal-title">댓글 수정</h2>
        <button class="modal-close-btn" @click="handleCancel">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>

      <div class="modal-body">
        <!-- 비밀번호 입력 단계 -->
        <div v-if="!isLoggedIn && !passwordVerified" class="password-step">
          <div class="step-info">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
              <circle cx="12" cy="16" r="1"></circle>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
            </svg>
            <p>댓글을 수정하려면 작성 시 입력한 비밀번호를 입력해주세요.</p>
          </div>

          <div class="form-group">
            <label for="comment-password">비밀번호</label>
            <input
                id="comment-password"
                type="password"
                v-model="formData.password"
                placeholder="댓글 작성 시 입력한 비밀번호"
                class="form-input"
                @keyup.enter="verifyPassword"
            />
          </div>
        </div>

        <!-- 댓글 수정 단계 -->
        <div v-else class="edit-step">
          <div class="step-info">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
              <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
            </svg>
            <p>댓글 내용을 수정해주세요.</p>
          </div>

          <div class="form-group">
            <label for="comment-content">댓글 내용</label>
            <textarea
                id="comment-content"
                v-model="formData.content"
                placeholder="수정할 댓글 내용을 입력하세요"
                class="form-textarea"
                rows="6"
            ></textarea>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="cancel-btn" @click="handleCancel">취소</button>
        <button
            v-if="!isLoggedIn && !passwordVerified"
            class="verify-btn"
            @click="verifyPassword"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="20 6 9 17 4 12"></polyline>
          </svg>
          확인
        </button>
        <button
            v-else
            class="save-btn"
            @click="handleSave"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
            <polyline points="17 21 17 13 7 13 7 21"></polyline>
            <polyline points="7 3 7 8 15 8"></polyline>
          </svg>
          수정하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EditCommentModal',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    comment: {
      type: Object,
      default: () => ({
        id: null,
        text: '',
        user: '',
        timestamp: ''
      })
    },
    isLoggedIn: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      formData: {
        content: '',
        password: ''
      },
      passwordVerified: false
    };
  },
  watch: {
    show(newVal) {
      if (newVal) {
        this.formData.content = this.comment.text || '';
        this.formData.password = '';
        this.passwordVerified = this.isLoggedIn;
      } else {
        this.resetForm();
      }
    },
    comment: {
      handler(newVal) {
        if (this.show) {
          this.formData.content = newVal.text || '';
        }
      },
      deep: true
    }
  },
  methods: {
    verifyPassword() {
      if (!this.formData.password.trim()) {
        alert('비밀번호를 입력해주세요.');
        return;
      }

      this.$emit('verify-password', {
        commentId: this.comment.id,
        password: this.formData.password,
        onSuccess: () => {
          this.passwordVerified = true;
        },
        onError: (message) => {
          alert(message || '비밀번호가 일치하지 않습니다.');
        }
      });
    },

    handleSave() {
      if (!this.formData.content.trim()) {
        alert('댓글 내용을 입력해주세요.');
        return;
      }

      this.$emit('save', {
        commentId: this.comment.id,
        content: this.formData.content,
        password: this.isLoggedIn ? 'LOGIN_USER' : this.formData.password
      });
    },

    handleCancel() {
      this.$emit('cancel');
    },

    resetForm() {
      this.formData = {
        content: '',
        password: ''
      };
      this.passwordVerified = false;
    }
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(5px);
}

.modal-container {
  width: 90%;
  max-width: 600px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  animation: modal-appear 0.3s ease-out;
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

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #e2e8f0;
  background: #3b82f6;
}

.modal-title {
  color: white;
  font-size: 20px;
  font-weight: 700;
  margin: 0;
}

.modal-close-btn {
  background: transparent;
  border: none;
  color: white;
  cursor: pointer;
  padding: 5px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.modal-close-btn:hover {
  background-color: rgba(255, 255, 255, 0.2);
  transform: rotate(90deg);
}

.modal-body {
  padding: 24px;
  max-height: 70vh;
  overflow-y: auto;
}

.step-info {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background-color: #eff6ff;
  border-radius: 8px;
  margin-bottom: 20px;
  border-left: 4px solid #3662E3;
}

.step-info svg {
  color: #3b82f6;
  flex-shrink: 0;
}

.step-info p {
  margin: 0;
  color: #3A4CA4;
  font-size: 14px;
  line-height: 1.5;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

.form-group label {
  display: block;
  font-weight: 600;
  margin-bottom: 8px;
  color: #1e293b;
  font-size: 14px;
}

.form-input,
.form-textarea {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #e2e8f0;
  border-radius: 8px;
  font-size: 15px;
  transition: all 0.2s ease;
  color: #1e293b;
  background-color: #f8fafc;
}

.form-input:focus,
.form-textarea:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
  background-color: white;
}

.form-textarea {
  resize: vertical;
  min-height: 120px;
  line-height: 1.6;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 16px 24px;
  border-top: 1px solid #e2e8f0;
  background-color: #f8fafc;
}

.cancel-btn,
.verify-btn,
.save-btn {
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  gap: 8px;
}

.cancel-btn {
  background-color: white;
  color: #64748b;
  border: 2px solid #e2e8f0;
}

.cancel-btn:hover {
  background-color: #f1f5f9;
  color: #334155;
}

.verify-btn {
  background: linear-gradient(135deg, #f59e0b, #d97706);
  color: white;
  border: none;
  padding: 10px 24px;
}

.verify-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(245, 158, 11, 0.3);
}

.save-btn {
  background: #3662E3;
  color: white;
  border: none;
  padding: 10px 24px;
}

.save-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
}

/* 반응형 디자인 */
@media (max-width: 640px) {
  .modal-container {
    width: 95%;
    max-height: 90vh;
  }

  .modal-header {
    padding: 16px 20px;
  }

  .modal-title {
    font-size: 18px;
  }

  .modal-body {
    padding: 16px;
  }

  .step-info {
    padding: 12px;
    margin-bottom: 16px;
  }

  .step-info p {
    font-size: 13px;
  }

  .form-input,
  .form-textarea {
    padding: 10px 14px;
    font-size: 14px;
  }

  .modal-footer {
    padding: 12px 16px;
  }

  .cancel-btn,
  .verify-btn,
  .save-btn {
    padding: 8px 16px;
    font-size: 13px;
  }
}
</style>