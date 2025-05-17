package com.example.vegetabledragon.service;

<<<<<<< HEAD
import com.example.vegetabledragon.domain.Category;
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import com.example.vegetabledragon.exception.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import java.util.List;
=======
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.dto.PostRequest;
import org.springframework.data.domain.Page;

>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import javax.swing.text.html.Option;
import java.util.Optional;

// DIP를 위해 인터페이스 추가작업.
public interface PostService {
<<<<<<< HEAD
    Post createPost(String username, PostRequest request) throws InvalidPostFieldException, UserNotFoundException;
    Page<Post> getAllPosts(int page, int size) throws InvalidPageSizeException;
    Optional<Post> getPostById(Long postId) throws PostNotFoundException;

    List<Post> getPostsByCategory(Category category, int limit);

    //  CRUD에서 UD 추가
    void deletePostById(Long postId, HttpSession session) throws PostNotFoundException, UnauthorizedException;

    Post updatePost(Long postId, PostRequest request, HttpSession session) throws PostNotFoundException, InvalidPostFieldException, UnauthorizedException;
=======
    Post createPost(String username, PostRequest request);
    Page<Post> getAllPosts(int page, int size);
    Optional<Post> getPostById(Long postId);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
