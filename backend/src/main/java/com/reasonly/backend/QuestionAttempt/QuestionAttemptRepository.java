package com.reasonly.backend.QuestionAttempt;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.reasonly.backend.Question.Question;

public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {
    List<QuestionAttempt> findByUserId(Long userId);

    List<QuestionAttempt> findByQuestion(Question question);

    List<QuestionAttempt> findByUserIdAndQuestion(Long userId, Question question);

    @Modifying
    void deleteAllByUserId(Long userId);
}