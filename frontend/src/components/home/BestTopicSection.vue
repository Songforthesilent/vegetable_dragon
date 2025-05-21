<template>
  <section class="article-section">
    <section-header title="Best Topics" more-link="/best-topics" />

    <div class="best-topics-container">
      <topic-card
          v-for="article in bestTopics"
          :key="article.id"
          :article="article"
      />
    </div>
  </section>
</template>

<script>
import axios from 'axios';
import SectionHeader from '@/components/home/SectionHeader.vue';
import TopicCard from '@/components/topic/TopicCard.vue';

export default {
  name: 'BestTopicsSection',
  components: {
    SectionHeader,
    TopicCard
  },
  data() {
    return {
      bestTopics: [],
      loading: false,
      error: null
    };
  },
  created() {
    this.fetchBestTopics();
  },
  methods: {
    async fetchBestTopics() {
      this.loading = true;
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

        this.bestTopics = filtered.slice(0, 3);
        this.loading = false;
      } catch (error) {
        console.error("Best Topics 불러오기 실패", error);
        this.error = error;
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.article-section {
  width: 100%;
  padding: 0px;
  margin: 20px 0 30px 0;
  background-color: #ffffff;
}

.best-topics-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

@media (max-width: 1024px) {
  .best-topics-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .best-topics-container {
    grid-template-columns: 1fr;
  }
}
</style>
