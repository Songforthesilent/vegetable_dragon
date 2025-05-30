<template>
  <div class="vote-labels">
    <div class="vote-label true" :style="{ color: trueColor }">
      <span class="vote-percent">진짜뉴스 {{ truePercent }}%</span>
    </div>
    <div class="vote-label false" :style="{ color: falseColor }">
      <span class="vote-percent">가짜뉴스 {{ falsePercent }}%</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VoteLabels',
  props: {
    trueRatio: {
      type: Number,
      default: 0.5
    },
    categoryColor: {
      type: String,
      default: '#3A4CA4'
    }
  },
  computed: {
    truePercent() {
      return Math.round((this.trueRatio || 0.5) * 100);
    },
    falsePercent() {
      return Math.round((1 - (this.trueRatio || 0.5)) * 100);
    },
    // 찬성이 우세한지 확인
    isTrueWinning() {
      return (this.trueRatio || 0.5) > 0.5;
    },
    // 진짜뉴스 색상
    trueColor() {
      if (this.isTrueWinning) {
        return this.categoryColor; // 우세하면 카테고리 색상
      } else {
        return '#94a3b8'; // 열세면 밝은 회색
      }
    },
    // 가짜뉴스 색상
    falseColor() {
      if (!this.isTrueWinning) {
        return this.categoryColor; // 우세하면 카테고리 색상
      } else {
        return '#94a3b8'; // 열세면 밝은 회색
      }
    }
  }
}
</script>

<style scoped>
.vote-labels {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-top: 8px;
}

.vote-label {
  display: flex;
  justify-content: center;
  width: 45%;
  transition: color 0.3s ease;
}

.vote-percent {
  font-size: 13px;
  font-weight: 700;
}

/* 반응형 */
@media (max-width: 768px) {
  .vote-percent {
    font-size: 12px;
  }
}
</style>