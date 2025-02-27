package com.example.vegetabledragon.domain;

import com.example.vegetabledragon.dto.UserForm;
import lombok.Getter;
import jakarta.persistence.*;

// Entity : 데이터 베이스와 직접 소통하는 객체
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String userName;

    @Column(length = 50, nullable = false)
    private String password;

    public User() {}

    public User(UserForm userForm) {
        this.email = userForm.getEmail();
        this.userName = userForm.getUserName();
        this.password = userForm.getPassword();
    }

    public static User createUser(UserForm userForm) {
        return new User(userForm);
    }
}
