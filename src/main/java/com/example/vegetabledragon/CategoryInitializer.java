package com.example.vegetabledragon;

import com.example.vegetabledragon.domain.Category;
import com.example.vegetabledragon.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.findByName("경제") == null) {
            categoryRepository.save(new Category("경제"));
            System.out.println("경제 카테고리가 데이터베이스에 추가되었습니다.");
        }
        // 카테고리가 없으면 기본 카테고리 생성
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("전체"));
            categoryRepository.save(new Category("사회"));
            categoryRepository.save(new Category("정치"));
            categoryRepository.save(new Category("문화"));
            categoryRepository.save(new Category("연예"));
            categoryRepository.save(new Category("국제"));
            categoryRepository.save(new Category("경제"));
            System.out.println("기본 카테고리가 데이터베이스에 추가되었습니다.");
        }
    }
}
