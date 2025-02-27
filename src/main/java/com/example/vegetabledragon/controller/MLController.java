package com.example.vegetabledragon.controller;
import com.example.vegetabledragon.service.MLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")

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

        if (userText == null || userText.isEmpty()) {
            System.out.println("[Spring Boot] 텍스트가 없습니다!");
            return new ResponseEntity<>(Map.of("error", "Invalid input"), HttpStatus.BAD_REQUEST);
        }

        // Flask API 호출
        Map<String, Object> result = mlService.predict(userText);
        System.out.println("✅ [Spring Boot] Flask 응답: " + result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
