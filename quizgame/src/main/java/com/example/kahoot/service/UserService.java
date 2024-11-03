package com.example.kahoot.service;

import com.example.kahoot.model.Role;
import com.example.kahoot.model.User;
import com.example.kahoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//проверить, рефакторить
    public User createUser(String userName, Role role) {
        User user = new User();
        user.setUserName(userName);
        user.setRole(role);
        user.setScore(0);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
