package com.example.vegetabledragon;

import com.example.vegetabledragon.controller.PostController;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private HttpSession session;

    @InjectMocks
    private PostController postController; // PostController에 Mock 객체 주입

    private MockMvc mockMvc; // 이게 뭐묘

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }

    @Test
    @DisplayName("포스트 생성 작동 확인")
    void createPost_shouldReturnOk() throws Exception{
        // PostService에서 가져온 authorUsername이 세션의 loggedInUser하고 같은지 확인.

        // Given
        String loggedInUser = "test";
        PostRequest postRequest = new PostRequest("Post Title", "Post Content");
        Post savedPost = new Post("Post Title", "Post Content", loggedInUser);
        savedPost.setId(1L);

        // Mocking : PostService의 createPost 메소드가 호출될 때 가짜 Post 객체 반환 -> loggedInUser하고 postRequest받으면 savedPost 반환
        // eq(loggedInUser)를 통해서 세션 사용자와 loggedInUser가 같은지 확인.
        when(postService.createPost(eq(loggedInUser), any(PostRequest.class))).thenReturn(savedPost);

        // when : HTTP Post 요청 수행
        mockMvc.perform(post("/posts")
                .contentType("application/json")
                .content("{\"title\" : \"Post Title\", \"content\" : \"Post Content\" }")
                        .sessionAttr("loggedInUser", loggedInUser))

        // Then: 상태 코드와 응답 내용을 검증
                .andExpect(status().isOk()) // HTTP 200 OK 상태 코드 확인
                .andExpect(jsonPath("$.id").value(1L)) // 반환된 Post 객체의 id가 1L인지 확인
                .andExpect(jsonPath("$.title").value("Post Title"))
                .andExpect(jsonPath("$.content").value("Post Content"))
                .andExpect(jsonPath("$.authorUsername").value(loggedInUser));

        verify(postService, times(1)).createPost(eq(loggedInUser), any(PostRequest.class));

    }

}
