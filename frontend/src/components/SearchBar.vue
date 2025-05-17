<template>
  <section class="search-section">
    <div class="search-container">
      <input
          v-model="searchQuery"
          type="text"
          placeholder="검색어를 입력하세요"
          class="search-input"
          @keyup.enter="handleSearch"
      />
      <button @click="handleSearch" class="search-button">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="11" cy="11" r="8"></circle>
          <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
        </svg>
      </button>
    </div>
  </section>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: ''
    };
  },
  methods: {
    handleSearch() {
      this.$emit('search', this.searchQuery);
    }
  }
};
</script>

<style scoped>
.search-section {
  width: 100%;
  margin-bottom: 20px;
}

.search-container {
  position: relative;
  width: 100%;
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
</style>