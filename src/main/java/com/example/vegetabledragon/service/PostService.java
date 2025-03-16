package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import org.springframework.data.domain.Page;

import javax.swing.text.html.Option;
import java.util.Optional;

// DIP를 위해 인터페이스 추가작업.
public interface PostService {
    Post createPost(String username, PostRequest request);
    Page<Post> getAllPosts(int page, int size);
    Optional<Post> getPostById(Long postId);
}
