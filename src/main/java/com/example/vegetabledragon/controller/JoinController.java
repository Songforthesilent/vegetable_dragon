package com.example.vegetabledragon.controller;

import com.example.vegetabledragon.domain.User;
import com.example.vegetabledragon.dto.LoginForm;
import com.example.vegetabledragon.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        joinService.join(user);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm){
        String result = joinService.login(loginForm);
        return ResponseEntity.ok(result);
    }
}
