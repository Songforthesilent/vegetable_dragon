package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.exception.InvalidPageSizeException;
import com.example.vegetabledragon.exception.InvalidPostFieldException;
import com.example.vegetabledragon.exception.PostNotFoundException;
import org.springframework.data.domain.Page;

import javax.swing.text.html.Option;
import java.util.Optional;

// DIP를 위해 인터페이스 추가작업.
public interface PostService {
    Post createPost(String username, PostRequest request) throws InvalidPostFieldException;
    Page<Post> getAllPosts(int page, int size) throws InvalidPageSizeException;
    Optional<Post> getPostById(Long postId) throws PostNotFoundException;

    //  CRUD에서 UD 추가
    void deletePostById(Long postId) throws PostNotFoundException;
    Post updatePost(Long postId, PostRequest request) throws PostNotFoundException, InvalidPostFieldException;
}
