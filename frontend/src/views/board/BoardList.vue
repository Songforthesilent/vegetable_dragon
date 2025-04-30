<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <div class="content">
      <article class="board-list">
        <section class="common-buttons">
          <!-- 왼쪽 정렬: 카테고리 선택과 검색창 -->
          <div class="left-controls">
            <select v-model="selectedCategory" class="category-select">
              <option value="">전체</option>
              <option value="vue">사회</option>
              <option value="react">정치</option>
              <option value="angular">문화</option>
            </select>
            <div>
              <input v-model="searchQuery" type="text" placeholder="검색어 입력" class="search-input" />
              <button @click="search" class="search-button">
                <i class="fas fa-search search-icon"></i> <!-- 돋보기 아이콘 -->
              </button>
            </div>
          </div>

          <!-- 오른쪽 정렬: 등록 버튼 -->
          <div class="write-controls">
            <button type="button" class="w3-button w3-round w3-blue-gray" @click="fnWrite">등록</button>
          </div>
        </section>

        <section>
          <table class="w3-table-all">
            <thead>
            <tr>
              <th>No</th>
              <th>제목</th>
              <th>작성자</th>
              <th>등록일시</th>
            </tr>
            </thead>
            <tbody>
              <tr v-for="post in posts" :key="post.id">
                <td>{{ post.id }}</td>
                <td><a @click="fnView(post.id)">{{ post.title }}</a></td>
                <td>{{ post.authorUsername }}</td>
                <td>{{ formatDate(post.createdAt) }}</td>
              </tr>
            </tbody>
          </table>
        </section>

        <!-- 페이지네이션 -->
        <div class="pagination w3-bar w3-padding-16 w3-small" v-if="totalElements > 0">
          <span class="pg">
            <a href="javascript:;" @click="fnPage(0)" class="first w3-button w3-border">&lt;&lt;</a>
            <a href="javascript:;" v-if="currentPage > 0" @click="fnPage(currentPage - 1)"
               class="prev w3-button w3-border">&lt;</a>
            <template v-for="n in pagination">
              <template v-if="currentPage === n">
                <strong class="w3-button w3-border w3-green" :key="n">{{ n + 1 }}</strong>
              </template>
              <template v-else>
                <a class="w3-button w3-border" href="javascript:;" @click="fnPage(n)" :key="n">{{ n + 1 }}</a>
              </template>
            </template>
            <a href="javascript:;" v-if="currentPage < totalPages - 1" @click="fnPage(currentPage + 1)"
               class="next w3-button w3-border">&gt;</a>
            <a href="javascript:;" @click="fnPage(totalPages - 1)" class="last w3-button w3-border">&gt;&gt;</a>
          </span>
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
.board-list {
  width: 1050px;
  margin: auto;
}

.w3-table-all {
  width: 100%;
  border-collapse: collapse;
  border: none;
}

.w3-table-all th {
  border-top: 1px solid #939393;
  border-bottom: 1px solid #939393;
  background-color: #f1f1f1;
  color: black;
  text-align: center;
  font-size: 15px;
  font-weight: bold;
}

.w3-table-all td {
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
  text-align: center;
  color: #939393;
  font-size: 15px;
}

.w3-table-all td a {
  text-decoration: none;
}

.w3-table-all tr:nth-child(even) {
  background-color: transparent;
}

.w3-table-all tr:hover {
  background-color: #f1f1f1;
}

.common-buttons {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
  margin-bottom: 15px;
}

.left-controls {
  display: flex;
  gap: 10px;
}

.write-controls {
  display: flex;
}

.category-select,
.search-input {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.category-select {
  width: 70px;
}

.search-input {
  width: 200px;
}

.search-button {
  padding: 8px 15px;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
