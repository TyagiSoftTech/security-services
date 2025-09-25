package com.security.service.controller;

import com.security.service.entity.request.AuthRequest;
import com.security.service.entity.response.AuthResponse;
import com.security.service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest request) {
        if ("user".equals(request.getUsername()) && "pass".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getUsername(), List.of("ROLE_USER"));
            return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid credentials")));
    }

    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("pong");
    }


}
