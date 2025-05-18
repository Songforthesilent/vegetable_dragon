package com.example.vegetabledragon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 api 허용
                .allowedOrigins("http://localhost:8080") // 프론트 주소 // s 뺐는데
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // OPTIONS 추가
                .allowedHeaders("*") // 추가함
                .allowCredentials(true); // 세션 쿠키 허용
    }
}
