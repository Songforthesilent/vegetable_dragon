  <template>
    <div class="board-detail">
      <!-- 로딩 인디케이터 -->
      <div v-if="loading" class="loading-indicator">
        데이터를 불러오는 중입니다...
      </div>

      <div v-else>
        <!-- 작성자 정보 -->
        <div class="author-info">
          <img src="https://via.placeholder.com/50" alt="Profile" class="profile-img" />
          <div>
            <h4 class="author-name">{{ article.authorUsername }}</h4>
            <p class="date">{{ formatDate(article.createdAt) }}</p>
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
          <a :href="article.link" target="_blank" class="article-link" v-if="article.link">기사 원문 보기</a>
        </div>

        <div class="vote-section">
          <div class="vote-button">
            <button @click="vote('agree')" :class="{ 'active-agree': voteType === 'agree' }">
              👍 진짜뉴스이다. ({{ getVotePercentage(agreeVotes) }}%)
            </button>
            <button @click="vote('disagree')" :class="{ 'active-disagree': voteType === 'disagree' }">
              👎 가짜뉴스이다. ({{ getVotePercentage(disagreeVotes) }}%)
            </button>
          </div>

          <p>투표 결과</p>
          <!-- 투표 퍼센트 바 (클릭 가능) -->
          <div class="progress-bar" @click="handleVote">
            <div
                class="agree-bar"
                :style="{ width: getVotePercentage(agreeVotes) + '%' }"
                data-vote="agree">
              <span v-if="agreeVotes > 0" class="progress-text">
                {{ getVotePercentage(agreeVotes) }}%
              </span>
            </div>

            <div
                class="disagree-bar"
                :style="{ width: getVotePercentage(disagreeVotes) + '%' }"
                data-vote="disagree">
                  <span v-if="disagreeVotes > 0" class="progress-text">
                    {{ getVotePercentage(disagreeVotes) }}%
                  </span>
            </div>
          </div>
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
            <li v-for="(comment, index) in comments" :key="comment.id || index">
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
        <div v-if="editingCommentIndex !== null && !isLoggedIn && !confirmingEditComment" class="modal">
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

        <!-- 댓글 삭제 모달 -->
        <div v-if="deletingCommentIndex !== null" class="modal">
          <!-- 로그인 여부에 따라 표시 분기 -->
          <template v-if="!isLoggedIn">
            <p>댓글 삭제 비밀번호를 입력하세요</p>
            <input type="password" v-model="deleteCommentPassword" placeholder="비밀번호 입력" />
          </template>
          <template v-else>
            <p>이 댓글을 삭제하시겠습니까?</p>
          </template>

          <div>
            <button class="confirm-btn" @click="confirmDeleteComment">삭제</button>
            <button class="cancel-btn" @click="cancelDeleteComment">취소</button>
          </div>
        </div>


        <!-- API 작업 중 로딩 오버레이 -->
        <div v-if="apiLoading" class="api-loading-overlay">
          <div class="api-loading-spinner">처리 중...</div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import axios from "axios";
  export default {
    data() {
      return {
        article: {},
        loading: true,
        apiLoading: false, // API 요청 중 로딩 상태
        error: null,
        showMoreOptions: false, // 더보기 옵션 표시 여부
        loggedInUser: null,
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
        deleteCommentPassword: "",
        agreeVotes: 0,
        disagreeVotes: 0,
        totalVotes: 0,
      };
    },
    computed: {
      // 로그인 상태 확인
      isLoggedIn() {
        return this.loggedInUser !== null;
      }
    },
    mounted() {
      console.log("[DEBUG] 현재 게시글 ID: ", this.$route.params.id);
      this.checkLoginStatus();
      this.getArticleDetail();
      this.fetchComments(); // 게시글에 대한 댓글 목록 가져오기
    },
    methods: {
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
      toggleMoreOptions() {
        this.showMoreOptions = !this.showMoreOptions;
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
            createdAt: response.data.createdAt
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
      formatDate(dateString) {
        if (!dateString) return '';

        try {
          const date = new Date(dateString);

          // 유효한 날짜인지 확인
          if (isNaN(date.getTime())) {
            return dateString;
          }

          const year = date.getFullYear();
          const month = String(date.getMonth() + 1).padStart(2, '0');
          const day = String(date.getDate()).padStart(2, '0');
          const hours = String(date.getHours()).padStart(2, '0');
          const minutes = String(date.getMinutes()).padStart(2, '0');

          return `${year}-${month}-${day} ${hours}:${minutes}`;
        } catch (e) {
          console.error('날짜 형식 변환 오류:', e);
          return dateString;
        }
      },
      vote(type) {
        if (type === "agree") {
          this.agreeVotes++;
          this.voteType = "agree";
        } else {
          this.disagreeVotes++;
          this.voteType = "disagree";
        }
        this.totalVotes = this.agreeVotes + this.disagreeVotes;
      },
      handleVote(event) {
        const voteType = event.target.getAttribute("data-vote");
        if (voteType) {
          this.vote(voteType);
        }
      },
      getVotePercentage(voteCount) {
        if (this.totalVotes === 0) return 50;
        return ((voteCount / this.totalVotes) * 100).toFixed(1);
      },
      addComment() {
        if (!this.newComment.trim()) {
          alert("댓글을 입력하세요!");
          return;
        }

        // const now = new Date();
        // const formattedDate = now.toISOString().replace("T", " ").slice(0, 19);
        const postId = this.article.id;

        // 로그인된 경우
        if (this.isLoggedIn) {
          axios.post(`http://localhost:8081/posts/${postId}/comments`, {
            comment: this.newComment
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
            this.newComment = "";
          }).catch(err => {
            console.error("댓글 저장 실패", err);
            alert("댓글 저장 중 오류가 발생했습니다.");
          });
        }
        // 비로그인 사용자
        else {
          if (!this.commentPassword.trim()) {
            alert("비밀번호를 입력하세요!");
            return;
          }

          axios.post(`http://localhost:8081/posts/${postId}/comments`, {
            comment: this.newComment,
            password: this.commentPassword
          }).then(res => {
            const saved = res.data;
            this.comments.push({
              id: saved.id,
              user: saved.writer,
              text: saved.comment,
              timestamp: new Date(saved.createdAt).toLocaleString()
            });
            this.newComment = "";
            this.commentPassword = "";
          }).catch(err => {
            console.error("익명 댓글 저장 실패", err);
            alert("댓글 저장 중 오류가 발생했습니다.");
          });
        }
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
  }};
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
    display: flex;
    flex-direction: column;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 20px;
    box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.1);
    margin-top: 70px;
  }

  .vote-button {
    display: flex;
    gap: 215x; /* 버튼 사이 간격 조정 */
    width: 100%;
    height: 180px;
    text-align: center;
    margin-bottom: 15px;
  }

  .vote-section button {
    flex: 1;
    margin: 10px;
    padding: 10px;
    cursor: pointer;
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 15px;
    box-shadow: 3px 3px 5px rgba(0, 0, 0, 0.1);
    transition: background-color 0.3s, color 0.3s;
  }

  /* 찬성(진짜뉴스) 버튼 클릭 시 파란색 */
  .vote-section button.active-agree {
    font-weight: bold;
    color: #3A4CA4;
    border: 3px solid #3A4CA4;
  }

  /* 반대(가짜뉴스) 버튼 클릭 시 빨간색 */
  .vote-section button.active-disagree {
    font-weight: bold;
    color: #FF4C4C;
    border: 3px solid #FF4C4C;
  }

  .vote-section p {
    font-size: 15px;
    font-weight: bold;
    margin-top: 30px;
    padding: 10px;
    text-align: left;
  }

  .progress-bar {
    display: flex;
    width: 100%;
    height: 35px;
    background: #3A4CA4;
    border: 1px solid #ccc;
    border-radius: 30px;
    overflow: hidden;
    margin: 10px 0px;
  }

  .progress-text {
    position: absolute;
    font-size: 14px;
    color: white;
    font-weight: bold;
  }

  .agree-bar {
    height: 100%;
    background: #3A4CA4; /* 파란색 (찬성) */
    transition: width 0.3s ease;
  }

  .disagree-bar {
    height: 100%;
    background: #FF4C4C; /* 빨간색 (반대) */
    transition: width 0.3s ease;
  }

  /* 댓글 */
  .comments-section {
    margin-top: 50px;
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
