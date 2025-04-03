package com.example.vegetabledragon.config;

import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import com.example.vegetabledragon.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public PostService postService(PostRepository postRepository, UserRepository userRepository) {
        return new PostServiceImpl(postRepository, userRepository);
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
