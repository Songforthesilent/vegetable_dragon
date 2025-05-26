<template>
  <div class="comments-section">
    <h3 class="comments-title">댓글 {{ comments.length }}개</h3>

    <div class="comment-input-container">
      <textarea
          v-model="newComment"
          placeholder="건전한 토론 문화를 위해 예의를 지켜주세요..."
          class="comment-textarea"
      ></textarea>

      <div class="comment-form-bottom">
        <button @click="addComment" class="submit-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="22" y1="2" x2="11" y2="13"/>
            <polygon points="22,2 15,22 11,13 2,9"/>
          </svg>
          댓글 등록
        </button>
      </div>
    </div>

    <div class="comments-list">
      <div v-for="(comment, index) in comments" :key="comment.id || index" class="comment-item">
        <div class="comment-header">
          <div class="comment-avatar">
            {{ getInitial(comment.user) }}
          </div>
          <div class="comment-meta">
            <span class="comment-author">{{ comment.user }}</span>
            <span class="comment-time">{{ formatTime(comment.timestamp) }}</span>
          </div>
        </div>

        <div class="comment-content">
          {{ comment.text }}
        </div>

        <div class="comment-actions">
          <button class="action-btn edit-btn" @click="$emit('edit-comment', index)">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/>
              <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/>
            </svg>
            수정
          </button>
          <button class="action-btn delete-btn" @click="$emit('delete-comment', index)">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
            </svg>
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    comments: {
      type: Array,
      default: () => []
    },
    isLoggedIn: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      newComment: '',
      commentPassword: ''
    };
  },
  methods: {
    addComment() {
      if (!this.newComment.trim()) {
        alert("댓글을 입력하세요!");
        return;
      }

      if (!this.isLoggedIn && !this.commentPassword.trim()) {
        alert("비밀번호를 입력하세요!");
        return;
      }

      this.$emit('add-comment', {
        text: this.newComment,
        password: this.commentPassword
      });

      this.newComment = '';
      this.commentPassword = '';
    },

    getInitial(username) {
      return username ? username.charAt(0).toUpperCase() : 'U';
    },

    formatTime(timestamp) {
      // 간단한 시간 포맷팅 (실제로는 더 정교한 로직 필요)
      return timestamp || '2025.5.2 오전 9:13';
    }
  }
};
</script>

<style scoped>
.comments-section {
  margin-top: 40px;
  background: white;
  border-radius: 12px;
  padding: 5px;
}

.comments-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 24px 0;
  padding-bottom: 16px;
  text-align: left;
  border-bottom: 1px solid #f1f5f9;
}

/* 댓글 입력 영역 */
.comment-input-container {
  margin-bottom: 32px;
}

.comment-textarea {
  width: 100%;
  min-height: 120px;
  padding: 16px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  line-height: 1.5;
  resize: vertical;
  transition: all 0.2s ease;
  font-family: inherit;
  background: #fafbfc;
}

.comment-textarea:focus {
  outline: none;
  border-color: #3662E3;
  background: white;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.comment-textarea::placeholder {
  color: #94a3b8;
}

.comment-form-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
  gap: 12px;
}

.submit-button {
  display: flex;
  align-items: end;
  gap: 6px;
  padding: 10px 20px;
  background: #3662E3;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.submit-button:hover {
  background: #2563eb;
  transform: translateY(-1px);
}

.submit-button:active {
  transform: translateY(0);
}

/* 댓글 목록 */
.comments-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.comment-item {
  padding: 20px 0;
  border-bottom: 1px solid #f1f5f9;
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.comment-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: #dbeafe;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
  font-size: 16px;
  flex-shrink: 0;
}

.comment-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  flex: 1;
}

.comment-author {
  font-weight: 600;
  color: #1e293b;
  font-size: 14px;
}


.comment-time {
  color: #64748b;
  font-size: 12px;
  margin-left: auto;
}

.comment-content {
  color: #374151;
  line-height: 1.6;
  font-size: 14px;
  text-align: left;
  margin-bottom: 16px;
  padding-left: 52px;
}

.comment-actions {
  display: flex;
  align-items: center;
  gap: 16px;
  padding-left: 45px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 4px;
  padding: 6px 8px;
  background: none;
  border: none;
  color: #64748b;
  font-size: 12px;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.action-btn:hover {
  background: #f1f5f9;
  color: #374151;
}

.like-btn:hover {
  color: #ef4444;
}

.reply-btn:hover {
  color: #3662E3;
}

.edit-btn:hover {
  color: #f59e0b;
}

.delete-btn:hover {
  color: #ef4444;
}

.action-btn svg {
  flex-shrink: 0;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .comments-section {
    padding: 16px;
    margin-top: 24px;
  }

  .comment-form-bottom {
    flex-direction: column;
    align-items: stretch;
    gap: 12px;
  }

  .nickname-input {
    flex: none;
  }

  .submit-button {
    justify-content: center;
  }

  .comment-header {
    gap: 8px;
  }

  .comment-avatar {
    width: 32px;
    height: 32px;
    font-size: 14px;
  }

  .comment-content {
    padding-left: 40px;
    font-size: 13px;
  }

  .comment-actions {
    padding-left: 40px;
    gap: 12px;
    flex-wrap: wrap;
  }

  .action-btn {
    font-size: 11px;
    padding: 4px 6px;
  }
}

@media (max-width: 480px) {
  .comments-section {
    padding: 12px;
  }

  .comment-textarea {
    min-height: 100px;
    padding: 12px;
  }

  .comment-content {
    padding-left: 0;
    margin-top: 8px;
  }

  .comment-actions {
    padding-left: 0;
    gap: 8px;
  }

  .comment-header {
    flex-wrap: wrap;
  }

  .comment-time {
    margin-left: 0;
    order: 3;
    flex-basis: 100%;
    margin-top: 4px;
  }
}
</style>