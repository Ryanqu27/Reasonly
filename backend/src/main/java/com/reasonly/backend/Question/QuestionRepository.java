package com.reasonly.backend.Question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByType(QuestionType type);

    @Query("SELECT q FROM Question q WHERE q.difficulty = :difficulty AND q.id NOT IN (SELECT qa.question.id FROM QuestionAttempt qa WHERE qa.userId = :userId)")
    List<Question> findUnansweredByDifficultyAndUserId(@Param("difficulty") QuestionDifficulty difficulty,
            @Param("userId") Long userId);
}