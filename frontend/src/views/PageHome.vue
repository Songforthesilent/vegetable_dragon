<template>
  <div class="main-container">
    <!-- 게시글 영역 -->
    <article class="content">
      <!-- 검색창 영역 -->
      <search-bar v-if="showSearch" @search="search" />

      <!-- MainHeader 영역 -->
      <HeroSection />

      <!-- Best Topics 영역 -->
      <best-topic-section />

      <!-- 최근 게시글 영역 -->
      <recent-post-section />
    </article>

    <!-- 로그인 배너 영역 -->
  </div>
</template>

<script>
import HeroSection from '@/components/home/HeroSection.vue';
import SearchBar from '@/components/home/SearchBar.vue';
import BestTopicSection from '@/components/home/BestTopicSection.vue';
import RecentPostSection from '@/components/home/RecentPostSection.vue';
import axios from 'axios';

export default {
  components: {
    HeroSection,
    SearchBar,
    BestTopicSection,
    RecentPostSection
  },
  data() {
    return {
      categories: ['경제', '연예', '정치', '사회', '국제', '문화'],
      selectedCategory: '전체',  // 기본 카테고리
      bestTopics: [], // 예시로 남겨놓은 Best Topics
      recentArticles: [],  // 백엔드에서 받아올 최신 게시글
      page: 0, // 현재 페이지 (기본은 첫 번째 페이지)
      size: 5, // 한 페이지에 표시할 게시글 수
      categoryMap: {
        "vue": "사회",
        "react": "정치",
        "angular": "문화",
        "economy": "경제",
        "entertainment": "연예",
        "international": "국제",
        "": "기타"
      }
    };
  },
  computed: {
    filteredArticles() {
      if (Array.isArray(this.recentArticles) && this.recentArticles.length > 0) {
        const categoryToCompare = this.selectedCategory.toLowerCase().trim();  // 소문자, 공백 제거
        if (this.selectedCategory === '전체') {
          return this.recentArticles;
        } else {
          return this.recentArticles.filter(article =>
              article.category && article.category.name.toLowerCase().trim() === categoryToCompare
          );
        }
      }
      return [];  // 최근 게시글이 없다면 빈 배열 반환
    }
  },
  methods: {
    // 카테고리 클릭 시 호출
    filterCategory(category) {
      this.selectedCategory = category;
      this.fetchRecentPosts();  // 카테고리 변경 시 게시글을 다시 불러옴
    },

    // 검색 기능 (예시로 alert로 검색어 출력)
    search(query) {
      alert(`검색어: ${query}`);
    },

    async fetchBestTopics() {
      try {
        const res = await axios.get("http://localhost:8081/posts", {
          params: { page: 0, size: 100 }
        });

        const posts = res.data.content;

        const ratioPromises = posts.map(post =>
            axios.get(`http://localhost:8081/feedback/${post.id}/ratio`)
                .then(ratioRes => ({
                  ...post,
                  ratio: ratioRes.data
                }))
                .catch(() => null)
        );

        const postsWithRatio = (await Promise.all(ratioPromises)).filter(Boolean);

        const filtered = postsWithRatio.filter(p =>
            Math.abs(p.ratio.trueNewsRatio - 0.5) <= 0.05
        );

        filtered.sort((a, b) =>
            Math.abs(a.ratio.trueNewsRatio - 0.5) - Math.abs(b.ratio.trueNewsRatio - 0.5)
        );

        this.bestTopics = filtered.slice(0, 3); // 또는 10개 원하면 .slice(0, 10)
      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
      }
    },

    // 최근 게시글을 백엔드에서 불러오는 메소드
    async fetchRecentPosts() {
      if (this.selectedCategory === "전체"){
        try {
          const response = await axios.get("http://localhost:8081/posts", {
            params: {
              page: this.page, // 현재 페이지
              size: this.size, // 한 페이지에 표시할 게시글 수
            },
          });
          this.recentArticles = response.data.content; // 페이지네이션이 포함된 응답의 내용
        } catch (error) {
          console.error("게시글을 불러오는 데 실패했습니다.", error);
        }
      } else {
        try {
          // const encodedCategory = encodeURIComponent(this.selectedCategory);
          const url = `http://localhost:8081/posts/category/${this.selectedCategory}`;
          console.log("Request URL:", url); // 요청 URL 확인용 로그
          const response = await axios.get(url, {
            params: {
              page: this.page,
              size: this.size,
            },
          });
          console.log(response.data);
          this.recentArticles = response.data;
          console.log("Fetched articles:", this.recentArticles); // 데이터 확인용 로그
        } catch (error) {
          console.error("카테고리별 게시글을 불러오는 데 실패했습니다.", error);
        }
      }
    }
  },
  created() {
    this.fetchRecentPosts();  // 페이지가 로드되면 최근 게시글을 불러옴
    this.fetchBestTopics();
  }
};
</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: space-between;
}

.content {
  width:100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .main-container {
    flex-direction: column;
    padding: 0 16px;
  }
}
</style>