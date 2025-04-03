package com.example.vegetabledragon;

import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.exception.InvalidPostFieldException;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    //createPost 테스트
    @Test
    void createPost_shouldCreatePostSuccessfully() throws InvalidPostFieldException {
        String username = "testUser";
        PostRequest postRequest = new PostRequest();
        postRequest.setTitle("Test Title");
        postRequest.setContent("Test Content");

        Post post = new Post(postRequest.getTitle(), postRequest.getContent(), username);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(username, postRequest);

        // Assert
        assertNotNull(createdPost);
        assertEquals(post.getTitle(), createdPost.getTitle());
        assertEquals(post.getContent(), createdPost.getContent());
    }
    //getPostById 테스트
    //getAllPost 테스트
    //deletePostById 테스트
    //UpdatePostById 테스트
}
