<template>
  <section class="hero-section">
    <div class="hero-container">
      <!-- 왼쪽 콘텐츠 -->
      <div class="hero-content">
        <!-- 새로운 플랫폼 배지 -->
        <div class="platform-badge">
          <span class="badge-icon">🎉</span>
          새로운 토론 플랫폼 오픈!
        </div>

        <!-- 메인 헤드라인 -->
        <h1 class="hero-title">
          AI 생성형 콘텐츠에 대해<br>
          <span class="highlight-text">의견을 교환하는</span><br>
          토론의 공간
        </h1>

        <!-- 설명 텍스트 -->
        <p class="hero-description">
          생성형 AI 기술의 발전으로 개별 콘텐츠의 진위를 판단하기 어려운 상황 속 집단지성을<br>
          이용하여 성장하는 커뮤니티입니다.
        </p>

        <!-- 액션 버튼들 -->
        <div class="hero-actions">
          <button class="primary-button" @click="startDiscussion">
            토론 시작하기
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
            </svg>
          </button>
        </div>
      </div>

      <!-- 오른쪽 통계 카드들 -->
      <div class="stats-section">
        <!-- 찬성률 카드 -->
        <div class="stat-card main-stat floating-card">
          <div class="stat-header">
            <span class="stat-label">사회</span>
            <span class="stat-time">실시간</span>
          </div>
          <div class="stat-title">기후변화 대응 정책</div>
          <div class="circular-progress">
            <svg viewBox="0 0 100 100" class="progress-ring">
              <circle cx="50" cy="50" r="45" class="progress-ring-bg"/>
              <circle cx="50" cy="50" r="45" class="progress-ring-fill" :style="{ strokeDasharray: `${70 * 2.83} 283` }"/>
            </svg>
            <div class="progress-text">70%</div>
          </div>
          <div class="stat-footer">
            <span class="stat-result">진짜뉴스 70%</span>
            <span class="stat-participants">가짜뉴스 30%</span>
          </div>
        </div>

        <!-- 비교 카드들 -->
        <div class="comparison-cards">
          <div class="comparison-card floating-card floating-delay-1">
            <div class="comparison-header">
              <span class="comparison-label purple">기술</span>
              <span class="comparison-time">2시간 전</span>
            </div>
            <div class="comparison-title">AI 윤리</div>
            <div class="comparison-stats">
              <span class="stat-result2">진짜뉴스 65%</span>
              <span class="stat-participants">      VS      가짜뉴스 35%</span>
            </div>
          </div>

          <div class="comparison-card floating-card floating-delay-2">
            <div class="comparison-header">
              <span class="comparison-label orange">사회</span>
              <span class="comparison-time">1시간 전</span>
            </div>
            <div class="comparison-title">교육 정책</div>
            <div class="comparison-stats">
              <span class="stat-participants">진짜뉴스 45%      VS      </span>
              <span class="stat-result3">가짜뉴스 55%</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'HeroSection',
  methods: {
    startDiscussion() {
      this.$router.push('/board/write');
    },
    viewPopularTopics() {
      this.$router.push('/best-topics');
    }
  }
};
</script>

<style scoped>
/* 히어로 섹션 */
.hero-section {
  padding: 80px 0 120px;
  background: linear-gradient(135deg, #f8fafc 0%, #F9F6FE 100%);
  overflow: hidden;
}

.hero-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: grid;
  grid-template-columns: 1fr 400px;
  gap: 80px;
  align-items: center;
}

/* 히어로 콘텐츠 */
.hero-content {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.platform-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 3px 16px;
  background-color: rgba(54, 98, 227, 0.1);
  color: #3662E3;
  border-radius: 20px;
  font-size: 14px;
  font-weight: 500;
  width: fit-content;
  border: 1px solid rgba(54, 98, 227, 0.2);
}

.badge-icon {
  font-size: 12px;
}

.hero-title {
  font-size: 45px;
  font-weight: 700;
  line-height: 1.2;
  color: #1e293b;
  margin: 0;
  text-align: left;
}

.highlight-text {
  background: linear-gradient(135deg, #2952CC 0%, #3662E3 50%, #7D3DE1 50%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  color: transparent;
  font-weight: 600;
}

.hero-description {
  font-size: 18px;
  line-height: 1.6;
  color: #64748b;
  margin: 0;
  text-align: left;
}

.hero-actions {
  display: flex;
  gap: 16px;
  margin-top: 16px;
}

.primary-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 26px;
  background-color: #3662E3;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
}

.primary-button:hover {
  background-color: #2952CC;
  transform: translateY(-2px);
}

