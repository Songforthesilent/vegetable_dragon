<template>
  <div class="board-detail">
<<<<<<< HEAD
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    </div>
  </div>
</template>

<script>
<<<<<<< HEAD
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
=======
export default {
  data() {
    return {
      article: {},
      voteType: null,
      newComment: "",
      commentPassword: "",
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
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
<<<<<<< HEAD
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
      }
    },
    closeEditPostPasswordModal() {
      this.editPostPasswordModal = false;
    },
<<<<<<< HEAD
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
=======
    saveEditPost() {
      // 게시글 수정 저장 로직
      this.editPostModal = false;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
    },
    openDeletePostPasswordModal() {
      this.deletePostPasswordModal = true;
    },
<<<<<<< HEAD
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
=======
    confirmDeletePost() {
      if (this.deletePostPassword === "correct_password") {
        // 게시글 삭제 로직
        alert("게시글이 삭제되었습니다.");
        this.deletePostPasswordModal = false;
      } else {
        alert("비밀번호가 일치하지 않습니다.");
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
      }
    },
    openEditCommentModal(index) {
      this.editingCommentIndex = index;
<<<<<<< HEAD
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
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
<<<<<<< HEAD
=======
    confirmDeleteComment() {
      if (this.deleteCommentPassword === this.comments[this.deletingCommentIndex].password) {
        this.comments.splice(this.deletingCommentIndex, 1);
        this.cancelDeleteComment();
      } else {
        alert("비밀번호가 일치하지 않습니다!");
      }
    },
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
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

<<<<<<< HEAD
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
=======
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
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
