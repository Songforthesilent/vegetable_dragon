<template>
  <div class="board-detail">
    <!-- 로딩 인디케이터 -->
    <div v-if="loading" class="loading-indicator">
      데이터를 불러오는 중입  다...
    </div>

    <div v-else>
      <!-- 작성자 정보 -->
      <ArticleHeader
          :author="article.authorUsername"
          :date="article.createdAt"
          @edit="openEditPostPasswordModal"
          @delete="openDeletePostPasswordModal"
      />

      <!-- 제목 및 내용 -->
      <ArticleContent
          :title="article.title"
          :content="article.content"
          :link="article.link"
      />

      <!-- AI 분석 섹션 -->
      <AIAnalysis
          :post-id="article.id"
          :article-content="article.content"
          :article-title="article.title"
      />
      <!-- 투표 섹션 -->
      <VoteSection
          :initial-agree-votes="agreeVotes"
          :initial-disagree-votes="disagreeVotes"
          :initial-vote-type="voteType"
          :post-id="article.id"
          @vote="handleVoteUpdate"
      />

      <!-- 댓글 섹션 -->
      <CommentSection
          :comments="comments"
          :is-logged-in="isLoggedIn"
          @add-comment="addComment"
          @edit-comment="openEditCommentModal"
          @delete-comment="openDeleteCommentModal"
      />

      <!-- 모달 컴포넌트들 -->
      <!-- 게시글 삭제 비밀번호 모달 -->
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

      <!-- 게시글 수정 모달 -->
      <ModalContainer
          :show="editPostModal"
          title="게시글 수정"
          @confirm="saveEditPost"
          @cancel="editPostModal = false"
      >
        <input type="text" v-model="article.title" placeholder="제목 입력" />
        <textarea v-model="article.content" placeholder="내용 입력"></textarea>
      </ModalContainer>

      <!-- 게시글 수정 비밀번호 모달 -->
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


      <!-- 게시글 삭제 비밀번호 모달 -->
      <ModalContainer
          :show="deletePostPasswordModal"
          title="게시글 삭제 비밀번호 입력"
          @confirm="confirmDeletePost"
          @cancel="deletePostPasswordModal = false"
      >
        <input type="password" v-model="deletePostPassword" placeholder="비밀번호 입력" />
      </ModalContainer>

      <!-- 댓글 수정 비밀번호 모달 -->
      <ModalContainer
          v-if="editingCommentIndex !== null && !isLoggedIn && !confirmingEditComment"
          :show="editingCommentIndex !== null && !isLoggedIn && !confirmingEditComment"
          title="수정할 댓글의 비밀번호를 입력하세요"
          @confirm="confirmEditComment"
          @cancel="cancelEditComment"
      >
        <input type="password" v-model="editCommentPassword" placeholder="비밀번호 입력" />
      </ModalContainer>

      <!-- 댓글 수정 모달 -->
      <ModalContainer
          :show="confirmingEditComment && editingCommentIndex !== null"
          title="수정할 댓글 내용을 입력하세요"
          @confirm="saveEditComment"
          @cancel="cancelEditComment"
      >
        <textarea v-model="editCommentText" placeholder="수정할 댓글 내용"></textarea>
      </ModalContainer>

      <!-- 댓글 삭제 모달 -->
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
  </div>
</template>

<script>
import axios from "axios";
import ArticleHeader from '@/components/ArticleHeader.vue';
import ArticleContent from '@/components/ArticleContent.vue';
import VoteSection from '@/components/VoteSection.vue';
import CommentSection from '@/components/CommentSection.vue';
import ModalContainer from '@/components/ModalContainer.vue';
import AIAnalysis from '@/components/AIAnalysis.vue';

export default {
  components: {
    ArticleHeader,
    ArticleContent,
    VoteSection,
    CommentSection,
    ModalContainer,
    AIAnalysis
  },
  data() {
    return {
      article: {},
      loading: true,
      apiLoading: false, // API 요청 중 로딩 상태
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
    // 로그인 상태 확인
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
    async confirmDeletePost() {
      try {
        this.apiLoading = true;
        const postId = this.article.id;

        const requestBody = this.isPostAuthor
            ? {} // 본인은 비밀번호 없이 삭제
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
          id: comment.id, // 댓글 ID 추가
          user: comment.writer,
          text: comment.comment,
          timestamp: new Date(comment.createdAt).toLocaleString(),
          password: ""
        }));
      } catch (error) {
        console.error("댓글 불러오기 실패 : ", error);
        // 댓글 불러오기 실패 시 빈 배열로 초기화
        this.comments = [];
      }
    },
    async getArticleDetail() {
      this.loading = true;
      try {
        const postId = this.$route.params.id;
        const response = await axios.get(`http://localhost:8081/posts/${postId}`);

        // API 응답 데이터를 article 객체에 매핑
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

        // API 호출 실패 시 더미 데이터 사용
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

      // 여기서 서버에 투표 정보를 저장하는 API 호출을 할 수 있습니다
    },
    addComment(commentData) {
      const postId = this.article.id;

      // 로그인된 경우
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
      }
      // 비로그인 사용자
      else {
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

      console.log("loggedInUser.email:", this.loggedInUser?.email);
      console.log("article.authorEmail:", this.article?.authorEmail);
      console.log("isPostAuthor:", this.isPostAuthor);
      // 작성자라면 비밀번호 없이 바로 수정 모달 오픈
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
          category: this.article.category,  // 혹시 바꾸게 된다면 포함
          // password: this.isPostAuthor ? "LOGIN_USER" : this.editPostPassword
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

        // 로그인 여부에 따라 body 설정
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
      this.confirmingEditComment = this.isLoggedIn;  // 로그인 사용자는 비밀번호 없이 바로 수정 가능
    },
    confirmEditComment() {
      const comment = this.comments[this.editingCommentIndex];
      if (!this.editCommentPassword.trim()) {
        alert("비밀번호를 입력하세요.");
        return;
      }
      if (this.editCommentPassword === comment.password) {
        this.confirmingEditComment = true;
      } else {
        alert("비밀번호가 일치하지 않거나 삭제 권한이 없습니다.");
      }
    },
    async saveEditComment() {
      const comment = this.comments[this.editingCommentIndex];
      const postId = this.article.id;
      const commentId = comment.id;

      const requestBody = {
        comment: this.editCommentText,
        password: this.isLoggedIn ? "LOGIN_USER" : this.editCommentPassword
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
  max-width: 1050px;
  margin: auto;
  padding: 25px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.loading-indicator {
  text-align: center;
  padding: 50px;
  font-size: 16px;
  color: #666;
}

.api-loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.api-loading-spinner {
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  font-size: 16px;
  color: #3A4CA4;
}
</style>