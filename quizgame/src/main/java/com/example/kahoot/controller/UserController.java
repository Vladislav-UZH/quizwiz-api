package com.example.kahoot.controller;

import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// UserController
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@RequestBody String username) {
        User admin = userService.createUser(username, Role.ADMIN);
        return ResponseEntity.ok(admin);
    }
}