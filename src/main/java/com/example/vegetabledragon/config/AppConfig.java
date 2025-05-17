package com.example.vegetabledragon.config;

<<<<<<< HEAD
import com.example.vegetabledragon.repository.CategoryRepository;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import com.example.vegetabledragon.repository.PostRepository;
import com.example.vegetabledragon.repository.UserRepository;
import com.example.vegetabledragon.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.context.annotation.EnableAspectJAutoProxy;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
<<<<<<< HEAD
@EnableAspectJAutoProxy // AOP 활성화
public class AppConfig {
    @Bean
    public PostService postService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository, MLService mlService) {
        return new PostServiceImpl(postRepository, userRepository, categoryRepository, mlService);
=======
public class AppConfig {
    @Bean
    public PostService postService(PostRepository postRepository) {
        return new PostServiceImpl(postRepository);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
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
