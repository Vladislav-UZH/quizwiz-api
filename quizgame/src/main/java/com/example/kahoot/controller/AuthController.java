package com.example.kahoot.controller;

import com.example.kahoot.auth.AuthResponse;
import com.example.kahoot.auth.TokenRequest;
import com.example.kahoot.dto.UserCredentials;
import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.security.JwtTokenProvider;
import com.example.kahoot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    public AuthController(JwtTokenProvider tokenProvider, UserService userService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        userService.createUser(user.getUserName(), user.getPassword(), Role.ROLE_USER);
        String accessToken = tokenProvider.generateToken(user.getUserName(), true);
        String refreshToken = tokenProvider.generateToken(user.getUserName(), false);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        User user = userService.authenticate(credentials);
        String accessToken = tokenProvider.generateToken(user.getUserName(), true);
        String refreshToken = tokenProvider.generateToken(user.getUserName(), false);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRequest tokenRequest) {
        if (tokenProvider.validateToken(tokenRequest.getRefreshToken())) {
            String username = tokenProvider.getUsernameFromToken(tokenRequest.getRefreshToken());
            String accessToken = tokenProvider.generateToken(username, true);
            return ResponseEntity.ok(new AuthResponse(accessToken, tokenRequest.getRefreshToken()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        // Логика для удаления токена или добавления в черный список
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
