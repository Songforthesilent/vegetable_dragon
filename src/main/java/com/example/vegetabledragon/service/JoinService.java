package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
<<<<<<< HEAD
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.UserAlreadyExistsException;

public interface JoinService {
    User join(User user) throws UserAlreadyExistsException;
    String login(LoginForm loginForm) throws InvalidLoginException;
=======

public interface JoinService {
    User join(User user);
    String login(LoginForm loginForm);
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
