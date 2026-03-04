package com.reasonly.backend.QuestionAttempt;

import java.time.LocalDate;
import java.util.List;

import com.reasonly.backend.Question.Question;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_attempts")
public class QuestionAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    private Question question;

    @ElementCollection
    @Column(name = "answer")
    private List<String> answer;

    private LocalDate nextReviewDate = LocalDate.now().plusDays(1);

    @Column(name = "\"interval\"")
    private int interval = 1;
}
