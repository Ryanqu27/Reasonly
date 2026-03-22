package com.reasonly.backend.Question;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionTopic topic;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    private QuestionDifficulty difficulty;

    private String question;

    @ElementCollection
    @Column(name = "answer")
    private List<String> answers;

    @ElementCollection
    @Column(name = "correct_answer")
    private List<String> correctAnswer;

    // Only used by CODE_WRITING questions to specify which method the Runner should invoke
    private String methodName;

    public Question(Long id, QuestionTopic topic, QuestionType type, QuestionDifficulty difficulty, String question,
            List<String> answers, List<String> correctAnswer) {
        this.id = id;
        this.topic = topic;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    // Overloaded constructor for CODE_WRITING questions that require a methodName
    public Question(Long id, QuestionTopic topic, QuestionType type, QuestionDifficulty difficulty, String question,
            List<String> answers, List<String> correctAnswer, String methodName) {
        this(id, topic, type, difficulty, question, answers, correctAnswer);
        this.methodName = methodName;
    }
}
