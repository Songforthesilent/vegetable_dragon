//package com.example.vegetabledragon;
//
//import com.example.vegetabledragon.controller.PostController;
//import com.example.vegetabledragon.domain.Post;
//import com.example.vegetabledragon.dto.PostRequest;
//import com.example.vegetabledragon.exception.InvalidPageSizeException;
//import com.example.vegetabledragon.exception.PostNotFoundException;
//import com.example.vegetabledragon.exception.UnauthorizedException;
//import com.example.vegetabledragon.service.PostService;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.Optional;
//
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class PostControllerTest {
//
//    @Mock
//    private PostService postService;
//
//    @Mock
//    private HttpSession session;
//
//    @InjectMocks
//    private PostController postController; // PostController에 Mock 객체 주입
//
//    private MockMvc mockMvc; // 이게 뭐묘
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
//    }
//
//    @Test
//    @DisplayName("포스트 생성 작동 확인")
//    void createPost_shouldReturnOk() throws Exception{
//        // PostService에서 가져온 authorUsername이 세션의 loggedInUser하고 같은지 확인.
//
//        // Given
//        String loggedInUser = "test";
//        PostRequest postRequest = new PostRequest("Post Title", "Post Content");
//        Post savedPost = new Post("Post Title", "Post Content", loggedInUser);
//        savedPost.setId(1L);
//
//        // Mocking : PostService의 createPost 메소드가 호출될 때 가짜 Post 객체 반환 -> loggedInUser하고 postRequest받으면 savedPost 반환
//        // eq(loggedInUser)를 통해서 세션 사용자와 loggedInUser가 같은지 확인.
//        when(postService.createPost(eq(loggedInUser), any(PostRequest.class))).thenReturn(savedPost);
//
//        // when : HTTP Post 요청 수행
//        mockMvc.perform(post("/posts")
//                .contentType("application/json")
//                .content("{\"title\" : \"Post Title\", \"content\" : \"Post Content\" }")
//                        .sessionAttr("loggedInUser", loggedInUser))
//
//        // Then: 상태 코드와 응답 내용을 검증
//                .andExpect(status().isOk()) // HTTP 200 OK 상태 코드 확인
//                .andExpect(jsonPath("$.id").value(1L)) // 반환된 Post 객체의 id가 1L인지 확인
//                .andExpect(jsonPath("$.title").value("Post Title"))
//                .andExpect(jsonPath("$.content").value("Post Content"))
//                .andExpect(jsonPath("$.authorUsername").value(loggedInUser));
//
//    }
//
//    @Test
//    @DisplayName("게시글 리스트를 반환하는지 확인")
//    void getAllPosts_shoudReturnOKwithPosts() throws Exception{
//        // Given : 게시글 목록 임의 생성
//        List<Post>posts = List.of(
//                new Post("Post Title1", "Post Content1", "user1"),
//                new Post("Post Title2","Post Content2", "user2")
//        );
//        Page<Post> page = new PageImpl<>(posts, PageRequest.of(0, 10), posts.size());
//
//        // when : postService가 해당 page를 반환
//        given(postService.getAllPosts(0, 10)).willReturn(page);
//
//        // then
//        mockMvc.perform(get("/posts")
//                .param("page", "0")
//                .param("size", "10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray())
//                .andExpect(jsonPath("$.content[0].title").value("Post Title1"))
//                .andExpect(jsonPath("$.content[1].title").value("Post Title2"));
//    }
//
//    @Test
//    @DisplayName("페이지네이션으로 각각 다른 페이지의 게시글을 반환하는지 확인")
//    void getAllPosts_shoudReturnDifferentPages() throws Exception{
//        List<Post> allPosts = List.of(
//                new Post("Post Title1", "Post Content1", "user1"),
//                new Post("Post Title2","Post Content2", "user2")
//        );
//
//        int size = 1;
//
//        int page0 = 0;
//        List<Post> contentPage0 = allPosts.subList(page0, size);
//        Page<Post> pageResult0 = new PageImpl<>(contentPage0, PageRequest.of(page0, size), allPosts.size());
//
//        given(postService.getAllPosts(page0, size)).willReturn(pageResult0);
//
//        mockMvc.perform(get("/posts")
//                .param("page", String.valueOf(page0))
//                .param("size", String.valueOf(size)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].title").value("Post Title1"))
//                .andExpect(jsonPath("$.content.length()").value(1));
//
//        int page1 = 1;
//        List<Post> contentPage1 = allPosts.subList(page1, size + 1);
//        Page<Post> pageResult1 = new PageImpl<>(contentPage1, PageRequest.of(page1, size), allPosts.size());
//
//        given(postService.getAllPosts(page1, size)).willReturn(pageResult1);
//
//        mockMvc.perform(get("/posts")
//                        .param("page", String.valueOf(page1))
//                        .param("size", String.valueOf(size)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].title").value("Post Title2"))
//                .andExpect(jsonPath("$.content.length()").value(1));
//
//
//    }
//
//    @Test
//    @DisplayName("게시글 조회가 제대로 되는지 확인")
//    void getPostwithPostId() throws Exception{
//        //given
//        Post post = new Post("Post Title", "Post Content", "user1");
//        post.setId(1L);
//
//        given(postService.getPostById(1L)).willReturn(Optional.of(post));
//
//        mockMvc.perform(get("/posts/{id}", 1L))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.title").value("Post Title"))
//                .andExpect(jsonPath("$.content").value("Post Content"))
//                .andExpect(jsonPath("$.authorUsername").value("user1"));
//
//    }
//
//    @Test
//    @DisplayName("세션 사용자로 게시글 ID를 삭제하면 204 반환")
//    void deletePostWithSessionUser() throws Exception{
//        Long postId = 1L;
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("loggedInUser", "user1");
//
//        doNothing().when(postService).deletePostById(postId, session);
//
//        mockMvc.perform(delete("/posts/{postId}", postId).session(session))
//                .andExpect(status().isNoContent());
//
//    }
//
//    @Test
//    @DisplayName("세션 사용자로 게시글을 수정하면 수정된 게시글과 함께 200 반환")
//    void updatePost_Return200() throws Exception{
//        Long postId = 1L;
//        MockHttpSession session = new MockHttpSession();
//        session.setAttribute("loggedInUser", "user1");
//
//        PostRequest request = new PostRequest("Updated Title", "Updated Content");
//        Post updatedPost = new Post("Updated Title", "Updated Content", "user1");
//        updatedPost.setId(postId);
//
//        given(postService.updatePost(eq(postId), any(PostRequest.class), eq(session)))
//                .willReturn(updatedPost);
//
//        mockMvc.perform(put("/posts/{postId}", postId).session(session)
//                .contentType("application/json")
//                .content("""
//                {"title": "Updated Title",
//                 "content": "Updated Content"
//                }
//               """))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.title").value("Updated Title"))
//                .andExpect(jsonPath("$.content").value("Updated Content"))
//                .andExpect(jsonPath("$.authorUsername").value("user1"));
//
//    }
//
//
//}
