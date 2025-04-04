package com.example.vegetabledragon.controller;
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

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
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
        log.info("[MLController] 게시글 내용 : {}", postContent);

        // Flask API 호출
        Map<String, Object> result = mlService.predict(postContent);
        log.debug("[MLController] Flask 응답: " + result);

        return ResponseEntity.ok(result);
    }
}
