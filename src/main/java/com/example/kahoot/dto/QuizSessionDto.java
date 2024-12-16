package com.example.kahoot.dto;

public class QuizSessionDto {

    private Long id;
    private Integer quizTime;
    private Long quizId;
    private Integer score;
    private Long currentQuestionId;
    private Integer currentQuestionStartTime;

    public QuizSessionDto() {
    }

    public QuizSessionDto(Long id, Integer quizTime, Long quizId, Integer score, Long currentQuestionId, Integer currentQuestionStartTime) {
        this.id = id;
        this.quizTime = quizTime;
        this.quizId = quizId;
        this.score = score;
        this.currentQuestionId = currentQuestionId;
        this.currentQuestionStartTime = currentQuestionStartTime;
    }

    // Гетери і сетери
    public Long getId() {
        return id;
    }

    public Integer getQuizTime() {
        return quizTime;
    }

    public Long getQuizId() {
        return quizId;
    }

    public Integer getScore() {
        return score;
    }

    public Long getCurrentQuestionId() {
        return currentQuestionId;
    }

    public Integer getCurrentQuestionStartTime() {
        return currentQuestionStartTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuizTime(Integer quizTime) {
        this.quizTime = quizTime;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setCurrentQuestionId(Long currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public void setCurrentQuestionStartTime(Integer currentQuestionStartTime) {
        this.currentQuestionStartTime = currentQuestionStartTime;
    }
}
