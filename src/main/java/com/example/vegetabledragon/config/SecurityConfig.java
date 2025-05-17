package com.example.vegetabledragon.config;

import org.springframework.context.annotation.Bean;
<<<<<<< HEAD
import org.springframework.security.config.Customizer;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
<<<<<<< HEAD
                .cors(Customizer.withDefaults()) // 추가함
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
