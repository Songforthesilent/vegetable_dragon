<template>
  <div class="vote-section">
    <div class="vote-section2">
      <h3 class="vote-title">이 뉴스에 대한 의견을 투표해주세요</h3>

      <div class="vote-button-container">
        <button
            @click="vote('agree')"
            :class="{ 'active': voteType === 'agree', 'vote-btn': true, 'agree-btn': true }"
        >
          <div class="vote-symbol">O</div>
          <div class="vote-text">진짜뉴스</div>
        </button>

        <button
            @click="vote('disagree')"
            :class="{ 'active': voteType === 'disagree', 'vote-btn': true, 'disagree-btn': true }"
        >
          <div class="vote-symbol">X</div>
          <div class="vote-text">가짜뉴스</div>
        </button>
      </div>
    </div>

    <!-- 구분선 추가 -->
    <div class="section-divider"></div>

    <div class="vote-result">
      <p class="result-title">투표 결과</p>

      <!-- 투표 수 표시 (양쪽 정렬 + SVG) -->
      <div class="vote-counts">
        <div class="count-item true-news">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="count-icon">
            <path d="M9 12l2 2 4-4"/>
            <circle cx="12" cy="12" r="10"/>
          </svg>
          <span class="count-label">진짜뉴스 {{ agreeVotes }}표</span>
        </div>
        <div class="count-item fake-news">
          <span class="count-label">가짜뉴스 {{ disagreeVotes }}표</span>
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="count-icon">
            <circle cx="12" cy="12" r="10"/>
            <line x1="15" y1="9" x2="9" y2="15"/>
            <line x1="9" y1="9" x2="15" y2="15"/>
          </svg>
        </div>
      </div>

      <!-- 진행 바 -->
      <div class="progress-container">
        <div class="progress-bar">
          <div class="progress-fill true-news" :style="{ width: getVotePercentage(agreeVotes) + '%' }"></div>
          <div class="progress-fill fake-news" :style="{ width: getVotePercentage(disagreeVotes) + '%' }"></div>
        </div>
      </div>

      <!-- 퍼센티지 표시 -->
      <div class="percentage-display">
        <span class="percentage true-news">{{ getVotePercentage(agreeVotes) }}%</span>
        <span class="percentage fake-news">{{ getVotePercentage(disagreeVotes) }}%</span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
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
    },
    postId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      agreeVotes: this.initialAgreeVotes,
      disagreeVotes: this.initialDisagreeVotes,
      voteType: this.initialVoteType,
    };
  },
  computed: {
    totalVotes() {
      return this.agreeVotes + this.disagreeVotes;
    }
  },
  methods: {
    async vote(type) {
      try {
        await this.submitVote(type);
        this.voteType = type;
        await this.fetchVoteRatio();
      } catch (error) {
        console.error('투표 실패:', error);
        const msg = error.response?.data?.message || "투표 실패";
        alert(msg);
      }
    },

    async submitVote(type) {
      return axios.post(
          `http://localhost:8081/feedback/${this.postId}`,
          { fakeNews: type === 'disagree' },
          { withCredentials: true }
      );
    },

    async fetchVoteRatio() {
      try {
        const response = await axios.get(
            `http://localhost:8081/feedback/${this.postId}/ratio`,
            { withCredentials: true }
        );

        this.agreeVotes = response.data.trueNewsCount;
        this.disagreeVotes = response.data.fakeNewsCount;
      } catch (error) {
        console.error('비율 가져오기 실패:', error);
        alert('투표 비율을 가져오는 데 실패했습니다.');
      }
    },

    getVotePercentage(voteCount) {
      if (this.totalVotes === 0) return 50;
      return ((voteCount / this.totalVotes) * 100).toFixed(1);
    }
  },

  mounted() {
    this.fetchVoteRatio();
  }
};
</script>

<style scoped>
.vote-section {
  padding: 10px;
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
  justify-content: center;
}

.vote-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px 30px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
  background-color: white;
  min-width: 140px;
}

.vote-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

.vote-symbol {
  font-size: 48px;
  font-weight: 900;
  margin-top: 20px;
  margin-bottom: 10px;
  font-family: 'Arial', sans-serif;
}

.vote-text {
  font-weight: 600;
  font-size: 14px;
  color: #64748b;
}

.agree-btn .vote-symbol {
  color: #3662E3;
}

.disagree-btn .vote-symbol {
  color: #ea4335;
}

