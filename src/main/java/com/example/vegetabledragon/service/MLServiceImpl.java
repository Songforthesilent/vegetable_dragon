package com.example.vegetabledragon.service;

import com.example.vegetabledragon.exception.MLServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import lombok.extern.slf4j.Slf4j;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RequiredArgsConstructor
<<<<<<< HEAD
@Slf4j
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
public class MLServiceImpl implements MLService {
    // flask 연결
    // https 로 설정해야 한다.
    private final RestTemplate restTemplate;
    private final String flaskApiUrl;

    public MLServiceImpl(@Value("${flask.api.url}") String flaskApiUrl, RestTemplate restTemplate) {
        this.flaskApiUrl = flaskApiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Object> predict(String text) {
<<<<<<< HEAD
//        log.info("[Spring Boot] MLService, predict 실행 응답: " + text);
=======
        System.out.println("[Spring Boot] MLService, predict 실행 응답: " + text);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8


        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Body로 보낼 데이터
        Map<String, String> requestBody = new HashMap<>();
<<<<<<< HEAD
        requestBody.put("title", ""); // or post.getTitle() if available
        requestBody.put("content", text); // post.getContent()

=======
        requestBody.put("text", text);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

        // 제대로 request를 보내는지 확인
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);
<<<<<<< HEAD
//            log.info("Generated Request Body: " + jsonBody);
=======
            System.out.println("Generated Request Body: " + jsonBody);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        } catch (Exception e){
            e.printStackTrace();
        }

        // HTTP
        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        // Flask API 호출 (POST 요청)
        try {
<<<<<<< HEAD
//            log.debug("[Spring Boot] Flask로 요청을 보냅니다: " + flaskApiUrl);
            Map response = restTemplate.postForObject(flaskApiUrl, request, Map.class);
//            log.info("[Spring Boot] Flask 응답: " + response);
            return response;
        } catch (Exception e) {
//            log.warn("[Spring Boot] Flask 요청 실패: " + e.getMessage());
=======
            System.out.println("[Spring Boot] Flask로 요청을 보냅니다: " + flaskApiUrl);
            Map response = restTemplate.postForObject(flaskApiUrl, request, Map.class);
            System.out.println("[Spring Boot] Flask 응답: " + response);
            return response;
        } catch (Exception e) {
            System.out.println("[Spring Boot] Flask 요청 실패: " + e.getMessage());
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
            throw new MLServiceException("Flask 요청 실패", e);
        }
    }
}
