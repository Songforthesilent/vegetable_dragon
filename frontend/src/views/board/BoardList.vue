<template>
  <div class="main-container">
    <div class="content">
      <article class="board-list-container">
        <!-- 페이지 제목 -->
        <!--<div class="page-header">
          <p class="page-description">다양한 주제의 게시글을 확인하세요</p>
        </div>-->

        <!-- 검색 및 필터 영역 -->
        <section class="filter-section">
          <div class="filter-controls">
            <div class="select-wrapper">
              <select v-model="selectedCategory" class="category-select" @change="search">
                <option value="">전체</option>
                <option value="v1">경제</option>
                <option value="v2">연예</option>
                <option value="v3">정치</option>
                <option value="v4">사회</option>
                <option value="v5">국제</option>
                <option value="v6">문화</option>
              </select>
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="select-icon">
                <polyline points="6 9 12 15 18 9"></polyline>
              </svg>
            </div>

            <div class="search-container">
              <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="검색어를 입력하세요"
                  class="search-input"
                  @keyup.enter="search"
              />
              <button @click="search" class="search-button">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <circle cx="11" cy="11" r="8"></circle>
                  <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
                </svg>
              </button>
            </div>
          </div>

          <button @click="fnWrite" class="write-button">
            글 등록하기
          </button>
        </section>

        <!-- 게시글 목록 -->
        <section class="board-card">
          <div class="board-table-container">
            <table class="board-table">
              <thead>
              <tr>
                <th class="col-id">No</th>
                <th class="col-title">제목</th>
                <th class="col-author">작성자</th>
                <th class="col-date">등록일시</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="post in posts" :key="post.id" @click="fnView(post.id)" class="post-row">
                <td class="col-id">{{ post.id }}</td>
                <td class="col-title">
                  <div class="post-title-container">
                    <span class="post-title">{{ post.title }}</span>
                  </div>
                </td>
                <td class="col-author">{{ post.authorUsername }}</td>
                <td class="col-date">{{ formatDate(post.createdAt) }}</td>
              </tr>
              <tr v-if="posts.length === 0" class="empty-row">
                <td colspan="4">
                  <div class="empty-message">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                      <circle cx="12" cy="12" r="10"></circle>
                      <line x1="12" y1="8" x2="12" y2="12"></line>
                      <line x1="12" y1="16" x2="12.01" y2="16"></line>
                    </svg>
                    <p>게시글이 없습니다</p>
                  </div>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </section>

        <!-- 페이지네이션 -->
        <div class="pagination-container" v-if="totalElements > 0">
          <div class="pagination">
            <button
                @click="fnPage(0)"
                class="page-button first-button"
                :disabled="currentPage === 0"
                title="첫 페이지"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="11 17 6 12 11 7"></polyline>
                <polyline points="18 17 13 12 18 7"></polyline>
              </svg>
            </button>

            <button
                @click="fnPage(currentPage - 1)"
                class="page-button prev-button"
                :disabled="currentPage === 0"
                title="이전 페이지"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="15 18 9 12 15 6"></polyline>
              </svg>
            </button>

            <div class="page-numbers">
              <button
                  v-for="n in pagination"
                  :key="n"
                  @click="fnPage(n)"
                  :class="['page-number', { active: currentPage === n }]"
              >
                {{ n + 1 }}
              </button>
            </div>

            <button
                @click="fnPage(currentPage + 1)"
                class="page-button next-button"
                :disabled="currentPage >= totalPages - 1"
                title="다음 페이지"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="9 18 15 12 9 6"></polyline>
              </svg>
            </button>

            <button
                @click="fnPage(totalPages - 1)"
                class="page-button last-button"
                :disabled="currentPage >= totalPages - 1"
                title="마지막 페이지"
            >
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="13 17 18 12 13 7"></polyline>
                <polyline points="6 17 11 12 6 7"></polyline>
              </svg>
            </button>
          </div>
        </div>
      </article>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      posts: [], // 게시글 목록
      currentPage: 0, // 현재 페이지 (0부터 시작)
      pageSize: 10, // 페이지 크기
      totalPages: 0, // 전체 페이지 수
      totalElements: 0, // 전체 게시글 수
      selectedCategory: '',
      searchQuery: '',
    };
  },
  computed: {
    pagination() {
      // 페이지네이션 버튼 계산
      const pageButtons = [];
      const startPage = Math.floor(this.currentPage / 10) * 10;
      const endPage = Math.min(startPage + 9, this.totalPages - 1);

      for (let i = startPage; i <= endPage; i++) {
        pageButtons.push(i);
      }

      return pageButtons;
    }
  },
  mounted() {
    this.fnGetList(); // 페이지 로딩 시 목록 호출
  },
  methods: {
    fnGetList() {
      // API 호출 파라미터 설정
      const params = {
        page: this.currentPage,
        size: this.pageSize
      };

      // 카테고리나 검색어가 있는 경우 파라미터 추가
      if (this.selectedCategory) {
        params.category = this.selectedCategory;
      }

      if (this.searchQuery) {
        params.keyword = this.searchQuery;
      }

      // API 호출
      axios.get("http://localhost:8081/posts", { params })
          .then(response => {
            // API 응답 데이터 처리
            this.posts = response.data.content;
            this.totalPages = response.data.totalPages;
            this.totalElements = response.data.totalElements;
          })
          .catch(error => {
            console.error('게시글 목록을 불러오는 중 오류가 발생했습니다:', error);
            if (error.response) {
              // 서버에서 응답한 경우 (HTTP 상태 코드 포함)
              console.error('Error response:', error.response);
              alert(`서버 오류 발생: ${error.response.status} - ${error.response.data.message || '알 수 없는 오류'}`);
            } else if (error.message.includes('Network Error')) {
              alert('네트워크가 원활하지 않습니다. 잠시 후 다시 시도해주세요.');
            } else {
              // 기타 오류
              alert('알 수 없는 오류가 발생했습니다.');
            }
          });
    },
    search() {
      this.currentPage = 0; // 검색 시 첫 페이지로 이동
      this.fnGetList();
    },
    fnWrite() {
      this.$router.push('/board/write');
    },
    fnView(id) {
      // id 파라미터를 사용하여 상세 페이지로 이동
      this.$router.push(`/board/view/${id}`);
    },
    fnPage(page) {
      this.currentPage = page;
      this.fnGetList(); // 페이지 변경 시 목록 다시 호출
    },
    formatDate(dateString) {
      // ISO 형식의 날짜를 사용자 친화적인 형식으로 변환
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}`;
    }
  },
};
</script>

<style scoped>
/* 전체 레이아웃 */
.main-container {
  display: flex;
  justify-content: center;
  padding: 0 20px;
  margin-top: 30px;
}

.content {
  width: 100%;
  max-width: 1200px;
}

.board-list-container {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

/* 필터 섹션 */
.filter-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 20px;
}

.filter-controls {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.select-wrapper {
  position: relative;
}

.category-select {
  appearance: none;
  padding: 12px 40px 12px 16px;
  border: 1px solid #3A4CA4;
  border-radius: 10px;
  background-color: #ffffff;
  color: #1e293b;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 90px;
}

.category-select:focus {
  outline: none;
  border-color: #3A4CA4;
  box-shadow: 0 0 0 2px rgba(58, 76, 164, 0.1);
}

.select-icon {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #64748b;
  pointer-events: none;
}

.search-container {
  position: relative;
  flex-grow: 1;
  min-width: 250px;
}

.search-input {
  width: 100%;
  padding: 12px 45px 12px 16px;
  border: 1px solid #3A4CA4;
  border-radius: 10px;
  background-color: #ffffff;
  color: #1e293b;
  font-size: 14px;
  transition: all 0.3s ease;
}

.search-input:focus {
  outline: none;
  border-color: #3A4CA4;
  box-shadow: 0 0 0 2px rgba(58, 76, 164, 0.1);
}

.search-button {
  position: absolute;
  right: 5px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.search-button:hover {
  background-color: #2d3a7c;
}

.write-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px;
  background-color: #3A4CA4;
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.write-button:hover {
  background-color: #2d3a7c;
  transform: translateY(-2px);
}

.button-icon {
  stroke: white;
}

/* 게시글 목록 테이블 */
.board-card {
  background-color: #ffffff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.board-card:hover {
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.08);
}

.board-table-container {
  overflow-x: auto;
}

.board-table {
  width: 100%;
  border-collapse: collapse;
}

.board-table th {
  background-color: #f8fafc;
  color: #475569;
  font-weight: 600;
  font-size: 14px;
  text-align: center;
  padding: 16px;
  border-bottom: 1px solid #e2e8f0;
}

.board-table td {
  padding: 16px;
  border-bottom: 1px solid #f1f5f9;
  color: #1e293b;
  font-size: 14px;
}

.col-id {
  width: 80px;
  text-align: center;
}

.col-title {
  min-width: 300px;
}

.col-author {
  width: 120px;
  text-align: center;
}

.col-date {
  width: 150px;
  text-align: center;
}

.post-row {
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.post-row:hover {
  background-color: #f8fafc;
}

.post-title-container {
  display: flex;
  align-items: center;
}

.post-title {
  font-weight: 500;
  color: #1e293b;
  transition: color 0.2s ease;
}

.post-row:hover .post-title {
  color: #3A4CA4;
}

.empty-row td {
  padding: 40px 0;
}

.empty-message {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10px;
  color: #94a3b8;
}

.empty-message svg {
  stroke: #94a3b8;
}

.empty-message p {
  margin: 0;
  font-size: 15px;
}

/* 페이지네이션 */
.pagination-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.pagination {
  display: flex;
  align-items: center;
  gap: 5px;
}

.page-button {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  background-color: #ffffff;
  color: #64748b;
  cursor: pointer;
  transition: all 0.2s ease;
}

.page-button:hover:not(:disabled) {
  background-color: #f1f5f9;
  color: #1e293b;
}

.page-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-numbers {
  display: flex;
  gap: 5px;
}

.page-number {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 36px;
  height: 36px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  background-color: #ffffff;
  color: #64748b;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.page-number:hover {
  background-color: #f1f5f9;
  color: #1e293b;
}

.page-number.active {
  background-color: #3A4CA4;
  color: white;
  border-color: #3A4CA4;
}

.page-info {
  font-size: 14px;
  color: #64748b;
}

/* 반응형 디자인 */
@media (max-width: 768px) {
  .filter-section {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-controls {
    flex-direction: column;
    width: 100%;
  }

  .search-container {
    width: 100%;
  }

  .write-button {
    width: 100%;
    justify-content: center;
  }

  .col-id, .col-date {
    display: none;
  }

  .page-numbers {
    max-width: 250px;
    flex-wrap: wrap;
    justify-content: center;
  }
}
</style>