package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
<<<<<<< HEAD
import com.example.vegetabledragon.exception.InvalidLoginException;
import com.example.vegetabledragon.exception.UserAlreadyExistsException;
import com.example.vegetabledragon.repository.UserRepository;
=======
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
import com.example.vegetabledragon.service.JoinService;
import com.example.vegetabledragon.service.JoinServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

=======
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
<<<<<<< HEAD
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserAlreadyExistsException {
=======

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        User savedUser = joinService.join(user);
        return ResponseEntity.ok(savedUser);
    }

    // 로그인 (세션 저장)
    @PostMapping("/login")
<<<<<<< HEAD
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm, HttpSession session) throws InvalidLoginException {
        String username = joinService.login(loginForm);
        if (username != null) {
            session.setAttribute("loggedInUser", username); // 세션에 로그인한 사용자 저장
            // ➕ 사용자 이메일도 함께 저장
            Optional<User> user = userRepository.findByUsername(username);
            user.ifPresent(u -> session.setAttribute("loggedInEmail", u.getEmail()));
            return ResponseEntity.ok(username);
=======
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm, HttpSession session) {
        String username = joinService.login(loginForm);
        if (username != null) {
            session.setAttribute("loggedInUser", username); // 세션에 로그인한 사용자 저장
            return ResponseEntity.ok("로그인 성공");
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
        }
        return ResponseEntity.status(401).body("로그인 실패");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session.getAttribute("loggedInUser") != null) {
<<<<<<< HEAD
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

=======
            return ResponseEntity.status(400).body("로그인 상태가 아닙니다.");
        }
        session.invalidate(); // 세션 무효화
        return ResponseEntity.ok("로그아웃 성공");
    }
>>>>>>> 808ff354926d354652e056c47aa6693ac10583e8
}
