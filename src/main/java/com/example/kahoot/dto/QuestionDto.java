package com.example.kahoot.dto;

public class QuestionDto {
    private Long id;
    private Long quizStackId;
    private String text;

    // Можна додати список варіантів, якщо потрібно повернути питання разом із опціями
    // private List<OptionDto> options;

    public QuestionDto() {
    }

    public QuestionDto(Long id, Long quizStackId, String text) {
        this.id = id;
        this.quizStackId = quizStackId;
        this.text = text;
    }

    // Гетери та сетери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuizStackId() {
        return quizStackId;
    }

    public void setQuizStackId(Long quizStackId) {
        this.quizStackId = quizStackId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

