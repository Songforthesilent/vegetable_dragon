package com.example.vegetabledragon;

import com.example.vegetabledragon.domain.NewsArticle;
import com.example.vegetabledragon.repository.NewsArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NewsArticleRepositoryTest {

    @Autowired
    private NewsArticleRepository newsArticleRepository;

    @Test
    public void 뉴스기사_저장_및_조회_테스트(){
        //1. 테스트용 뉴스 기사 생성
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle("채소용 테스트 뉴스");
        newsArticle.setContent("데이터베이스가 잘 연결되었는지 확인해보는 테스트를 실행합니다. 임의로 Ai 확률을 넣었습니다.");
        newsArticle.setAiPrediction(0.80);

        //2. 데이터 저장
        NewsArticle savedArticle = newsArticleRepository.save(newsArticle);

        //3. 저장된 데이터가 있는지 확인(조회)
        Optional<NewsArticle> foundArticle = newsArticleRepository.findById(savedArticle.getId());

        // 4. 검증
        assertThat(foundArticle).isPresent();
        assertThat(foundArticle.get().getAiPrediction()).isEqualTo(0.80);
        assertThat(foundArticle.get().getTitle()).isEqualTo(newsArticle.getTitle());
        assertThat(foundArticle.get().getContent()).isEqualTo(newsArticle.getContent());

    }

    @Test
    public void 여러개뉴스기사_저장_및_전체조회_테스트(){
        NewsArticle newsArticle1 = new NewsArticle();
        NewsArticle newsArticle2 = new NewsArticle();

        newsArticle1.setTitle("테스트 1");
        newsArticle1.setContent("뉴스기사 저장을 여러모로 해보고 있습니다. 제대로 될까요?");
        newsArticle1.setAiPrediction(0.80);
        newsArticleRepository.save(newsArticle1);

        newsArticle2.setTitle("테스트 2");
        newsArticle2.setContent("이것 참 잘 되기를 간절히 바라옵니다.");
        newsArticle2.setAiPrediction(0.00);
        newsArticleRepository.save(newsArticle2);

        // 2. 전체 조회
        List<NewsArticle> articles = newsArticleRepository.findAll();

        //3. 검증
        assertThat(articles.size()).isEqualTo(2);
    }
}
