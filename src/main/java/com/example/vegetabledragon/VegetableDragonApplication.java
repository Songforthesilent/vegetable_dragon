package com.example.vegetabledragon;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;

@SpringBootApplication(scanBasePackages = "com.example.vegetabledragon")
public class VegetableDragonApplication {

    public static void main(String[] args) {
        // 🔹 Spring Boot 애플리케이션 실행
        SpringApplication.run(VegetableDragonApplication.class, args);
    }
}

@Component
class DatabaseInitializer implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            List<Map<String, Object>> articles = jdbcTemplate.queryForList("select * from news_articles");

            if (!articles.isEmpty()) {
                System.out.println("Found " + articles.size() + " articles");
            }
            else {
                System.out.println("No articles found");
            }

        } catch (Exception e)    {
            System.err.println("오류발생");
        }
    }
}

