package com.example.kahoot.dto;

public class OptionDto {
    private Long id;
    private Long questionId;
    private String text;

    public OptionDto() {
    }

    public OptionDto(Long id, Long questionId, String text) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
    }

    // Гетери та сетери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
