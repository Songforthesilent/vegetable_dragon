<template>
  <div class="ai-analysis-section">
    <div class="ai-header">
      <h3 class="ai-title">AI 분석 결과</h3>
      <div class="ai-status" :class="{ 'loading': isAnalyzing }">
        {{ isAnalyzing ? '분석 중...' : '분석 완료' }}
      </div>
    </div>

    <div v-if="isAnalyzing" class="ai-loading">
      <div class="ai-loading-animation">
        <div class="dot"></div>
        <div class="dot"></div>
        <div class="dot"></div>
      </div>
      <p>기사 내용을 분석하고 있습니다...</p>
    </div>

    <div v-else class="ai-result">
      <div class="ai-score-container">
        <div class="ai-score-circle" :style="{ background: getScoreGradient() }">
          <span class="ai-score-value">{{ aiScore }}%</span>
        </div>
        <div class="ai-score-label">
          진실성 점수
        </div>
      </div>
    </div>

    <div class="ai-actions">
        <span class="ai-action-icon">※ 모델은 참고용입니다.</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  props: {
    postId: {
      type: Number,
      required: true
    },
    articleContent: {
      type: String,
      required: true
    },
    articleTitle: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      isAnalyzing: true,
      aiScore: 0,
      analysisTimeout: null
    };
  },
  mounted() {
    // 실제로는 API 호출을 통해 AI 분석을 수행하겠지만,
    // 여기서는 시뮬레이션을 위해 setTimeout 사용
    this.fetchPrediction();
    // this.analyzeArticle();
  },
  methods: {
    async fetchPrediction() {
      this.isAnalyzing = true;
      try {
        const response = await axios.get(`http://localhost:8081/posts/${this.postId}`);
        const prediction = response.data.prediction;

        if (prediction == null) {
          this.aiScore = 0;
        } else {
          // 예: 0.0~1.0 → 퍼센트 변환
          this.aiScore = Math.round(prediction );
        }
      } catch (e) {
        console.error("AI 예측 점수 불러오기 실패:", e);
        this.aiScore = 0;
      } finally {
        this.isAnalyzing = false;
      }
    },
    analyzeArticle() {
      this.isAnalyzing = true;

      // 실제 구현에서는 이 부분을 API 호출로 대체
      this.analysisTimeout = setTimeout(() => {
        // 분석 결과 시뮬레이션 (실제로는 API 응답에서 가져옴)
        this.aiScore = this.generateRandomScore();
        this.isAnalyzing = false;
      }, 1500); // 1.5초 후 분석 완료 (시뮬레이션)
    },
    reanalyze() {
      // this.analyzeArticle();
      this.fetchPrediction();
    },
    getScoreGradient() {
      if (this.aiScore >= 80) {
        return 'linear-gradient(135deg, #4ade80, #22c55e)';
      } else if (this.aiScore >= 60) {
        return 'linear-gradient(135deg, #facc15, #eab308)';
      } else {
        return 'linear-gradient(135deg, #f87171, #ef4444)';
      }
    },
    generateRandomScore() {
      // 실제로는 AI 모델의 분석 결과에 따라 점수가 결정됨
      return Math.floor(Math.random() * 100);
    }
  }
};
</script>

<style scoped>
.ai-analysis-section {
  padding: 20px;
}

.ai-header {
  display: flex;
  margin-bottom: 10px;
}

.ai-title {
  font-size: 18px;
  font-weight: 700;
  text-align: left;
  color: #1e293b;
  margin: 0;
  flex-grow: 1;
}

.ai-status {
  font-size: 14px;
  padding: 4px 10px;
  border-radius: 20px;
  background-color: #e0f2fe;
  color: #0369a1;
}

.ai-status.loading {
  background-color: #fef9c3;
  color: #854d0e;
}

.ai-loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px 0;
}

.ai-loading-animation {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
}

.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: #6366f1;
  animation: bounce 1.4s infinite ease-in-out both;
}

.dot:nth-child(1) {
  animation-delay: -0.32s;
}

.dot:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes bounce {
  0%, 80%, 100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

.ai-loading p {
  color: #64748b;
  font-size: 15px;
}

.ai-result {
  display: flex;
  justify-content: center;
  padding: 10px 0;
}

.ai-score-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 10px;
}

.ai-score-circle {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.ai-score-value {
  font-size: 28px;
  font-weight: 800;
  color: white;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.ai-score-label {
  font-size: 15px;
  font-weight: 600;
  color: #475569;
}

.ai-actions {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.ai-action-icon {
  margin-right: 8px;
  font-size: 14px;
}

@media (max-width: 768px) {
  .ai-analysis-section {
    padding: 15px;
  }

  .ai-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .ai-status {
    align-self: flex-start;
  }

  .ai-score-circle {
    width: 80px;
    height: 80px;
  }

  .ai-score-value {
    font-size: 24px;
  }
}
</style>