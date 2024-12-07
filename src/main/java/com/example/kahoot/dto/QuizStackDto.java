package com.example.kahoot.dto;

public class QuizStackDto {
    private Long id;
    private String name;

    // Конструктори
    public QuizStackDto() {
    }

    public QuizStackDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Гетери та сетери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
