package com.example.vegetabledragon.service;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    // 한 번 할당되면은 변경되지 않는다.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(User user) {
        // 중복 검사
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already in use");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }
        if (userRepository.existsByAnonymousName(user.getAnonymousName())) {
            throw new RuntimeException("Anonymous name is already in use");
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        // 새 사용자 생성
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setEmail(user.getEmail());
        newUser.setAnonymousName(user.getAnonymousName());
        newUser.setBirthday(user.getBirthday());
        newUser.setRealName(user.getRealName());

        // 저장
        return userRepository.save(newUser);

    }

    public String login(LoginForm loginForm) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new RuntimeException("The email does not exists"));

        // 비밀번호 검증
        if(!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return "Login Success";
    }
}
