<template>
  <section class="post-content">
    <div class="post-header">
      <img class="profile-img" :src="profileImage" alt="Profile" />
      <div class="author-info">
        <div class="author-name">{{ author || '익명' }}</div>
        <div class="post-date">{{ formattedDate }}</div>
      </div>
    </div>
    <div class="post-body">
      <h2 class="post-title">{{ title }}</h2>
      <p class="post-text" v-html="content"></p>
      <a
          v-if="articleLink"
          :href="articleLink"
          class="post-link"
          target="_blank"
          rel="noopener noreferrer"
      >
        📰 관련 기사 보기
      </a>
    </div>
  </section>
</template>

<script>
export default {
  props: {
    author: {
      type: String,
      default: ''
    },
    date: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      required: true
    },
    content: {
      type: String,
      required: true
    },
    articleLink: {
      type: String,
      default: ''
    },
    profileImage: {
      type: String,
      default: '/default-profile.png'
    }
  },
  computed: {
    formattedDate() {
      if (!this.date) return '';
      const dateObj = new Date(this.date);
      return dateObj.toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    }
  }
};
</script>

<style scoped>
.post-content {
  background-color: #ffffff;
  border-radius: 15px;
  padding: 25px;
  margin-bottom: 30px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.post-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 15px;
  background-color: #e0e0e0;
}

.author-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.author-name {
  font-weight: bold;
  font-size: 18px;
  color: #333;
}

.post-date {
  font-size: 13px;
  color: #888;
  margin-top: 4px;
}

.post-body {
  margin-top: 10px;
}

.post-title {
  font-size: 24px;
  font-weight: bold;
  color: #222;
  margin-bottom: 15px;
}

.post-text {
  font-size: 16px;
  line-height: 1.7;
  color: #444;
  margin-bottom: 20px;
}

.post-link {
  display: inline-block;
  margin-top: 10px;
  color: #3A4CA4;
  font-weight: bold;
  text-decoration: none;
}

.post-link:hover {
  text-decoration: underline;
}
</style>
