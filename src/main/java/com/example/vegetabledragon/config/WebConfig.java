package com.example.vegetabledragon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 api 허용
        .allowedOrigins("https://localhost:8080") // 프론트 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true); // 세션 쿠키 허용
    }
}
