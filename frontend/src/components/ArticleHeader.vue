<template>
  <div class="author-info">
    <div class="author-profile">
      <img src="https://via.placeholder.com/50" alt="Profile" class="profile-img" />
      <div>
        <h4 class="author-name">{{ author }}</h4>
        <p class="date">{{ formattedDate }}</p>
      </div>
    </div>

    <!-- 더보기 버튼 -->
    <div class="more-options">
      <button @click="toggleMoreOptions" class="more-button">
        <span class="more-icon">⋮</span>
      </button>
      <div v-if="showOptions" class="options-dropdown">
        <button @click="$emit('edit')" class="option-btn">
          <span class="option-icon">✏️</span> 수정
        </button>
        <button @click="$emit('delete')" class="option-btn">
          <span class="option-icon">🗑️</span> 삭제
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    author: {
      type: String,
      required: true
    },
    date: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      showOptions: false
    };
  },
  computed: {
    formattedDate() {
      if (!this.date) return '';

      try {
        const date = new Date(this.date);
        if (isNaN(date.getTime())) return this.date;

        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
      } catch (e) {
        console.error('날짜 형식 변환 오류:', e);
        return this.date;
      }
    }
  },
  methods: {
    toggleMoreOptions() {
      this.showOptions = !this.showOptions;
    }
  }
};
</script>

<style scoped>
.author-info {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.author-profile {
  display: flex;
  align-items: center;
}

.profile-img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 10px;
  object-fit: cover;
  border: 1px solid #eee;
}

.author-name {
  color: #3A4CA4;
  font-size: 16px;
  font-weight: bold;
  text-align: left;
  margin: 0;
}

.date {
  color: #999;
  font-size: 14px;
  margin: 4px 0 0 0;
}

/* 더보기 버튼 스타일 */
.more-options {
  position: relative;
}

.more-button {
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #666;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.2s;
}

.more-button:hover {
  background-color: #f5f5f5;
}

.more-icon {
  line-height: 1;
}

.options-dropdown {
  position: absolute;
  right: 0;
  top: 100%;
  background: white;
  border-radius: 8px;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.2);
  width: 120px;
  z-index: 10;
  overflow: hidden;
}

.option-btn {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 12px 15px;
  text-align: left;
  background: none;
  border: none;
  cursor: pointer;
  transition: background-color 0.2s;
  font-size: 14px;
}

.option-btn:hover {
  background-color: #f5f5f5;
}

.option-icon {
  margin-right: 8px;
  font-size: 16px;
}
</style>
