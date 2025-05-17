package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.UserAlreadyExistsException;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
=======
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
    // 한 번 할당되면은 변경되지 않는다.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
<<<<<<< HEAD
    public User join(User user) throws UserAlreadyExistsException {
=======
    public User join(User user) {
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        // 중복 검사
        validateUser(user);

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        // 새 사용자 생성
<<<<<<< HEAD
        User newUser = new User(
                null,
                user.getRealName(),
                user.getUsername(),
                user.getEmail(),
                encryptedPassword,
                user.getBirthday(),
                user.getAnonymousName(),
                null
        );
=======
        User newUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .anonymousName(user.getAnonymousName())
                .birthday(user.getBirthday())
                .realName(user.getRealName())
                .build();
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

        // 저장
        return userRepository.save(newUser);

    }
    @Override
<<<<<<< HEAD
    public String login(LoginForm loginForm) throws InvalidLoginException {
        User user;
        // 이메일로 사용자 조회
        try {
            user = userRepository.findByEmail(loginForm.getEmail())
                    .orElseThrow(() -> new InvalidLoginException("The email does not exist"));
        } catch(InvalidLoginException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
=======
    public String login(LoginForm loginForm) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new InvalidLoginException("The Email does not exist"));
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8

        // 비밀번호 검증
        if(!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new InvalidLoginException("The password is incorrect");
        }

        return user.getUsername();
    }

<<<<<<< HEAD
    private void validateUser(User user) throws UserAlreadyExistsException {
=======
    private void validateUser(User user) {
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }
        if (userRepository.existsByAnonymousName(user.getAnonymousName())) {
            throw new UserAlreadyExistsException("Anonymous name already exists");
        }
    }
}
