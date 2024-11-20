package com.example.kahoot.controller;

import com.example.kahoot.auth.AuthResponse;
import com.example.kahoot.auth.TokenRequest;
import com.example.kahoot.dto.UserCredentials;
import com.example.kahoot.dto.UserResponse;
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
    public ResponseEntity<?> register(@RequestBody UserCredentials userCredentials) {
        userService.createUser(userCredentials.getUsername(), userCredentials.getPassword(), Role.ROLE_USER);
        String accessToken = tokenProvider.generateToken(userCredentials.getUsername(), true);
        String refreshToken = tokenProvider.generateToken(userCredentials.getUsername(), false);
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
        if (tokenRequest == null || tokenRequest.getRefreshToken() == null) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }
        // Логирование
        System.out.println("Received refresh token: " + tokenRequest.getRefreshToken());

        if (tokenProvider.validateToken(tokenRequest.getRefreshToken())) {
            String username = tokenProvider.getUsernameFromToken(tokenRequest.getRefreshToken());
            String accessToken = tokenProvider.generateToken(username, true);
            return ResponseEntity.ok(new AuthResponse(accessToken, tokenRequest.getRefreshToken()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        if (tokenProvider.validateToken(jwt)) {
            String username = tokenProvider.getUsernameFromToken(jwt);
            User user = userService.getUserByUsername(username);
            userService.deleteUserById(user.getId());  // Удаляем все токены пользователя по Id
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        if (tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            User user = userService.getUserByUsername(username);
            UserResponse userResponse = new UserResponse(user.getUserName(), user.getScore(), user.getRole());
            return ResponseEntity.ok(userResponse);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
