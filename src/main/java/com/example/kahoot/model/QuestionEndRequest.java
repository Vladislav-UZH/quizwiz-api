package com.example.kahoot.dto;

public class QuestionEndRequest {
    private Long id;
    private Integer currentQuestionId;
    private Integer finish_time; // finishTime
    private boolean correct;

    public Long getId() {
        return id;
    }

    public Integer getCurrentQuestionId() {
        return currentQuestionId;
    }

    public Integer getFinish_time() {
        return finish_time;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrentQuestionId(Integer currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public void setFinish_time(Integer finish_time) {
        this.finish_time = finish_time;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}