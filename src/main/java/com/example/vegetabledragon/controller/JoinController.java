package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.UserAlreadyExistsException;
import com.example.vegetabledragon.repository.UserRepository;
import com.example.vegetabledragon.service.JoinService;
import com.example.vegetabledragon.service.JoinServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserAlreadyExistsException {
        User savedUser = joinService.join(user);
        return ResponseEntity.ok(savedUser);
    }

    // 로그인 (세션 저장)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm, HttpSession session) throws InvalidLoginException {
        String username = joinService.login(loginForm);
        if (username != null) {
            session.setAttribute("loggedInUser", username); // 세션에 로그인한 사용자 저장
            // ➕ 사용자 이메일도 함께 저장
            Optional<User> user = userRepository.findByUsername(username);
            user.ifPresent(u -> session.setAttribute("loggedInEmail", u.getEmail()));
            return ResponseEntity.ok(username);
        }
        return ResponseEntity.status(401).body("로그인 실패");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
            session.invalidate(); // 세션 무효화


            return ResponseEntity.ok("로그아웃 성공");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 상태가 아닙니다.");
    }

    @GetMapping("/session")
    public ResponseEntity<?> getSessionUser(HttpSession session) {
        String username = (String) session.getAttribute("loggedInUser");
        String email = (String) session.getAttribute("loggedInEmail");

        if (username != null && email != null) {
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", username);
            userInfo.put("email", email);
            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

}
