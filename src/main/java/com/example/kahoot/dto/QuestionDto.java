package com.example.kahoot.dto;

import java.util.List;

public class QuestionDto {
    private Long id;
    private Long quizStackId;
    private String text;
    private List<OptionDto> options;

    public QuestionDto() {
    }

    public QuestionDto(Long id, Long quizStackId, String text, List<OptionDto> options) {
        this.id = id;
        this.quizStackId = quizStackId;
        this.text = text;
        this.options = options;
    }

    // Гетери і сетери
    public Long getId() {
        return id;
    }

    public Long getQuizStackId() {
        return quizStackId;
    }

    public String getText() {
        return text;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuizStackId(Long quizStackId) {
        this.quizStackId = quizStackId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }
}
