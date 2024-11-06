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
    private String userName;

    @Setter
    private int score;

    @Setter
    private String password;  // Добавлено поле password для хранения пароля

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(Long id, String userName, Role role) {
        this.id = id;
        this.userName = userName;
        this.role = role;
    }

    public User(Long id, String userName, String password, int score, Role role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.role = role;
    }

    public User(long l, String user, int i, Role role) {
    }
}