/* 통계 섹션 */
.stats-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 플로팅 카드 기본 스타일 */
.floating-card {
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  cursor: pointer;
  position: relative;
}

.floating-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-radius: inherit;
  background: linear-gradient(135deg, rgba(54, 98, 227, 0.1), rgba(125, 61, 225, 0.1));
  opacity: 0;
  transition: opacity 0.3s ease;
  z-index: -1;
}

/* 플로팅 애니메이션 지연 */
.floating-delay-1 {
  animation: float 6s ease-in-out infinite;
  animation-delay: -2s;
}

.floating-delay-2 {
  animation: float 6s ease-in-out infinite;
  animation-delay: -4s;
}

/* 메인 통계 카드 플로팅 */
.main-stat.floating-card {
  animation: float 6s ease-in-out infinite;
}

/* 플로팅 키프레임 애니메이션 */
@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-8px);
  }
}

/* 글로우 효과 */
@keyframes glow {
  0%, 100% {
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  }
  50% {
    box-shadow: 0 8px 30px rgba(54, 98, 227, 0.15);
  }
}

.stat-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  border: 1px solid #f1f5f9;
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
}

.main-stat {
  text-align: center;
  animation: glow 4s ease-in-out infinite;
}

/* 메인 통계 카드 플로팅 */
.main-stat.floating-card {
  animation: floatRotateMain 9s ease-in-out infinite;
  transform: rotate(-1.8deg);
}

.stat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.stat-label {
  background-color: #dcfce7;
  color: #166534;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.stat-time {
  font-size: 12px;
  color: #94a3b8;
}

.stat-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 24px;
  transition: color 0.3s ease;
}

.circular-progress {
  position: relative;
  width: 120px;
  height: 120px;
  margin: 0 auto 24px;
  transition: transform 0.3s ease;
}

.progress-ring {
  width: 100%;
  height: 100%;
  transform: rotate(-90deg);
  filter: drop-shadow(0 2px 4px rgba(0, 0, 0, 0.1));
}

.progress-ring-bg {
  fill: none;
  stroke: #f1f5f9;
  stroke-width: 8;
}

.progress-ring-fill {
  fill: none;
  stroke: #22c55e;
  stroke-width: 8;
  stroke-linecap: round;
  transition: all 0.5s ease;
}

.progress-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 24px;
  font-weight: 700;
  color: #1e293b;
  transition: all 0.3s ease;
}

.stat-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
}

.stat-result {
  color: #22c55e;
  font-weight: 600;
  transition: all 0.3s ease;
}

.stat-participants {
  color: #64748b;
}

/* 비교 카드들 */
.comparison-cards {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.comparison-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  border: 1px solid #f1f5f9;
  backdrop-filter: blur(10px);
}

.comparison-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.comparison-label {
  padding: 2px 8px;
  border-radius: 8px;
  font-size: 11px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.comparison-label.purple {
  background-color: #ede9fe;
  color: #7c3aed;
}

.comparison-label.orange {
  background-color: #fed7aa;
  color: #ea580c;
}

.comparison-time {
  font-size: 11px;
  color: #94a3b8;
}

.comparison-title {
  font-size: 14px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 8px;
  transition: color 0.3s ease;
}

.comparison-stats {
  font-size: 12px;
  color: #64748b;
  justify-content: space-between;
}

.stat-result2 {
  color: #7c3aed;
  font-weight: 600;
  font-size: 12px;
  transition: all 0.3s ease;
}

.stat-result3 {
  color: #ea580c;
  font-weight: 600;
  transition: all 0.3s ease;
}

/* 반응형 디자인 */
@media (max-width: 1024px) {
  .hero-container {
    grid-template-columns: 1fr;
    gap: 60px;
    text-align: center;
  }

  .stats-section {
    max-width: 400px;
    margin: 0 auto;
  }
}

@media (max-width: 768px) {
  .hero-section {
    padding: 60px 0 80px;
  }

  .hero-title {
    font-size: 36px;
  }

  .hero-actions {
    flex-direction: column;
    align-items: center;
  }

  .primary-button,
  .secondary-button {
    width: 100%;
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .hero-container {
    padding: 0 16px;
  }

  .hero-title {
    font-size: 28px;
  }

  .hero-description {
    font-size: 16px;
  }

  /* 모바일에서는 애니메이션 비활성화 */
  .floating-card {
    animation: none;
  }
}

/* 접근성을 위한 모션 감소 설정 */
@media (prefers-reduced-motion: reduce) {
  .floating-card,
  .main-stat,
  .floating-delay-1,
  .floating-delay-2 {
    animation: none;
  }
}
</style>