package com.example.kahoot.dto;

import java.util.List;

public class QuestionDto {
    private Long id;
    private Long quizStackId; // ID Quiz
    private String text;
    private List<OptionDto> options;

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
