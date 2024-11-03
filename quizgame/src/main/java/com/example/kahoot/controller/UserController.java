package com.example.kahoot.controller;

import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.service.UserService;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// UserController.java
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@RequestBody @NotEmpty String username) {
        // Проверяем, существует ли уже пользователь с таким именем
        if (userService.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }

        User admin = userService.createUser (username, Role.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin); // 201 Created
    }
}