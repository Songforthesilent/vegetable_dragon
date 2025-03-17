package com.example.vegetabledragon.repository;

import com.example.vegetabledragon.domain.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
}
