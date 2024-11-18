package com.example.kahoot.model;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private int score;

    @Setter
    private String password;  // Добавлено поле password для хранения пароля

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public User(Long id, String username, String password, int score, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.score = score;
        this.role = role;
    }

    public User(long l, String user, int i, Role role) {
    }
}