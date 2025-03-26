<template>
  <div class="board-detail">
    <!-- 작성자 정보 -->
    <div class="author-info">
      <img :src="article.profileImage" alt="Profile" class="profile-img" />
      <div>
        <h4 class="author-name">{{ article.author }}</h4>
        <p class="date">{{ article.created_at }}</p>
      </div>

      <!-- 더보기 버튼 -->
      <div class="more-options">
        <button @click="toggleMoreOptions">⋮</button>
        <div v-if="showMoreOptions" class="options-dropdown">
          <button @click="openEditPostPasswordModal">수정</button>
          <button @click="openDeletePostPasswordModal">삭제</button>
        </div>
      </div>
    </div>

    <!-- 제목 및 내용 -->
    <div class="article-content">
      <h2>{{ article.title }}</h2>
      <p>{{ article.content }}</p>
      <a :href="article.link" target="_blank" class="article-link">기사 원문 보기</a>
    </div>

    <!-- 찬반 투표 -->
    <div class="vote-section">
      <button @click="vote('agree')" :class="{ active: voteType === 'agree' }">👍 진짜뉴스이다.</button>
      <button @click="vote('disagree')" :class="{ active: voteType === 'disagree' }">👎 가짜뉴스이다.</button>
    </div>

    <!-- 댓글 -->
    <div class="comments-section">
      <h3>댓글</h3>
      <div class="comment-input">
        <textarea v-model="newComment" placeholder="댓글을 입력하세요"></textarea>
        <div class="comment-actions">
          <input type="password" v-model="commentPassword" placeholder="비밀번호 입력" />
          <button @click="addComment">등록</button>
        </div>
      </div>

      <ul>
        <li v-for="(comment, index) in comments" :key="index">
          <div class="comment-text">
            <strong>{{ comment.user }}</strong>: {{ comment.text }}
            <span class="comment-timestamp">{{ comment.timestamp }}</span>
          </div>
          <div class="comment-buttons">
            <button @click="openEditCommentModal(index)">수정</button>
            <button @click="openDeleteCommentModal(index)">삭제</button>
          </div>
        </li>
      </ul>
    </div>

    <!-- 비밀번호 입력 모달 (게시글 수정) -->
    <div v-if="editPostPasswordModal" class="modal">
      <p>게시글 수정 비밀번호 입력</p>
      <input type="password" v-model="editPostPassword" placeholder="비밀번호 입력" />
      <button @click="confirmEditPost">확인</button>
      <button @click="closeEditPostPasswordModal">취소</button>
    </div>

    <!-- 게시글 수정 모달 -->
    <div v-if="editPostModal" class="modal">
      <p>게시글 수정</p>
      <input type="text" v-model="article.title" placeholder="제목 입력" />
      <textarea v-model="article.content" placeholder="내용 입력"></textarea>
      <button @click="saveEditPost">저장</button>
      <button @click="editPostModal = false">취소</button>
    </div>

    <!-- 비밀번호 입력 모달 (게시글 삭제) -->
    <div v-if="deletePostPasswordModal" class="modal">
      <p>게시글 삭제 비밀번호 입력</p>
      <input type="password" v-model="deletePostPassword" placeholder="비밀번호 입력" />
      <button @click="confirmDeletePost">삭제</button>
      <button @click="deletePostPasswordModal = false">취소</button>
    </div>

    <!-- 댓글 수정 비밀번호 입력 모달 -->
    <div v-if="editingCommentIndex !== null && !confirmingEditComment" class="modal">
      <p>수정할 댓글의 비밀번호를 입력하세요</p>
      <input type="password" v-model="editCommentPassword" placeholder="비밀번호 입력" />
      <div>
        <button class="confirm-btn" @click="confirmEditComment">확인</button>
        <button class="cancel-btn" @click="cancelEditComment">취소</button>
      </div>
    </div>

    <!-- 댓글 수정 모달 -->
    <div v-if="confirmingEditComment && editingCommentIndex !== null" class="modal">
      <p>수정할 댓글 내용을 입력하세요:</p>
      <textarea v-model="editCommentText" placeholder="수정할 댓글 내용"></textarea>
      <div>
        <button class="confirm-btn" @click="saveEditComment">수정 저장</button>
        <button class="cancel-btn" @click="cancelEditComment">취소</button>
      </div>
    </div>

    <!-- 댓글 삭제 확인 모달 -->
    <div v-if="deletingCommentIndex !== null" class="modal">
      <p>댓글 삭제 비밀번호를 입력하세요</p>
      <input type="password" v-model="deleteCommentPassword" placeholder="비밀번호 입력" />
      <button @click="confirmDeleteComment">삭제</button>
      <button @click="cancelDeleteComment">취소</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      article: {},
      voteType: null,
      newComment: "",
      commentPassword: "",
      comments: [],
      editingCommentIndex: null,
      editPostPassword: "",
      editPostText: "",
      confirmingEditComment: false,
      deletingCommentIndex: null,
      deletePostPassword: "",
      editPostPasswordModal: false,
      editPostModal: false,
      deletePostPasswordModal: false,
      editCommentPassword: "",
      editCommentText: "",
      deleteCommentPassword: ""
    };
  },
  mounted() {
    this.getArticleDetail();
  },
  methods: {
    getArticleDetail() {
      const dummyData = {
        id: this.$route.params.id,
        title: "Vue.js 게시판 상세 페이지",
        content: "이 글은 Vue.js 게시판 상세 페이지를 설명합니다.",
        author: "관리자",
        created_at: "2025-03-19",
        profileImage: "https://via.placeholder.com/50",
        link: "https://example.com"
      };
      this.article = dummyData;
    },
    vote(type) {
      this.voteType = type;
      alert(type === "agree" ? "찬성했습니다." : "반대했습니다.");
    },
    addComment() {
      if (!this.newComment.trim() || !this.commentPassword.trim()) {
        alert("댓글과 비밀번호를 입력하세요!");
        return;
      }

      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, "0");
      const day = String(now.getDate()).padStart(2, "0");
      const hours = String(now.getHours()).padStart(2, "0");
      const minutes = String(now.getMinutes()).padStart(2, "0");
      const seconds = String(now.getSeconds()).padStart(2, "0");

      const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;

      this.comments.push({
        user: "익명",
        text: this.newComment,
        password: this.commentPassword,
        timestamp: formattedDate
      });

      this.newComment = "";
      this.commentPassword = "";
    },
    openEditPostPasswordModal() {
      this.editPostPasswordModal = true;
    },
    confirmEditPost() {
      if (this.editPostPassword === "correct_password") {
        this.editPostModal = true;
        this.closeEditPostPasswordModal();
      } else {
        alert("비밀번호가 일치하지 않습니다.");
      }
    },
    closeEditPostPasswordModal() {
      this.editPostPasswordModal = false;
    },
    saveEditPost() {
      // 게시글 수정 저장 로직
      this.editPostModal = false;
    },
    openDeletePostPasswordModal() {
      this.deletePostPasswordModal = true;
    },
    confirmDeletePost() {
      if (this.deletePostPassword === "correct_password") {
        // 게시글 삭제 로직
        alert("게시글이 삭제되었습니다.");
        this.deletePostPasswordModal = false;
      } else {
        alert("비밀번호가 일치하지 않습니다.");
      }
    },
    openEditCommentModal(index) {
      this.editingCommentIndex = index;
      this.editCommentPassword = "";
      this.editCommentText = this.comments[index].text;
      this.confirmingEditComment = false;
    },
    confirmEditComment() {
      if (this.editCommentPassword === this.comments[this.editingCommentIndex].password) {
        this.confirmingEditComment = true;
      } else {
        alert("비밀번호가 일치하지 않습니다!");
      }
    },
    saveEditComment() {
      this.comments[this.editingCommentIndex].text = this.editCommentText;
      this.cancelEditComment();
    },
    cancelEditComment() {
      this.editingCommentIndex = null;
      this.editCommentText = "";
      this.editCommentPassword = "";
      this.confirmingEditComment = false;
    },
    openDeleteCommentModal(index) {
      this.deletingCommentIndex = index;
      this.deleteCommentPassword = "";
    },
    confirmDeleteComment() {
      if (this.deleteCommentPassword === this.comments[this.deletingCommentIndex].password) {
        this.comments.splice(this.deletingCommentIndex, 1);
        this.cancelDeleteComment();
      } else {
        alert("비밀번호가 일치하지 않습니다!");
      }
    },
    cancelDeleteComment() {
      this.deletingCommentIndex = null;
      this.deleteCommentPassword = "";
    }
  }
};
</script>

