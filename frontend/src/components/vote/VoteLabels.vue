<template>
  <div class="vote-labels">
    <div class="vote-label true">
      <span class="vote-percent">{{ truePercent }}%</span>
    </div>
    <div class="vote-label false">
      <span class="vote-percent">{{ falsePercent }}%</span>
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
    }
  },
  computed: {
    truePercent() {
      return Math.round((this.trueRatio || 0.5) * 100);
    },
    falsePercent() {
      return Math.round((1 - (this.trueRatio || 0.5)) * 100);
    }
  }
}
</script>

<style scoped>
.vote-labels {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.vote-label {
  display: flex;
  justify-content: center;
  width: 45%;
}

.vote-label.true {
  color: #3A4CA4;
}

.vote-label.false {
  color: #ef4444;
}

.vote-percent {
  font-size: 14px;
  font-weight: 700;
}
</style>
