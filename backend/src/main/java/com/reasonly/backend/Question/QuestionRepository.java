package com.reasonly.backend.Question;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
        List<Question> findByTopic(QuestionTopic topic);

        @Query("SELECT q FROM Question q WHERE q.difficulty = :difficulty AND q.id NOT IN (SELECT qa.question.id FROM QuestionAttempt qa WHERE qa.userId = :userId)")
        List<Question> findUnansweredByDifficultyAndUserId(@Param("difficulty") QuestionDifficulty difficulty,
                        @Param("userId") Long userId);

        @Query(value = "SELECT q.* FROM questions q " +
                        "JOIN question_attempts qa ON q.id = qa.question_id " +
                        "WHERE qa.user_id = :userId " +
                        "AND qa.next_review_date <= CURRENT_DATE " +
                        "ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
        Optional<Question> findRandomDueReview(@Param("userId") Long userId);
}