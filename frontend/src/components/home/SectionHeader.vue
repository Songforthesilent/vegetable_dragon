<template>
  <div class="section-header">
    <h2 class="section-title">{{ title }}</h2>
    <router-link :to="moreLink" class="more-link">
      더보기
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <polyline points="9 18 15 12 9 6"></polyline>
      </svg>
    </router-link>
  </div>
</template>

<script>
export default {
  name: 'SectionHeader',
  props: {
    title: {
      type: String,
      required: true
    },
    moreLink: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped>
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.section-title {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
  position: relative;
  padding-left: 14px;
}

.section-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 20px;
  background-color: #3A4CA4;
  border-radius: 2px;
}

.more-link {
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 500;
  color: #3A4CA4;
  text-decoration: none;
  transition: color 0.2s ease;
}

.more-link:hover {
  color: #2d3a7c;
}

.more-link svg {
  margin-left: 4px;
  transition: transform 0.2s ease;
}

.more-link:hover svg {
  transform: translateX(3px);
}
</style>