<style scoped>
.board-detail {
  max-width: 1050px;
  margin: auto;
  padding: 25px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.author-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
}

.author-name {
  color: #3A4CA4;
  font-size: 16px;
  font-weight: bold;
  text-align: left;
}

.date {
  color: #ccc;
  font-size: 16px;
}

.article-content {
  text-align: left;
}

.article-content h2 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 30px;
}

.article-link {
  display: block;
  margin-top: 20px;
  color: #3A4CA4;
  text-align: right;
  text-decoration: underline;
}

.vote-section {
  margin-top: 70px;
}

.vote-section button {
  width: 100%;
  margin: 10px 0;
  padding: 10px;
  cursor: pointer;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 30px;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
}

.vote-section .active {
  background-color: #3A4CA4;
  color: white;
}

.comments-section {
  margin-top: 20px;
  text-align: left;
  font-size: 14px;
}

.comments-section h3 {
  font-size: 16px;
  font-weight: bold;
}

.comments-section textarea {
  width: 100%;
  height: auto;
  padding: 10px;
  margin: 20px 0;
  border: 1px solid #3A4CA4;
  border-radius: 8px;
}

.comments-section button {
  display: block;
  background-color: white;
  border: 1px solid #ddd;
  box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
}

.comments-section ul {
  list-style: none;
  padding: 0;
  background-color: #f8f9fc;
}

