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
            <tr v-for="(row, idx) in list" :key="idx">
              <td>{{ row.idx }}</td>
              <td><a @click="fnView(row.idx)">{{ row.title }}</a></td>
              <td>{{ row.author }}</td>
              <td>{{ row.created_at }}</td>
            </tr>
            </tbody>
          </table>
        </section>

        <!-- 페이지네이션 -->
        <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">
          <span class="pg">
            <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-border">&lt;&lt;</a>
            <a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(paging.start_page - 1)"
               class="prev w3-button w3-border">&lt;</a>
            <template v-for="(n, index) in pagination()">
              <template v-if="paging.page == n">
                <strong class="w3-button w3-border w3-green" :key="index">{{ n }}</strong>
              </template>
              <template v-else>
                <a class="w3-button w3-border" href="javascript:;" @click="fnPage(n)" :key="index">{{ n }}</a>
              </template>
            </template>
            <a href="javascript:;" v-if="paging.total_page_cnt > paging.end_page" @click="fnPage(paging.end_page + 1)"
               class="next w3-button w3-border">&gt;</a>
            <a href="javascript:;" @click="fnPage(paging.total_page_cnt)" class="last w3-button w3-border">&gt;&gt;</a>
          </span>
        </div>
      </article>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [], // 게시글 목록
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 1, // 기본 페이지
        page_size: 10, // 기본 페이지 크기
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      },
      selectedCategory: '',
      searchQuery: '',
    };
  },
  mounted() {
    this.fnGetList(); // 페이지 로딩 시 목록 호출
  },
  methods: {
    /*fnGetList() {
      const requestBody = {
        keyword: this.searchQuery,
        category: this.selectedCategory,
        page: this.paging.page,
        size: this.paging.page_size,
      };

      this.$axios
          .get(this.$serverUrl + "/board/list", { params: requestBody })
          .then((res) => {
            this.list = res.data.items; // 서버에서 받은 게시글 목록
            this.paging.total_list_cnt = res.data.total_list_cnt;
            this.paging.total_page_cnt = Math.ceil(this.paging.total_list_cnt / this.paging.page_size);
            this.paging.start_page = Math.floor((this.paging.page - 1) / 10) * 10 + 1;
            this.paging.end_page = Math.min(this.paging.start_page + 9, this.paging.total_page_cnt);
          })
          .catch((err) => {
            if (err.message.includes('Network Error')) {
              alert('네트워크가 원활하지 않습니다. 잠시 후 다시 시도해주세요.');
              console.error('Captured in errorCaptured hook:', err);
            } else if (err.response) {
              // 서버에서 응답한 경우 (HTTP 상태 코드 포함)
              console.error('Error response:', err.response);
              alert(`서버 오류 발생: ${err.response.status} - ${err.response.data.message || '알 수 없는 오류'}`);
            } else {
              // 기타 오류 (예: 요청 설정 문제 등)
              console.error('Error:', err);
              alert('알 수 없는 오류가 발생했습니다.');
            }
          });*/


    fnWrite() {
      this.$router.push('/board/write');
    },
    fnView(idx) {
      this.$router.push(`/board/view/${idx}`);
    },
    fnPage(page) {
      this.paging.page = page;
      this.fnGetList(); // 페이지 변경 시 목록 다시 호출
    },
    pagination() {
      let pageNumber = [];
      for (let i = this.paging.start_page; i <= this.paging.end_page; i++) {
        pageNumber.push(i);
      }
      return pageNumber;
    },
    // 게시글 목록 불러오기 (임시 데이터)
    fnGetList() {
      this.list = [
        { idx: 1, title: '제목1', author: '작성자1', created_at: '작성일시1' },
        { idx: 2, title: '제목2', author: '작성자2', created_at: '작성일시2' },
        { idx: 3, title: '제목3', author: '작성자3', created_at: '작성일시3' }
      ];
      this.paging.total_list_cnt = this.list.length;
      this.paging.total_page_cnt = Math.ceil(this.list.length / this.paging.page_size);
      this.paging.start_page = 1;
      this.paging.end_page = this.paging.total_page_cnt;
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
