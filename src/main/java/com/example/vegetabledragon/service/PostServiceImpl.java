package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;


@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.DESC, "createdAt");

    @Override
    public Post createPost(String username, PostRequest request) {
        Post post = new Post(request.getTitle(), request.getContent(), username);

        return postRepository.save(post);
    }
    @Override
    public Page<Post> getAllPosts(int page, int size){
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return postRepository.findAll(pageable);
    }
    @Override
    public Optional<Post> getPostById(Long postId){
        return postRepository.findById(postId);
    }
}
