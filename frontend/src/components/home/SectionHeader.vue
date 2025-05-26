<template>
  <div class="section-header">
    <h2 class="section-title">{{ title }}</h2>

  </div>
  <div class="section-subtitle">
    <p v-if="subtitle">{{ subtitle }}</p>
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
    subtitle: {
      type: String,
      default: ''
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
  font-size: 25px;
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
  background-color: #3662E3;
  border-radius: 2px;
}

.section-subtitle {
  font-size: 15px;
  color: #64748b;
  margin-bottom: 10px;
  font-weight: 400;
  line-height: 1.4;
  text-align: left;
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
