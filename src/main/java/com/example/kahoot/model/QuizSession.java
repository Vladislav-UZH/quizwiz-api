package com.example.kahoot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_session")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quiz_time", nullable = false)
    private Integer quizTime;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "score", nullable = false)
    private Integer score = 0;

    @Column(name = "current_question_id", nullable = false)
    private Integer currentQuestionId = -1;

    @Column(name = "current_question_start_time", nullable = false)
    private Integer currentQuestionStartTime = 0;

    public Long getId() {
        return id;
    }

    public Integer getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(Integer quizTime) {
        this.quizTime = quizTime;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getCurrentQuestionId() {
        return currentQuestionId;
    }

    public void setCurrentQuestionId(Integer currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    public Integer getCurrentQuestionStartTime() {
        return currentQuestionStartTime;
    }

    public void setCurrentQuestionStartTime(Integer currentQuestionStartTime) {
        this.currentQuestionStartTime = currentQuestionStartTime;
    }
}
