<template>
  <div v-if="show" class="modal-overlay" @click.self="handleCancel">
    <div class="modal-container">
      <div class="modal-header">
        <h2 class="modal-title">게시글 수정</h2>
        <button class="modal-close-btn" @click="handleCancel">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="18" y1="6" x2="6" y2="18"></line>
            <line x1="6" y1="6" x2="18" y2="18"></line>
          </svg>
        </button>
      </div>

      <div class="modal-body">
        <div class="step-info">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
          </svg>
          <p>게시글 제목 및 내용을 수정해주세요.</p>
        </div>
        <div class="form-group">
          <label for="post-title">제목</label>
          <input
              id="post-title"
              type="text"
              v-model="formData.title"
              placeholder="제목을 입력하세요"
              class="form-input"
          />
        </div>

        <div class="form-group">
          <label for="post-content">내용</label>
          <textarea
              id="post-content"
              v-model="formData.content"
              placeholder="내용을 입력하세요"
              class="form-textarea"
              rows="10"
          ></textarea>
        </div>
      </div>

      <div class="modal-footer">
        <button class="cancel-btn" @click="handleCancel">취소</button>
        <button class="save-btn" @click="handleSave">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11l5 5v11a2 2 0 0 1-2 2z"></path>
            <polyline points="17 21 17 13 7 13 7 21"></polyline>
            <polyline points="7 3 7 8 15 8"></polyline>
          </svg>
          저장하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EditPostModal',
  props: {
    show: {
      type: Boolean,
      default: false
    },
    post: {
      type: Object,
      default: () => ({
        title: '',
        content: '',
        category: '기타',
        link: ''
      })
    }
  },
  data() {
    return {
      formData: {
        title: '',
        content: '',
        category: '기타',
        link: ''
      }
    };
  },
  watch: {
    show(newVal) {
      if (newVal) {
        this.formData = { ...this.post };
      }
    },
    post: {
      handler(newVal) {
        if (this.show) {
          this.formData = { ...newVal };
        }
      },
      deep: true
    }
  },
  methods: {
    handleSave() {
      if (!this.formData.title.trim()) {
        alert('제목을 입력해주세요.');
        return;
      }

      if (!this.formData.content.trim()) {
        alert('내용을 입력해주세요.');
        return;
      }

      this.$emit('save', { ...this.formData });
    },
    handleCancel() {
      this.$emit('cancel');
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
  max-width: 700px;
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
}

.form-group label {
  display: block;
  font-weight: 600;
  margin-bottom: 8px;
  color: #1e293b;
  font-size: 14px;
  text-align: left;
}

.form-input,
.form-select,
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
.form-select:focus,
.form-textarea:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.2);
  background-color: white;
}

.form-textarea {
  resize: vertical;
  min-height: 150px;
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
.save-btn {
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 600;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s ease;
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

.save-btn {
  background: #3662E3;
  color: white;
  border: none;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 24px;
}

.save-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
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

  .form-input,
  .form-select,
  .form-textarea {
    padding: 10px 14px;
    font-size: 14px;
  }

  .modal-footer {
    padding: 12px 16px;
  }

  .cancel-btn,
  .save-btn {
    padding: 8px 16px;
    font-size: 13px;
  }
}
</style>