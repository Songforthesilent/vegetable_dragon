<template>
  <div class="comments-section">
    <h3>댓글</h3>
    <div class="comment-input">
      <textarea v-model="newComment" placeholder="댓글을 입력하세요"></textarea>
      <div class="comment-actions">
        <input
            v-if="!isLoggedIn"
            type="password"
            v-model="commentPassword"
            placeholder="비밀번호 입력"
        />
        <button @click="addComment">등록</button>
      </div>
    </div>

    <ul>
      <li v-for="(comment, index) in comments" :key="comment.id || index">
        <div class="comment-text">
          <strong>{{ comment.user }}</strong>: {{ comment.text }}
          <span class="comment-timestamp">{{ comment.timestamp }}</span>
        </div>
        <div class="comment-buttons">
          <button @click="$emit('edit-comment', index)">수정</button>
          <button @click="$emit('delete-comment', index)">삭제</button>
        </div>
      </li>
    </ul>
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
    }
  }
};
</script>

<style scoped>
.comments-section {
  margin-top: 50px;
  text-align: left;
  font-size: 14px;
}

.comments-section h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.comments-section textarea {
  width: 100%;
  height: auto;
  min-height: 100px;
  padding: 15px;
  margin: 10px 0;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
  resize: vertical;
  transition: border-color 0.3s;
}

.comments-section textarea:focus {
  border-color: #3A4CA4;
  outline: none;
  box-shadow: 0 0 5px rgba(58, 76, 164, 0.2);
}

.comment-input {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
}

.comment-actions {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
  margin-top: 10px;
}

.comment-actions input {
  width: 170px;
  height: 40px;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.3s;
}

.comment-actions input:focus {
  border-color: #3A4CA4;
  box-shadow: 0 0 5px rgba(58, 76, 164, 0.2);
}

.comment-actions button {
  width: 80px;
  height: 40px;
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s, transform 0.2s;
  font-size: 14px;
  font-weight: 500;
}

.comment-actions button:hover {
  background-color: #2A357A;
}

.comment-actions button:active {
  transform: scale(0.95);
}

.comments-section ul {
  list-style: none;
  padding: 0;
  margin-top: 30px;
  background-color: #f8f9fc;
  border-radius: 8px;
  overflow: hidden;
}

.comments-section li {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.comments-section li:last-child {
  border-bottom: none;
}

.comment-text {
  flex: 1;
  text-align: left;
  padding-right: 15px;
  word-wrap: break-word;
  line-height: 1.5;
}

.comment-timestamp {
  font-size: 12px;
  color: #888;
  margin-left: 10px;
  font-style: italic;
}

.comment-buttons {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.comment-buttons button {
  background-color: white;
  border: 1px solid #ddd;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
  font-size: 12px;
}

.comment-buttons button:hover {
  background-color: #f0f0f0;
  border-color: #ccc;
}

.comment-buttons button:active {
  transform: scale(0.95);
}

@media (max-width: 768px) {
  .comment-actions {
    flex-direction: column;
    align-items: stretch;
  }

  .comment-actions input,
  .comment-actions button {
    width: 100%;
  }

  .comments-section li {
    flex-direction: column;
  }

  .comment-buttons {
    margin-top: 10px;
    align-self: flex-end;
  }

  .comment-text {
    max-width: 100%;
    padding-right: 0;
  }
}
</style>
