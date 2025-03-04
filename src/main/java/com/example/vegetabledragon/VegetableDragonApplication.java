package com.example.vegetabledragon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class VegetableDragonApplication {

    public static void main(String[] args) {
        // 🔹 Spring Boot 애플리케이션 실행
        SpringApplication.run(VegetableDragonApplication.class, args);

        // 🔹 MySQL 데이터베이스 연결 및 조회
        try {
            String url = "jdbc:mysql://localhost:3306/news_ai_db";
            String UserName = "root";
            String Password = "1234";

            Connection connection = DriverManager.getConnection(url, UserName, Password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from news_articles");

            if (resultSet.next()) {  // 🔹 여러 줄을 읽을 수 있도록 수정
//                String name = resultSet.getString("email");
                System.out.println("무언가가 있습니다.");
            } else {
                System.out.println("데이터가 없습니다.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {

        }
    }
}
