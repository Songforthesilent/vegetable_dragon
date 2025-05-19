package com.example.vegetabledragon.repository;

import com.example.vegetabledragon.domain.Category;
import com.example.vegetabledragon.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContainingIgnoreCase(String title);
}