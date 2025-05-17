package com.example.vegetabledragon.controller;
<<<<<<< HEAD
import com.example.vegetabledragon.domain.Post;
import com.example.vegetabledragon.exception.PostNotFoundException;
import com.example.vegetabledragon.service.MLService;
import com.example.vegetabledragon.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
=======
import com.example.vegetabledragon.service.MLService;
import com.example.vegetabledragon.service.MLServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
<<<<<<< HEAD
@Slf4j
@RequiredArgsConstructor
public class MLController {
    private final MLService mlService;
    private final PostService postService;

    // vue.js에서 입력받은 데이터를 flask api로 전달
    @PostMapping("/predict/{postId}")
    public ResponseEntity<Map<String, Object>> callPythonAPI(@PathVariable Long postId) throws PostNotFoundException {

        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        String postContent = post.getContent();
//        log.info("[MLController] 게시글 내용 : {}", postContent);

        // Flask API 호출
        Map<String, Object> result = mlService.predict(postContent);
//        log.debug("[MLController] Flask 응답: " + result);
=======
public class MLController {
    private final MLService mlService;

    public MLController(MLService mlService) {
        this.mlService = mlService;
    }

    // vue.js에서 입력받은 데이터를 flask api로 전달
    @PostMapping("/predict")
    public ResponseEntity<Map<String, Object>> callPythonAPI(@RequestBody Map<String, String> request) {
        System.out.println("[Spring Boot] 받은 요청 데이터: " + request);

        String userText = request.get("text");
        if (userText == null || userText.trim().isEmpty()) {
            System.out.println("[Spring Boot] 텍스트가 없습니다!");
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid input"));
        }

        // Flask API 호출
        Map<String, Object> result = mlService.predict(userText);
        System.out.println("✅ [Spring Boot] Flask 응답: " + result);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

        return ResponseEntity.ok(result);
    }
}
