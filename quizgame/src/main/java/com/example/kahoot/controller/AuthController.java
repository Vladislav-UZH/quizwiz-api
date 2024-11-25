package com.example.kahoot.controller;

import com.example.kahoot.auth.AuthResponse;
import com.example.kahoot.auth.LogoutResponse;
import com.example.kahoot.auth.TokenRequest;
import com.example.kahoot.dto.UserCredentials;
import com.example.kahoot.dto.UserResponse;
import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.security.JwtTokenProvider;
import com.example.kahoot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        User user = userService.createUser(userCredentials.getUsername(), userCredentials.getPassword(), Role.ROLE_USER);

        // Генерація токенів
        String accessToken = tokenProvider.generateToken(user, "access");
        String refreshToken = tokenProvider.generateToken(user, "refresh");

        // Неконфіденційна інформація про користувача
        UserResponse userResponse = new UserResponse(user.getUsername(), user.getScore(), user.getRole());

        // Повернення відповіді
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, userResponse));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        User user = userService.authenticate(credentials);

        // Генерація токенів
        String accessToken = tokenProvider.generateToken(user, "access");
        String refreshToken = tokenProvider.generateToken(user, "refresh");

        // Неконфіденційна інформація про користувача
        UserResponse userResponse = new UserResponse(user.getUsername(), user.getScore(), user.getRole());

        // Повернення відповіді
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken, userResponse));
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRequest tokenRequest) {
        if (tokenRequest == null || tokenRequest.getRefreshToken() == null) {
            return ResponseEntity.badRequest().body("Refresh token is missing");
        }

        // Перевірка токена
        if (tokenProvider.validateToken(tokenRequest.getRefreshToken()) &&
                tokenProvider.isTokenOfType(tokenRequest.getRefreshToken(), "refresh")) {

            Long userId = tokenProvider.getUserIdFromToken(tokenRequest.getRefreshToken());
            User user = userService.getUserById(userId);

            // Генерація нового Access токена
            String accessToken = tokenProvider.generateToken(user, "access");

            // Неконфіденційна інформація про користувача
            UserResponse userResponse = new UserResponse(user.getUsername(), user.getScore(), user.getRole());

            // Повернення відповіді
            return ResponseEntity.ok(new AuthResponse(accessToken, tokenRequest.getRefreshToken(), userResponse));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader != null ? authHeader.replace("Bearer ", "") : null;

        if (jwt != null && tokenProvider.validateToken(jwt)) {
            // Отримання інформації про користувача перед відкликанням токена
            Long userId = tokenProvider.getUserIdFromToken(jwt);
            User user = userService.getUserById(userId);
            UserResponse userResponse = new UserResponse(user.getUsername(), user.getScore(), user.getRole());

            tokenProvider.revokeToken(jwt);

            // Повернення відповіді
            return ResponseEntity.ok(new LogoutResponse("Logged out successfully", userResponse));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }


    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            User user = (User) authentication.getPrincipal();

            UserResponse userResponse = new UserResponse(user.getUsername(), user.getScore(), user.getRole());
            return ResponseEntity.ok(userResponse);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
