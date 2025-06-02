/**
 * 게시글 제목과 내용을 기반으로 카테고리를 자동 분류하는 유틸리티
 */

// 카테고리별 키워드 가중치 시스템
const CATEGORY_KEYWORDS = {
    '경제': {
        high: ['경제', '금융', '투자', '주식', '증시', '코스피', '코스닥', '환율', '금리', '인플레이션', '경기', '부동산', '재정', '예산', '세금', '기업', '매출', '수익', '손실', '파산', '상장', '채권', '펀드', 'GDP', '물가'],
        medium: ['급락', '급등', '상승', '하락', '거래', '시장', '산업', '회사', '사업', '경영', '매출', '이익', '손해', '자본', '자금', '대출', '신용', '보험', '은행', '증권'],
        low: ['돈', '가격', '비용', '수입', '지출', '소득', '임금', '급여', '연봉', '보너스', '할인', '세일']
    },
    '정치': {
        high: ['정치', '정부', '국회', '대통령', '총리', '장관', '의원', '국정감사', '법안', '정책', '선거', '투표', '후보', '정당', '여당', '야당', '국정', '행정', '입법', '사법', '헌법', '개헌', '탄핵'],
        medium: ['청와대', '국무총리', '국무회의', '내각', '부처', '공무원', '공직', '민주주의', '독재', '권력', '정권', '집권', '야권', '보수', '진보', '중도'],
        low: ['국가', '정부', '공공', '시민', '국민', '주민', '지방', '중앙', '연방']
    },
    '사회': {
        high: ['사회', '복지', '교육', '의료', '보건', '안전', '치안', '범죄', '사건', '사고', '재해', '재난', '피해', '구조', '구급', '소방', '경찰', '법원', '검찰', '판결', '재판'],
        medium: ['지역', '동네', '마을', '시민', '주민', '이웃', '공동체', '봉사', '자원봉사', '기부', '후원', '도움', '지원', '혜택', '서비스', '시설', '인프라'],
        low: ['사람', '가족', '아이', '어린이', '청소년', '어르신', '노인', '장애인', '여성', '남성']
    },
    '연예': {
        high: ['연예', '연예인', '배우', '가수', '아이돌', '스타', '셀럽', '엔터테인먼트', '드라마', '영화', '음악', '앨범', '콘서트', '공연', '무대', '방송', 'TV', '예능', '토크쇼'],
        medium: ['데뷔', '컴백', '신곡', '히트', '인기', '팬', '팬클럽', '소속사', '기획사', '매니지먼트', '스케줄', '촬영', '녹화', '라이브'],
        low: ['노래', '춤', '연기', '출연', '게스트', 'MC', '진행', '호스트']
    },
    '기술': {
        high: ['기술', 'IT', 'AI', '인공지능', '머신러닝', '딥러닝', '빅데이터', '클라우드', '사물인터넷', 'IoT', '블록체인', '암호화폐', '비트코인', '메타버스', 'VR', 'AR', '로봇', '자동화', '디지털', '소프트웨어', '하드웨어'],
        medium: ['컴퓨터', '스마트폰', '앱', '애플리케이션', '프로그램', '시스템', '네트워크', '인터넷', '웹', '사이트', '플랫폼', '서비스', '솔루션', '혁신', '개발', '연구'],
        low: ['과학', '기계', '전자', '통신', '정보', '데이터', '코드', '프로그래밍']
    },
    '국제': {
        high: ['국제', '해외', '외교', '대사관', '영사관', '외무부', '국무부', '유엔', 'UN', 'G7', 'G20', 'NATO', 'EU', '아세안', '미국', '중국', '일본', '러시아', '유럽', '아시아', '아프리카', '남미', '북미'],
        medium: ['글로벌', '세계', '국경', '비자', '여권', '이민', '난민', '무역', '수출', '수입', '관세', '협정', 'FTA', '제재', '봉쇄', '갈등', '분쟁', '전쟁', '평화'],
        low: ['외국', '해외여행', '유학', '어학', '번역', '통역']
    },
    '스포츠': {
        high: ['스포츠', '축구', '야구', '농구', '배구', '테니스', '골프', '수영', '육상', '체조', '태권도', '유도', '복싱', '올림픽', '월드컵', '아시안게임', '프로리그', 'KBO', 'K리그', 'NBA', 'MLB'],
        medium: ['경기', '시합', '대회', '토너먼트', '챔피언십', '우승', '준우승', '메달', '금메달', '은메달', '동메달', '선수', '감독', '코치', '팀', '구단', '클럽'],
        low: ['운동', '훈련', '연습', '체력', '근력', '지구력', '기록', '점수', '승부', '승리', '패배']
    },
    '환경': {
        high: ['환경', '기후', '온난화', '탄소', '배출', '감축', '친환경', '재생에너지', '태양광', '풍력', '수력', '원자력', '석탄', '석유', '가스', '오염', '대기오염', '수질오염', '토양오염', '미세먼지', '황사'],
        medium: ['에너지', '전력', '발전', '절약', '효율', '지속가능', '생태', '자연', '보호', '보존', '복원', '녹색', '그린', '청정', '폐기물', '재활용', '분리수거'],
        low: ['나무', '숲', '바다', '강', '산', '공원', '동물', '식물']
    },
    '문화': {
        high: ['문화', '예술', '미술', '음악', '무용', '연극', '오페라', '클래식', '전시', '박물관', '미술관', '음식', '살', '갤러리', '축제', '페스티벌', '문화재', '유산', '전통', '역사', '유적', '고궁'],
        medium: ['작품', '작가', '화가', '조각가', '예술가', '창작', '창조', '영감', '아름다움', '감동', '표현', '스타일', '장르', '트렌드', '유행'],
        low: ['책', '소설', '시', '문학', '도서', '독서', '글', '글쓰기']
    }
};

