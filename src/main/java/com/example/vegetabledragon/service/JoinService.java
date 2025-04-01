package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.UserAlreadyExistsException;

public interface JoinService {
    User join(User user) throws UserAlreadyExistsException;
    String login(LoginForm loginForm) throws InvalidLoginException;
}
