package com.example.vegetabledragon.config;

import com.example.vegetabledragon.repository.CategoryRepository;
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import com.example.vegetabledragon.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAspectJAutoProxy // AOP 활성화
public class AppConfig {
    @Bean
    public PostService postService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        return new PostServiceImpl(postRepository, userRepository, categoryRepository);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MLService mlService(RestTemplate restTemplate, @Value("${flask.api.url}") String flaskApiUrl) {
        return new MLServiceImpl(restTemplate, flaskApiUrl);
    }

    @Bean
    public JoinService joinService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new JoinServiceImpl(userRepository, passwordEncoder);
    }

}