.agree-btn.active {
  border-color: #3662E3;
  background: linear-gradient(135deg, #eff6ff, #dbeafe);
}

.agree-btn.active .vote-text {
  color: #3662E3;
  font-weight: 700;
}

.disagree-btn.active {
  border-color: #ea4335;
  background: linear-gradient(135deg, #fef2f2, #fee2e2);
}

.disagree-btn.active .vote-text {
  color: #ea4335;
  font-weight: 700;
}

.vote-result {
  margin-top: 30px;
}

.result-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

/* 투표 수 표시 - 양쪽 정렬 */
.vote-counts {
  margin-bottom: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.count-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.count-item.true-news {
  justify-content: flex-start;
}

.count-item.fake-news {
  justify-content: flex-end;
  flex-direction: row-reverse;
}

.count-label {
  font-size: 14px;
  font-weight: 600;
}

.count-icon {
  width: 16px;
  height: 16px;
  flex-shrink: 0;
}

.count-item.true-news .count-label {
  color: #3662E3;
}

.count-item.true-news .count-icon {
  color: #3662E3;
}

.count-item.fake-news .count-label {
  color: #ea4335;
}

.count-item.fake-news .count-icon {
  color: #ea4335;
}

/* 진행 바 컨테이너 */
.progress-container {
  margin: 16px 0;
}

.progress-bar {
  height: 20px;
  background-color: #f1f3f4;
  border-radius: 12px;
  overflow: hidden;
  display: flex;
  position: relative;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
}

.progress-fill {
  height: 100%;
  transition: width 0.6s ease-in-out;
  position: relative;
}

.progress-fill.true-news {
  background: linear-gradient(90deg, #3662E3 0%, #3A4CA4 100%);
  border-radius: 12px 0 0 12px;
}

.progress-fill.fake-news {
  background: linear-gradient(90deg, #ff6b6b 0%, #ea4335 100%);
  border-radius: 0 12px 12px 0;
}

/* 퍼센티지 표시 */
.percentage-display {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12px;
}

.percentage {
  font-size: 16px;
  font-weight: 700;
  padding: 4px 8px;
  border-radius: 6px;
  background-color: rgba(255, 255, 255, 0.9);
}

.percentage.true-news {
  color: #3662E3;
  border: none;
}

.percentage.fake-news {
  color: #ea4335;
  border: none;
}

/* 애니메이션 효과 */
@keyframes slideIn {
  from {
    width: 0%;
  }
  to {
    width: var(--target-width);
  }
}

.progress-fill {
  animation: slideIn 0.8s ease-out;
}

/* 호버 효과 */
.progress-bar:hover .progress-fill {
  filter: brightness(1.1);
}

.percentage:hover {
  transform: scale(1.05);
  transition: transform 0.2s ease;
}

.count-item:hover .count-icon {
  transform: scale(1.1);
  transition: transform 0.2s ease;
}

@media (max-width: 768px) {
  .vote-button-container {
    gap: 15px;
  }

  .vote-btn {
    padding: 15px 20px;
    min-width: 100px;
  }

  .vote-symbol {
    font-size: 36px;
  }

  .vote-text {
    font-size: 12px;
  }

  .count-label {
    font-size: 13px;
  }

  .count-icon {
    width: 14px;
    height: 14px;
  }

  .progress-bar {
    height: 20px;
    border-radius: 10px;
  }

  .progress-fill.true-news {
    border-radius: 10px 0 0 10px;
  }

  .progress-fill.fake-news {
    border-radius: 0 10px 10px 0;
  }

  .percentage {
    font-size: 14px;
    padding: 3px 6px;
  }
}

@media (max-width: 480px) {
  .vote-button-container {
    flex-direction: column;
    align-items: center;
    gap: 10px;
  }

  .vote-btn {
    width: 100%;
    max-width: 200px;
  }

  .vote-symbol {
    font-size: 32px;
  }

  .vote-counts {
    flex-direction: column;
    gap: 8px;
    align-items: stretch;
  }

  .count-item.fake-news {
    flex-direction: row;
    justify-content: flex-start;
  }

  .count-label {
    font-size: 12px;
  }

  .count-icon {
    width: 12px;
    height: 12px;
  }

  .progress-bar {
    height: 18px;
    border-radius: 9px;
  }

  .progress-fill.true-news {
    border-radius: 9px 0 0 9px;
  }

  .progress-fill.fake-news {
    border-radius: 0 9px 9px 0;
  }

  .percentage {
    font-size: 13px;
    padding: 2px 5px;
  }
}

/* 섹션 구분선 */
.section-divider {
  height: 1px;
  background: linear-gradient(90deg, transparent 0%, #e2e8f0 20%, #cbd5e1 50%, #e2e8f0 80%, transparent 100%);
  margin: 20px 0;
  position: relative;
}

.section-divider::before {
  content: '';
  position: absolute;
  top: -2px;
  left: 50%;
  transform: translateX(-50%);
  width: 40px;
  height: 5px;
  background: linear-gradient(90deg, #3662E3, #ea4335);
  border-radius: 3px;
  opacity: 0.6;
}
</style>