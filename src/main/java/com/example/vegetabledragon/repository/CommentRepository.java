package com.example.vegetabledragon.repository;

import com.example.vegetabledragon.domain.Comment;
import com.example.vegetabledragon.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
