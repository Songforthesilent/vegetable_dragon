package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.UserAlreadyExistsException;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
    // 한 번 할당되면은 변경되지 않는다.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User join(User user) throws UserAlreadyExistsException {
        // 중복 검사
        validateUser(user);

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        // 새 사용자 생성
        User newUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .anonymousName(user.getAnonymousName())
                .birthday(user.getBirthday())
                .realName(user.getRealName())
                .build();

        // 저장
        return userRepository.save(newUser);

    }
    @Override
    public String login(LoginForm loginForm) throws InvalidLoginException {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new InvalidLoginException("The email does not exist"));

        // 비밀번호 검증
        if(!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new InvalidLoginException("The password is incorrect");
        }

        return user.getUsername();
    }

    private void validateUser(User user) throws UserAlreadyExistsException {
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
