<template>
  <div class="vote-section">
    <h3 class="vote-title">이 뉴스에 대한 의견을 투표해주세요</h3>

    <div class="vote-button-container">
      <button
          @click="vote('agree')"
          :class="{ 'active-agree': voteType === 'agree', 'vote-btn': true, 'agree-btn': true }"
      >
        <div class="vote-icon">👍</div>
        <div class="vote-text">진짜뉴스이다</div>
        <div class="vote-percent">{{ getVotePercentage(agreeVotes) }}%</div>
      </button>

      <!-- VS element -->
      <div class="vote-vs">
        <div class="vs-circle">VS</div>
        <div class="vs-line"></div>
      </div>

      <button
          @click="vote('disagree')"
          :class="{ 'active-disagree': voteType === 'disagree', 'vote-btn': true, 'disagree-btn': true }"
      >
        <div class="vote-icon">👎</div>
        <div class="vote-text">가짜뉴스이다</div>
        <div class="vote-percent">{{ getVotePercentage(disagreeVotes) }}%</div>
      </button>
    </div>

    <div class="vote-result">
      <p>투표 결과</p>
      <!-- 투표 퍼센트 바 (클릭 가능) -->
      <div class="progress-container">
        <div class="progress-bar" @click="handleVote">
          <div
              class="agree-bar"
              :style="{ width: getVotePercentage(agreeVotes) + '%' }"
              data-vote="agree"
          >
            <span v-if="agreeVotes > 0" class="progress-text">
              {{ getVotePercentage(agreeVotes) }}%
            </span>
          </div>

          <div
              class="disagree-bar"
              :style="{ width: getVotePercentage(disagreeVotes) + '%' }"
              data-vote="disagree"
          >
            <span v-if="disagreeVotes > 0" class="progress-text">
              {{ getVotePercentage(disagreeVotes) }}%
            </span>
          </div>
        </div>
        <div class="vote-labels">
          <span class="agree-label">진짜뉴스 {{ agreeVotes }}표</span>
          <span class="disagree-label">가짜뉴스 {{ disagreeVotes }}표</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    initialAgreeVotes: {
      type: Number,
      default: 0
    },
    initialDisagreeVotes: {
      type: Number,
      default: 0
    },
    initialVoteType: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      agreeVotes: this.initialAgreeVotes,
      disagreeVotes: this.initialDisagreeVotes,
      voteType: this.initialVoteType
    };
  },
  computed: {
    totalVotes() {
      return this.agreeVotes + this.disagreeVotes;
    }
  },
  methods: {
    vote(type) {
      if (this.voteType === type) return;

      // 이전 투표 취소
      if (this.voteType === 'agree') {
        this.agreeVotes--;
      } else if (this.voteType === 'disagree') {
        this.disagreeVotes--;
      }

      // 새 투표 추가
      if (type === 'agree') {
        this.agreeVotes++;
      } else {
        this.disagreeVotes++;
      }

      this.voteType = type;
      this.$emit('vote', { type, agreeVotes: this.agreeVotes, disagreeVotes: this.disagreeVotes });
    },
    handleVote(event) {
      const voteType = event.target.getAttribute('data-vote');
      if (voteType) {
        this.vote(voteType);
      }
    },
    getVotePercentage(voteCount) {
      if (this.totalVotes === 0) return 50;
      return ((voteCount / this.totalVotes) * 100).toFixed(1);
    }
  }
};
</script>

<style scoped>
.vote-section {
  display: flex;
  flex-direction: column;
  padding: 30px;
  border-radius: 16px;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
  margin-top: 20px;
  background-color: #f9f9fc;
  border: 1px solid #eaeaea;
}

.vote-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 25px;
  text-align: center;
  color: #333;
}

.vote-button-container {
  display: flex;
  gap: 20px;
  width: 100%;
  margin-bottom: 30px;
}

.vote-btn {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px 15px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;
  background-color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
}

.agree-btn {
  color: #3A4CA4;
}

.disagree-btn {
  color: #FF4C4C;
}

.vote-btn:hover {
  transform: translateY(-3px);
}

.vote-icon {
  font-size: 28px;
  margin-bottom: 10px;
}

.vote-text {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 8px;
}

.vote-percent {
  font-size: 20px;
  font-weight: 700;
}

.active-agree {
  border-color: #3A4CA4;
  background-color: rgba(58, 76, 164, 0.05);
}

.active-disagree {
  border-color: #FF4C4C;
  background-color: rgba(255, 76, 76, 0.05);
}

.vote-result {
  margin-top: 10px;
}

.vote-result p {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 15px;
  color: #555;
}

.progress-container {
  position: relative;
}

.progress-bar {
  display: flex;
  width: 100%;
  height: 40px;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
  background-color: #f0f0f0;
  position: relative;
}

.agree-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: linear-gradient(to right, #4a5fc1, #3A4CA4);
  position: relative;
  min-width: 40px;
  transition: width 0.5s ease;
}

.disagree-bar {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: linear-gradient(to right, #ff6b6b, #FF4C4C);
  position: relative;
  min-width: 40px;
  transition: width 0.5s ease;
}

.progress-text {
  color: white;
  font-weight: bold;
  font-size: 16px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.vote-labels {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  font-size: 14px;
}

.agree-label {
  color: #3A4CA4;
  font-weight: 500;
}

.disagree-label {
  color: #FF4C4C;
  font-weight: 500;
}

/* VS 요소 스타일 */
.vote-vs {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 2;
}

.vs-circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: linear-gradient(135deg, #3A4CA4, #FF4C4C);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 800;
  font-size: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 2;
  letter-spacing: -1px;
}

.vs-line {
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: #ddd;
  z-index: 1;
}

@media (max-width: 768px) {
  .vote-button-container {
    flex-direction: column;
    gap: 15px;
    position: relative;
  }

  .vote-vs {
    margin: 5px 0;
  }

  .vs-line {
    width: 2px;
    height: 100%;
    top: 0;
    left: 50%;
    transform: translateX(-50%);
  }

  .vote-btn {
    padding: 15px;
  }
}
</style>
