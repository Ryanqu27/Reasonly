package com.reasonly.backend.Question;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.reasonly.backend.QuestionType;

import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.Column;
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

    private int difficulty;

    @Column(columnDefinition = "jsonb")
    private String question;

    @Type(StringArrayType.class)
    @Column(name = "answers", columnDefinition = "text[]")
    private String[] answers;

    @Column(nullable = false)
    private String correctAnswer;
}
