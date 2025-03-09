package com.example.vegetabledragon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.util.*;

import org.springframework.stereotype.Service;

@Service  // 서비스 클래스 선언
public class MLService {
    // flask 연결
    // https 로 설정해야 한다.
    private static final String MODEL_API_URL = "http://da38-34-125-250-219.ngrok-free.app/predict"; // flask 서버 url

    public Map<String, Object> predict(String text) {
        System.out.println("[Spring Boot] MLService, predict 실행 응답: " + text);
        RestTemplate restTemplate = new RestTemplate();

        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body로 보낼 데이터
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", text);

        // 제대로 request를 보내는지 확인
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);
            System.out.println("Generated Request Body: " + jsonBody);
        } catch (Exception e){
            e.printStackTrace();
        }

        // HTTP
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        // Flask API 호출 (POST 요청)
        try {
            System.out.println("[Spring Boot] Flask로 요청을 보냅니다: " + MODEL_API_URL);
            Map response = restTemplate.postForObject(MODEL_API_URL, request, Map.class);
            System.out.println("[Spring Boot] Flask 응답: " + response);
            return response;
        } catch (Exception e) {
            System.out.println("[Spring Boot] Flask 요청 실패: " + e.getMessage());
            e.printStackTrace();
            return Collections.singletonMap("error", "Flask 요청 실패");
        }
    }
}
