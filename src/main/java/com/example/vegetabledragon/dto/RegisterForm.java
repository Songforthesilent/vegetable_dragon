package com.example.vegetabledragon.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterForm {
    private String realName;
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
    private String anonymousName;
}
