<template>
  <div class="board-detail">
    <!-- 로딩 인디케이터 -->
    <div v-if="loading" class="loading-indicator">
      <div class="loading-spinner"></div>
      <p>데이터를 불러오는 중입니다...</p>
    </div>

    <div v-else class="detail-container">
      <!-- 메인 콘텐츠 영역 -->
      <div class="main-content">
        <div class="article-section">
          <!-- 작성자 정보 -->
          <div class="author-section">
            <div class="author-info">
              <div class="author-avatar">
                {{ getAuthorInitial(article.authorUsername) }}
              </div>
              <div class="author-details">
                <div class="author-name">{{ article.authorUsername }}</div>
                <div class="author-date">{{ formatDate(article.createdAt) }}</div>
              </div>
            </div>
            <div class="action-buttons">
              <button v-if="isPostAuthor || !isLoggedIn" class="action-btn edit-btn" @click="openEditPostPasswordModal">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                </svg>
                수정
              </button>
              <button v-if="isPostAuthor || !isLoggedIn" class="action-btn delete-btn" @click="openDeletePostPasswordModal">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <polyline points="3 6 5 6 21 6"></polyline>
                  <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                </svg>
                삭제
              </button>
            </div>
          </div>

          <!-- 제목 및 내용 -->
          <ArticleContent
              :title="article.title"
              :content="article.content"
              :link="article.link"
          />
        </div>
        <div class="comment-section">
          <!-- 댓글 섹션 -->
          <CommentSection
              :comments="comments"
              :is-logged-in="isLoggedIn"
              @add-comment="addComment"
              @edit-comment="openEditCommentModal"
              @delete-comment="openDeleteCommentModal"
          />
        </div>
      </div>

      <!-- 사이드바 -->
      <div class="sidebar">
        <!-- 투표 섹션 -->
        <div class="sidebar-card">
          <h3 class="sidebar-title">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
              </svg>
            투표
          </h3>
          <VoteSection
              :initial-agree-votes="agreeVotes"
              :initial-disagree-votes="disagreeVotes"
              :initial-vote-type="voteType"
              :post-id="article.id"
              @vote="handleVoteUpdate"
              :compact="true"
          />
        </div>

        <!-- AI 분석 섹션 -->
        <div class="sidebar-card">
          <h3 class="sidebar-title">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>
            </svg>
            AI 모델
          </h3>
          <AIAnalysis
              :post-id="article.id"
              :article-content="article.content"
              :article-title="article.title"
          />
        </div>
      </div>
    </div>

    <!-- 모달들 -->
    <ModalContainer
        :show="deletePostPasswordModal"
        :title="isPostAuthor ? '게시글을 삭제하시겠습니까?' : '게시글 삭제 비밀번호 입력'"
        @confirm="confirmDeletePost"
        @cancel="deletePostPasswordModal = false"
    >
      <input
          v-if="!isPostAuthor"
          type="password"
          v-model="deletePostPassword"
          placeholder="비밀번호 입력"
      />
    </ModalContainer>

    <EditPostModal
        :show="editPostModal"
        :post="article"
        @save="saveEditPost"
        @cancel="editPostModal = false"
    />

    <ModalContainer
        :show="editPostPasswordModal"
        :title="isPostAuthor ? '게시글을 수정하시겠습니까?' : '게시글 수정 비밀번호 입력'"
        @confirm="confirmEditPost"
        @cancel="closeEditPostPasswordModal"
    >
      <input
          v-if="!isPostAuthor"
          type="password"
          v-model="editPostPassword"
          placeholder="비밀번호 입력"
      />
    </ModalContainer>

    <ModalContainer
        v-if="editingCommentIndex !== null && !isLoggedIn && !confirmingEditComment"
        :show="editingCommentIndex !== null && !isLoggedIn && !confirmingEditComment"
        title="수정할 댓글의 비밀번호를 입력하세요"
        @confirm="confirmEditComment"
        @cancel="cancelEditComment"
    >
      <input type="password" v-model="editCommentPassword" placeholder="비밀번호 입력" />
    </ModalContainer>

    <EditCommentModal
        :show="editingCommentIndex !== null"
        :comment="editingCommentIndex !== null ? comments[editingCommentIndex] : {}"
        :is-logged-in="isLoggedIn"
        @verify-password="handleVerifyCommentPassword"
        @save="handleSaveComment"
        @cancel="cancelEditComment"
    />

    <ModalContainer
        :show="deletingCommentIndex !== null"
        :title="!isLoggedIn ? '댓글 삭제 비밀번호를 입력하세요' : '이 댓글을 삭제하시겠습니까?'"
        @confirm="confirmDeleteComment"
        @cancel="cancelDeleteComment"
    >
      <input
          v-if="!isLoggedIn"
          type="password"
          v-model="deleteCommentPassword"
          placeholder="비밀번호 입력"
      />
    </ModalContainer>

    <!-- API 작업 중 로딩 오버레이 -->
    <div v-if="apiLoading" class="api-loading-overlay">
      <div class="api-loading-spinner">처리 중...</div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import ArticleContent from '@/components/ArticleContent.vue';