/**
 * 텍스트에서 카테고리별 점수를 계산
 */
function calculateCategoryScores(text) {
    const scores = {};
    const cleanText = text.toLowerCase().replace(/[^\w\s가-힣]/g, ' ');

    // 각 카테고리별로 점수 계산
    Object.keys(CATEGORY_KEYWORDS).forEach(category => {
        let score = 0;
        const keywords = CATEGORY_KEYWORDS[category];

        // 높은 가중치 키워드 (3점)
        keywords.high.forEach(keyword => {
            const matches = (cleanText.match(new RegExp(keyword, 'g')) || []).length;
            score += matches * 3;
        });

        // 중간 가중치 키워드 (2점)
        keywords.medium.forEach(keyword => {
            const matches = (cleanText.match(new RegExp(keyword, 'g')) || []).length;
            score += matches * 2;
        });

        // 낮은 가중치 키워드 (1점)
        keywords.low.forEach(keyword => {
            const matches = (cleanText.match(new RegExp(keyword, 'g')) || []).length;
            score += matches * 1;
        });

        scores[category] = score;
    });

    return scores;
}

/**
 * 제목과 내용의 가중치를 다르게 적용
 */
export function getCategoryByContent(title = '', content = '') {
    if (!title && !content) return '기타';

    // 제목은 3배 가중치, 내용은 1배 가중치
    const titleScores = calculateCategoryScores(title);
    const contentScores = calculateCategoryScores(content);

    const finalScores = {};

    // 모든 카테고리에 대해 최종 점수 계산
    Object.keys(CATEGORY_KEYWORDS).forEach(category => {
        finalScores[category] = (titleScores[category] || 0) * 3 + (contentScores[category] || 0) * 1;
    });

    // 가장 높은 점수의 카테고리 찾기
    let maxScore = 0;
    let bestCategory = '기타';

    Object.entries(finalScores).forEach(([category, score]) => {
        if (score > maxScore) {
            maxScore = score;
            bestCategory = category;
        }
    });

    // 최소 점수 임계값 (너무 낮으면 '기타'로 분류)
    const MIN_SCORE_THRESHOLD = 2;

    return maxScore >= MIN_SCORE_THRESHOLD ? bestCategory : '기타';
}

/**
 * 디버깅용: 각 카테고리별 점수를 반환
 */
export function getCategoryScoresDebug(title = '', content = '') {
    const titleScores = calculateCategoryScores(title);
    const contentScores = calculateCategoryScores(content);

    const finalScores = {};
    Object.keys(CATEGORY_KEYWORDS).forEach(category => {
        finalScores[category] = {
            title: titleScores[category] || 0,
            content: contentScores[category] || 0,
            total: (titleScores[category] || 0) * 3 + (contentScores[category] || 0) * 1
        };
    });

    return finalScores;
}

/**
 * 카테고리 이름에 따른 CSS 클래스 반환
 */
export function getCategoryClass(category) {
    const classMap = {
        '전체': 'category-all',
        '사회': 'category-society',
        '정치': 'category-politics',
        '문화': 'category-culture',
        '연예': 'category-entertainment',
        '경제': 'category-economy',
        '국제': 'category-international',
        '기술': 'category-tech',
        '환경': 'category-environment',
        '스포츠': 'category-sports',
        '기타': 'category-default'
    };
    return classMap[category] || 'category-default';
}

/**
 * 카테고리 이름에 따른 배경색 반환
 */
export function getCategoryBackgroundColor(category) {
    const colorMap = {
        '전체': '#f1f5f9',
        '사회': '#dcfce7',
        '정치': '#fee2e2',
        '문화': '#ede9fe',
        '연예': '#fed7aa',
        '경제': '#ccfbf1',
        '국제': '#e0f2fe',
        '기술': '#e0e7ff',
        '환경': '#dcfce7',
        '스포츠': '#ffe4e6',
        '기타': '#f1f5f9'
    };
    return colorMap[category] || '#f1f5f9';
}

/**
 * 카테고리 이름에 따른 텍스트 색상 반환
 */
export function getCategoryTextColor(category) {
    const colorMap = {
        '전체': '#475569',    // 슬레이트 700
        '사회': '#166534',    // 그린 800
        '정치': '#b91c1c',    // 레드 700
        '문화': '#7c3aed',    // 바이올렛 600
        '연예': '#ea580c',    // 오렌지 600
        '경제': '#0f766e',    // 틸 700
        '국제': '#0369a1',    // 스카이 700
        '기술': '#4338ca',    // 인디고 700
        '환경': '#15803d',    // 그린 700
        '스포츠': '#be123c',  // 로즈 700
        '기타': '#475569'     // 슬레이트 700
    };
    return colorMap[category] || '#475569';
}

/**
 * 카테고리 이름에 따른 프로그레스 색상 반환
 */
export function getCategoryProgressColor(category) {
    const colorMap = {
        '전체': '#64748b',    // 슬레이트 500
        '사회': '#22c55e',    // 그린 500
        '정치': '#ef4444',    // 레드 500
        '문화': '#a855f7',    // 바이올렛 500
        '연예': '#f97316',    // 오렌지 500
        '경제': '#14b8a6',    // 틸 500
        '국제': '#0ea5e9',    // 스카이 500
        '기술': '#6366f1',    // 인디고 500
        '환경': '#22c55e',    // 그린 500
        '스포츠': '#f43f5e',  // 로즈 500
        '기타': '#64748b'     // 슬레이트 500
    };
    return colorMap[category] || '#64748b';
}