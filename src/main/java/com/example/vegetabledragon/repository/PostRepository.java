package com.example.vegetabledragon.repository;

<<<<<<< HEAD
import com.example.vegetabledragon.domain.Category;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import com.example.vegetabledragon.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
<<<<<<< HEAD
    Page<Post> findByCategory(Category category, Pageable pageable);
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
