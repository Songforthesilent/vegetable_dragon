package com.example.vegetabledragon.repository;

import com.example.vegetabledragon.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name); // 카테고리 이름으로 카테고리 조회
}
