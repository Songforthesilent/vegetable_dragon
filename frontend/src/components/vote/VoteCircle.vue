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
        <!-- 찬성 부분 (파란색) -->
        <path class="vote-circle-true"
              :stroke-dasharray="`${(trueRatio || 0.5) * 100}, 100`"
              d="M18 2.0845
                a 15.9155 15.9155 0 0 1 0 31.831
                a 15.9155 15.9155 0 0 1 0 -31.831"
        />
        <!-- 반대 부분 (빨간색) -->
        <path class="vote-circle-false"
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
  stroke-width: 3.8;
}

.vote-circle-true {
  fill: none;
  stroke: #3A4CA4;
  stroke-width: 3.8;
  stroke-linecap: round;
}

.vote-circle-false {
  fill: none;
  stroke: #ef4444;
  stroke-width: 3.8;
  stroke-linecap: round;
}

.vote-percentage {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.vote-vs {
  font-size: 16px;
  font-weight: 800;
  color: #1e293b;
  background: linear-gradient(135deg, #3A4CA4, #ef4444);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  letter-spacing: -1px;
}
</style>
