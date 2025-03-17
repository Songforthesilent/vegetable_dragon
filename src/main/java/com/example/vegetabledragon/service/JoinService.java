package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;

public interface JoinService {
    User join(User user);
    String login(LoginForm loginForm);
}
