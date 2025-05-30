<template>
  <div class="vote-circle-container">
    <div class="vote-circle">
      <svg viewBox="0 0 36 36" class="vote-chart">
        <!-- 배경 원 -->
        <path class="vote-circle-bg"
              d="M18 2.0845
                a 15.9155 15.9155 0 0 1 0 31.831
                a 15.9155 15.9155 0 0 1 0 -31.831"
        />
        <!-- 찬성 부분 -->
        <path class="vote-circle-true"
              :style="{ stroke: trueColor }"
              :stroke-dasharray="`${(trueRatio || 0.5) * 100}, 100`"
              d="M18 2.0845
                a 15.9155 15.9155 0 0 1 0 31.831
                a 15.9155 15.9155 0 0 1 0 -31.831"
        />
        <!-- 반대 부분 -->
        <path class="vote-circle-false"
              :style="{ stroke: falseColor }"
              :stroke-dasharray="`${(1 - (trueRatio || 0.5)) * 100}, 100`"
              :stroke-dashoffset="-1 * (trueRatio || 0.5) * 100"
              d="M18 2.0845
                a 15.9155 15.9155 0 0 1 0 31.831
                a 15.9155 15.9155 0 0 1 0 -31.831"
        />
      </svg>
      <div class="vote-percentage">
        <div class="vote-vs">VS</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'VoteCircle',
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
    // 찬성이 우세한지 확인
    isTrueWinning() {
      return (this.trueRatio || 0.5) > 0.5;
    },
    // 찬성 부분 색상
    trueColor() {
      if (this.isTrueWinning) {
        return this.categoryColor; // 우세하면 카테고리 색상
      } else {
        return '#d1d5db'; // 열세면 밝은 회색
      }
    },
    // 반대 부분 색상
    falseColor() {
      if (!this.isTrueWinning) {
        return this.categoryColor; // 우세하면 카테고리 색상
      } else {
        return '#d1d5db'; // 열세면 밝은 회색
      }
    }
  }
}
</script>

<style scoped>
.vote-circle-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
}

.vote-circle {
  position: relative;
  width: 80px;
  height: 80px;
}

.vote-chart {
  width: 100%;
  height: 100%;
  transform: rotate(-90deg);
}

.vote-circle-bg {
  fill: none;
  stroke: #e2e8f0;
  stroke-width: 2.8;
}

.vote-circle-true {
  fill: none;
  stroke-width: 3.2;
  stroke-linecap: round;
  transition: stroke 0.3s ease;
}

.vote-circle-false {
  fill: none;
  stroke-width: 3.2;
  stroke-linecap: round;
  transition: stroke 0.3s ease;
}

.vote-percentage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.vote-vs {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  letter-spacing: -1px;
}
</style>