import VoteSection from '@/components/VoteSection.vue';
import CommentSection from '@/components/CommentSection.vue';
import ModalContainer from '@/components/ModalContainer.vue';
import AIAnalysis from '@/components/AIAnalysis.vue';
import EditPostModal from '@/components/EditPostModal.vue';
import EditCommentModal from '@/components/EditCommentModal.vue';

export default {
  components: {
    ArticleContent,
    VoteSection,
    CommentSection,
    ModalContainer,
    AIAnalysis,
    EditPostModal,
    EditCommentModal
  },
  data() {
    return {
      article: {},
      loading: true,
      apiLoading: false,
      error: null,
      loggedInUser: null,
      voteType: null,
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
      deleteCommentPassword: "",
      agreeVotes: 0,
      disagreeVotes: 0,
    };
  },
  computed: {
    isPostAuthor() {
      return (
          this.loggedInUser &&
          this.article &&
          this.loggedInUser.email &&
          this.article.authorEmail &&
          this.loggedInUser.email === this.article.authorEmail
      );
    },
    isLoggedIn() {
      return this.loggedInUser !== null;
    }
  },
  mounted() {
    console.log("[DEBUG] 현재 게시글 ID: ", this.$route.params.id);
    this.checkLoginStatus().then(() => {
      this.getArticleDetail();
      this.fetchComments();
    });
  },
  methods: {
    getAuthorInitial(username) {
      return username ? username.charAt(0).toUpperCase() : 'U';
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString();
    },
    async confirmDeletePost() {
      try {
        this.apiLoading = true;
        const postId = this.article.id;

        const requestBody = this.isPostAuthor
            ? {}
            : { password: this.deletePostPassword };

        await axios.delete(`http://localhost:8081/posts/${postId}`, {
          data: requestBody,
          withCredentials: true
        });

        alert("게시글이 삭제되었습니다.");
        this.$router.push("/board/list");
      } catch (error) {
        console.error("게시글 삭제 실패:", error);
        alert("삭제 실패: " + (error.response?.data?.message || "알 수 없는 오류"));
      } finally {
        this.apiLoading = false;
        this.deletePostPasswordModal = false;
      }
    },
    async checkLoginStatus() {
      try {
        const res = await axios.get("http://localhost:8081/join/session", {withCredentials: true});
        console.log("로그인된 사용자", res.data);
        this.loggedInUser = res.data;
      } catch (e) {
        console.log("로그인되지 않음");
        this.loggedInUser = null;
      }
    },
    async fetchComments() {
      try {
        const postId = this.$route.params.id;
        const response = await axios.get(`http://localhost:8081/posts/${postId}/comments`);

        this.comments = response.data.map(comment => ({
          id: comment.id,
          user: comment.writer,
          text: comment.comment,
          timestamp: new Date(comment.createdAt).toLocaleString(),
          password: ""
        }));
      } catch (error) {
        console.error("댓글 불러오기 실패 : ", error);
        this.comments = [];
      }
    },
    async getArticleDetail() {
      this.loading = true;
      try {
        const postId = this.$route.params.id;
        const response = await axios.get(`http://localhost:8081/posts/${postId}`);

        this.article = {
          id: response.data.id,
          title: response.data.title,
          content: response.data.content,
          authorUsername: response.data.authorUsername,
          authorEmail: response.data.authorEmail,
          createdAt: response.data.createdAt,
          link: response.data.link
        };

        this.loading = false;
      } catch (error) {
        console.error("게시글 상세 정보 불러오기 실패:", error);
        this.error = "게시글을 불러오는 중 오류가 발생했습니다.";

        this.article = {
          id: this.$route.params.id,
          title: "게시글을 불러올 수 없습니다.",
          content: "서버에서 게시글 정보를 가져오는 중 오류가 발생했습니다.",
          authorUsername: "알 수 없음",
          createdAt: new Date().toISOString()
        };

        this.loading = false;
      }
    },
    handleVoteUpdate(voteData) {
      this.voteType = voteData.type;
      this.agreeVotes = voteData.agreeVotes;
      this.disagreeVotes = voteData.disagreeVotes;
    },
    addComment(commentData) {
      const postId = this.article.id;

      if (this.isLoggedIn) {
        axios.post(`http://localhost:8081/posts/${postId}/comments`, {
          comment: commentData.text
        }, {
          withCredentials: true
        }).then(res => {
          const saved = res.data;
          this.comments.push({
            id: saved.id,
            user: saved.writer,
            text: saved.comment,
            timestamp: new Date(saved.createdAt).toLocaleString()
          });
        }).catch(err => {
          console.error("댓글 저장 실패", err);
          alert("댓글 저장 중 오류가 발생했습니다.");
        });
      } else {
        axios.post(`http://localhost:8081/posts/${postId}/comments`, {
          comment: commentData.text,
          password: commentData.password
        }).then(res => {
          const saved = res.data;
          this.comments.push({
            id: saved.id,
            user: saved.writer,
            text: saved.comment,
            timestamp: new Date(saved.createdAt).toLocaleString()
          });
        }).catch(err => {
          console.error("익명 댓글 저장 실패", err);
          alert("댓글 저장 중 오류가 발생했습니다.");
        });
      }
    },
    openEditPostPasswordModal() {
      if (this.isPostAuthor) {
        this.editPostModal = true;
      } else {
        this.editPostPasswordModal = true;
      }
    },
    confirmEditPost() {
      if (this.isPostAuthor) {
        this.editPostModal = true;
        this.closeEditPostPasswordModal();
        return;
      }

      if (!this.editPostPassword.trim()) {
        alert("비밀번호를 입력하세요.");
        return;
      }
    },
    closeEditPostPasswordModal() {
      this.editPostPasswordModal = false;
    },
    async saveEditPost() {
      try {
        this.apiLoading = true;
        const postId = this.article.id;

        const requestBody = {
          title: this.article.title,
          content: this.article.content,
          category: this.article.category,
        };

        await axios.put(`http://localhost:8081/posts/${postId}`, requestBody, {
          withCredentials: true
        });

        alert("게시글이 수정되었습니다.");
        this.editPostModal = false;
      } catch (error) {
        console.error("게시글 수정 실패:", error);
        alert("수정 실패: " + (error.response?.data?.message || "알 수 없는 오류"));
      } finally {
        this.apiLoading = false;
      }
    },
    openDeletePostPasswordModal() {
      this.deletePostPasswordModal = true;
    },
    async confirmDeleteComment() {
      try {
        const commentIndex = this.deletingCommentIndex;
        const comment = this.comments[commentIndex];
        const postId = this.$route.params.id;
        const commentId = comment.id;

        this.apiLoading = true;

        const requestBody = {
          password: this.isLoggedIn ? "LOGIN_USER" : this.deleteCommentPassword
        };

        await axios.delete(`http://localhost:8081/posts/${postId}/comments/${commentId}`, {
          data: requestBody,
          withCredentials: true
        });

        this.comments.splice(commentIndex, 1);
        alert("댓글이 삭제되었습니다.");
      } catch (error) {
        console.error("댓글 삭제 실패:", error);
        if (error.response) {
          if (error.response.status === 401 || error.response.status === 403) {
            alert("비밀번호가 일치하지 않거나 삭제 권한이 없습니다.");
          } else if (error.response.status === 404) {
            alert("댓글을 찾을 수 없습니다.");
          } else {
            alert(`댓글 삭제 중 오류: ${error.response.data.message || "알 수 없는 오류"}`);
          }
        } else {
          alert("서버 연결에 실패했습니다.");
        }
      } finally {
        this.apiLoading = false;
        this.cancelDeleteComment();
      }
    },
    openEditCommentModal(index) {
      this.editingCommentIndex = index;
      this.editCommentText = this.comments[index].text;
      this.editCommentPassword = "";
      this.confirmingEditComment = this.isLoggedIn;
    },
    confirmEditComment() {
      if (!this.editCommentPassword.trim()) {
        alert("비밀번호를 입력하세요.");
        return;
      }

      this._tempEditPassword = this.editCommentPassword;
      this.confirmingEditComment = true;
    },
    async saveEditComment() {
      const comment = this.comments[this.editingCommentIndex];
      const postId = this.article.id;
      const commentId = comment.id;

      const requestBody = {
        comment: this.editCommentText,
        password: this.isLoggedIn ? "LOGIN_USER" : this._tempEditPassword
      };

      try {
        await axios.put(
            `http://localhost:8081/posts/${postId}/comments/${commentId}`,
            requestBody,
            { withCredentials: true }
        );

        this.comments[this.editingCommentIndex].text = this.editCommentText;
        alert("댓글이 수정되었습니다.");
        this.cancelEditComment();
      } catch (error) {
        console.error("댓글 수정 실패:", error);
        alert("수정 실패: " + (error.response?.data?.message || "알 수 없는 오류"));
      }
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
    cancelDeleteComment() {
      this.deletingCommentIndex = null;
      this.deleteCommentPassword = "";
    }
  }
};
</script>

<style scoped>
.board-detail {
  margin: auto;
  padding: 40px 200px;
  background: #f8fafc;
}

.loading-indicator {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f4f6;
  border-top: 4px solid #3b82f6;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 16px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.detail-container {
  display: grid;
  grid-template-columns: 1fr 400px;
  gap: 30px;
  align-items: start;
}

/* 메인 콘텐츠 */
.main-content {
  background: white;
  border-radius: 6px;
  padding: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

/* 작성자 섹션 */
.author-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 0 20px;
  border-bottom: 2px solid #f1f5f9;
  margin-bottom: 40px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.author-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: linear-gradient(135deg, #3b82f6, #7c3aed);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 18px;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.author-name {
  font-weight: 700;
  color: #1e293b;
  font-size: 16px;
}

.author-date {
  font-size: 14px;
  color: #64748b;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.action-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 7px 12px;
  border: 2px solid #e5e7eb;
  border-radius: 10px;
  background: white;
  color: #64748b;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-btn:hover {
  background-color: #f9fafb;
  color: #374151;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.edit-btn:hover {
  color: #3b82f6;
  border-color: #3b82f6;
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
}

.delete-btn:hover {
  color: #ef4444;
  border-color: #ef4444;
  box-shadow: 0 4px 12px rgba(239, 68, 68, 0.2);
}

/* 사이드바 */
.sidebar {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.sidebar-card {
  background: white;
  border-radius: 6px;
  padding: 24px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid #f1f5f9;
}

.sidebar-title {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 18px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 20px 0;
  padding-bottom: 12px;
  border-bottom: 2px solid #f1f5f9;
}

.sidebar-title svg {
  color: #3b82f6;
}

/* 관련 토론 섹션 */
.related-discussions {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.related-item {
  padding: 16px;
  background: #f8fafc;
  border-radius: 12px;
  border-left: 4px solid #3b82f6;
  cursor: pointer;
  transition: all 0.3s ease;
}

.related-item:hover {
  background: #f1f5f9;
  transform: translateX(4px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.1);
}

.related-title {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 6px;
  line-height: 1.4;
}

.related-meta {
  font-size: 12px;
  color: #64748b;
}

/* API 로딩 오버레이 */
.api-loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.9);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  backdrop-filter: blur(5px);
}

.api-loading-spinner {
  padding: 30px 40px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  font-size: 18px;
  font-weight: 600;
  color: #3b82f6;
  display: flex;
  align-items: center;
  gap: 15px;
}

.api-loading-spinner::before {
  content: '';
  width: 20px;
  height: 20px;
  border: 3px solid #f3f4f6;
  border-top: 3px solid #3b82f6;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .detail-container {
    grid-template-columns: 1fr;
    gap: 20px;
  }

  .sidebar {
    order: -1;
  }

  .sidebar-card {
    padding: 20px;
  }
}

@media (max-width: 768px) {
  .board-detail {
    padding: 15px;
  }

  .main-content {
    padding: 25px 20px;
  }

  .author-section {
    flex-direction: column;
    gap: 15px;
    align-items: flex-start;
  }

  .action-buttons {
    align-self: stretch;
    justify-content: space-between;
  }

  .action-btn {
    flex: 1;
    justify-content: center;
  }

  .sidebar-card {
    padding: 16px;
  }

  .sidebar-title {
    font-size: 16px;
  }
}

@media (max-width: 480px) {
  .board-detail {
    padding: 10px;
  }

  .main-content {
    padding: 20px 15px;
  }

  .author-avatar {
    width: 40px;
    height: 40px;
    font-size: 16px;
  }

  .author-name {
    font-size: 14px;
  }

  .action-btn {
    padding: 8px 12px;
    font-size: 12px;
  }
}
</style>