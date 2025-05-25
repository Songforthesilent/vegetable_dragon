package com.example.vegetabledragon.service;

import com.example.vegetabledragon.apiPayload.code.status.ErrorStatus;
import com.example.vegetabledragon.apiPayload.exception.GeneralException;
import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {
    // 한 번 할당되면은 변경되지 않는다.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User join(User user) {
        // 중복 검사
        validateUser(user);

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(user.getPassword());

        // 새 사용자 생성
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

        // 저장
        return userRepository.save(newUser);

    }
    @Override
    public String login(LoginForm loginForm) {
        User user;
        // 이메일로 사용자 조회
        try {
            user = userRepository.findByEmail(loginForm.getEmail())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.EMAIL_NOT_FOUND));
        } catch(GeneralException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        // 비밀번호 검증 (암호화 -> matches 로 비교)
        if(!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_INCORRECT);
        }

        return user.getUsername();
    }

    private void validateUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new GeneralException(ErrorStatus.USER_NAME_DUPLICATE);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GeneralException(ErrorStatus.USER_EMAIL_DUPLICATE);
        }
        if (userRepository.existsByAnonymousName(user.getAnonymousName())) {
            throw new GeneralException(ErrorStatus.USER_ANONYMOUS_DUPLICATE);
        }
    }
}