.comment-timestamp {
  font-size: 12px;
  color: #888;
  margin-left: 10px;
  font-style: italic;
}

.comments-section li {
  display: flex; /* 가로 정렬 */
  justify-content: space-between; /* 좌우 정렬 */
  align-items: flex-start; /* 세로 정렬 */
  padding: 10px 15px;
  border-bottom: 2px solid white;
  flex-wrap: wrap;
}

.comment-input {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
}

/* 비밀번호 입력 + 버튼 감싸는 컨테이너 */
.comment-actions {
  display: flex;
  justify-content: flex-end; /* 🔹 오른쪽 정렬 */
  align-items: center;
  gap: 10px;
  margin-top: 5px;
}

/* 비밀번호 입력칸 스타일 */
.comment-actions input {
  width: 170px;
  height: 40px;
  padding: 8px;
  border: 1px solid #3A4CA4;
  border-radius: 3px;
  font-size: 14px;
  outline: none;
  transition: border-color 0.3s;
  text-align: center;
}

/* 입력 칸 포커스 효과 */
.comment-actions input:focus {
  border-color: #2A357A;
}

/* 등록 버튼 스타일 */
.comment-actions button {
  width: 65px; /* 🔹 버튼 크기 */
  height: 40px; /* 🔹 높이 조정 */
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s, transform 0.2s;
  font-size: 14px;
}

/* 🔹 버튼 호버 & 클릭 효과 */
.comment-actions button:hover {
  background-color: #2A357A;
}

.comment-actions button:active {
  transform: scale(0.95);
}

/* 댓글 내부 */
.comment-text {
  flex: 1;
  text-align: left;
  max-width: 85%; /* 댓글 내용 길어지지 않도록 */
  white-space: normal; /* 줄바꿈 허용 */
  word-wrap: break-word; /* 긴 단어 자동 줄바꿈 */
}

.comment-buttons {
  display: flex;
  gap: 8px;
}

/* 🔹 버튼 스타일 */
.comment-buttons button {
  background-color: white;
  border: 1px solid #ddd;
  padding: 5px 10px;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s, transform 0.2s;
  font-size: 12px;
}

/* 🔹 버튼 호버 효과 */
.comment-buttons button:hover {
  background-color: #f0f0f0;
}

.comment-buttons button:active {
  transform: scale(0.95);
}

.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: white;
  padding: 25px;
  border: 1px solid #ddd;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
  width: 350px;
  text-align: center;
}

.modal p {
  font-size: 15px;
  color: #666666;
}

.modal input {
  width: 100%;
  padding: 12px;
  margin: 10px 0;
  border: 1px solid #ddd;
  font-size: 14px;
  transition: border-color 0.3s;
}

.modal input:focus {
  border-color: #3A4CA4;
  outline: none;
  box-shadow: 0 0 5px rgba(58, 76, 164, 0.5);
}

.modal button {
  width: 100px;
  padding: 10px;
  margin: 10px 5px;
  border: none;
  font-size: 14px;
  cursor: pointer;
  transition: background 0.3s, transform 0.2s;
}

/* 🔹 확인 버튼 (파란색) */
.modal .confirm-btn {
  background-color: #3A4CA4;
  color: white;
}

.modal .confirm-btn:hover {
  background-color: #2d3c89;
}

.modal .confirm-btn:active {
  transform: scale(0.98);
}

/* 🔹 취소 버튼 (회색) */
.modal .cancel-btn {
  background-color: #ccc;
  color: black;
}

.modal .cancel-btn:hover {
  background-color: #b0b0b0;
}

.modal .cancel-btn:active {
  transform: scale(0.98);
}

</style>
