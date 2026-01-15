package com.reasonly.backend.Question;

import java.util.List;

import com.reasonly.backend.QuestionDifficulty;
import com.reasonly.backend.QuestionType;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "questions")
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private QuestionDifficulty difficulty;

    @Column(columnDefinition = "jsonb")
    private String question;

    @ElementCollection
    @Column(name = "answer")
    private List<String> answers;

    @Column(nullable = false)
    private String correctAnswer;

    public Long getId() {
        return id;
    }

    public QuestionType getType() {
        return type;
    }

    public QuestionDifficulty getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setId(Long newId) {
        id = newId;
    }

    public void setType(QuestionType newType) {
        type = newType;
    }

    public void setDifficulty(QuestionDifficulty newDifficulty) {
        difficulty = newDifficulty;
    }

    public void setQuestion(String newQuestion) {
        question = newQuestion;
    }

    public void setAnswers(List<String> newAnswers) {
        answers = newAnswers;
    }

    public void setCorrectAnswer(String newCorrectAnswer) {
        correctAnswer = newCorrectAnswer;
    }

}
