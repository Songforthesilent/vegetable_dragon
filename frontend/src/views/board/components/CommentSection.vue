<template>
  <div class="comment-section">
    <h3>댓글</h3>
    <div v-for="comment in comments" :key="comment.id" class="comment-item">
      <div v-if="editingCommentId === comment.id">
        <input v-model="editedComment" class="edit-input" />
        <button @click="updateComment(comment.id)">수정완료</button>
        <button @click="cancelEdit">취소</button>
      </div>
      <div v-else>
        <p>{{ comment.content }}</p>
        <small>작성자: {{ comment.writer }}</small>
        <div class="comment-actions">
          <button @click="startEdit(comment)">수정</button>
          <button @click="deleteComment(comment.id)">삭제</button>
        </div>
      </div>
    </div>

    <div class="comment-form">
      <input v-model="newComment" placeholder="댓글을 입력하세요" class="new-comment-input" />
      <button @click="addComment">작성</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    postId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      comments: [],
      newComment: '',
      editedComment: '',
      editingCommentId: null
    }
  },
  methods: {
    fetchComments() {
      fetch(`http://localhost:8081/board/comments/${this.postId}`)
          .then((res) => res.json())
          .then((data) => {
            this.comments = data;
          })
          .catch((err) => console.error(err));
    },
    addComment() {
      if (!this.newComment.trim()) return;

      fetch(`http://localhost:8081/board/comments/${this.postId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ content: this.newComment })
      })
          .then(() => {
            this.newComment = '';
            this.fetchComments();
          })
          .catch((err) => console.error(err));
    },
    startEdit(comment) {
      this.editingCommentId = comment.id;
      this.editedComment = comment.content;
    },
    cancelEdit() {
      this.editingCommentId = null;
      this.editedComment = '';
    },
    updateComment(commentId) {
      fetch(`http://localhost:8081/board/comments/${commentId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ content: this.editedComment })
      })
          .then(() => {
            this.editingCommentId = null;
            this.editedComment = '';
            this.fetchComments();
          })
          .catch((err) => console.error(err));
    },
    deleteComment(commentId) {
      fetch(`http://localhost:8081/board/comments/${commentId}`, {
        method: "DELETE"
      })
          .then(() => {
            this.fetchComments();
          })
          .catch((err) => console.error(err));
    }
  },
  mounted() {
    this.fetchComments();
  }
}
</script>

<style scoped>
.comment-section {
  margin-top: 30px;
}
.comment-item {
  margin-bottom: 15px;
}
.comment-actions {
  margin-top: 5px;
}
.comment-actions button {
  margin-right: 5px;
}
.comment-form {
  margin-top: 20px;
}
.new-comment-input, .edit-input {
  width: 70%;
  padding: 10px;
  margin-right: 10px;
}
button {
  background-color: #3A4CA4;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
}
</style>
