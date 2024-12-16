package com.example.kahoot.dto;

public class OptionDto {
    private Long id;
    private Long questionId;
    private String text;
    private boolean correct;

    public OptionDto() {
    }

    public OptionDto(Long id, Long questionId, String text, boolean correct) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.correct = correct;
    }

    // Гетери і сетери
    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
